package com.smart.home.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.cloud.qcloud.consts.BucketConsts;
import com.smart.home.cloud.qcloud.cos.CosUtil;
import com.smart.home.common.bean.Upload;
import com.smart.home.common.contants.FileStoreType;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.FileUtils;
import com.smart.home.modules.system.dao.SysFileMapper;
import com.smart.home.modules.system.entity.SysFile;
import com.smart.home.modules.system.entity.SysFileExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class SysFileService {

    @Resource
    SysFileMapper sysFileMapper;

    public int create(SysFile sysFile) {
        sysFile.setCreatedTime(new Date());
        return sysFileMapper.insertSelective(sysFile);
    }

    public int update(SysFile sysFile) {
        sysFile.setUpdatedTime(new Date());
        return sysFileMapper.updateByPrimaryKeySelective(sysFile);
    }

    public int deleteById(Long id) {
        return sysFileMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            sysFileMapper.deleteByPrimaryKey(id);
        }
    }

    public List<SysFile> selectByPage(SysFile sysFile, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SysFileExample example = new SysFileExample();
        SysFileExample.Criteria criteria = example.createCriteria();
        if(sysFile.getSyncFlag() != null) {
            criteria.andSyncFlagEqualTo(sysFile.getSyncFlag());
        }
        return sysFileMapper.selectByExample(example);
    }

    public List<SysFile> selectAll(SysFile sysFile, Date startDate, Date endDate) {
        SysFileExample example = new SysFileExample();
        SysFileExample.Criteria criteria = example.createCriteria();
        if(sysFile.getSyncFlag() != null) {
            criteria.andSyncFlagEqualTo(sysFile.getSyncFlag());
        }
        criteria.andCreatedTimeBetween(startDate, endDate);
        return sysFileMapper.selectByExample(example);
    }

    public SysFile findById(Long id) {
        SysFile sysFile = sysFileMapper.selectByPrimaryKey(id);
        return sysFile;
    }

    public void doUpload(InputStream inputStream, Upload upload) {
        SysFile sysFile = new SysFile();
        BeanUtils.copyProperties(upload, sysFile);
        if (StringUtils.equals(FileStoreType.COS, upload.getStoreType())) {
            // 存储到腾讯cos对象存储服务
            String md5 = CosUtil.uploadFile(inputStream, upload);
            sysFile.setMd5(md5);
            sysFile.setUrl("https://"+upload.getBucketName()+"-"+CosUtil.APP_ID+".cos.ap-nanjing.myqcloud.com/"+upload.getNewName());
        }
        sysFile.setCreatedTime(new Date());
        sysFile.setSyncFlag(YesNoEnum.NO.getCode());
        sysFile.setRevision(0);
        upload.setUrl(sysFile.getUrl());
        sysFileMapper.insertSelective(sysFile);
    }

    public void sync(String fileName) {
        // 1 代表同步了
        sysFileMapper.updateSyncFlag(fileName, 1);
    }

    public void syncList(List<String> needSyncFileNameList) {
        if (CollectionUtils.isEmpty(needSyncFileNameList)) {
            return;
        }
        sysFileMapper.updateSyncFlagList(needSyncFileNameList, 1);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteByNewName(String newFileName) {
        if (StringUtils.isBlank(newFileName)) {
            return;
        }
        SysFile sysFile = findByNewName(newFileName);
        if (null != sysFile) {
            if (deleteById(sysFile.getId()) > 0) {
                CosUtil.deleteFile(newFileName, BucketConsts.IMAGE);
            }
        }
    }

    private SysFile findByNewName(String newFileName) {
        if (StringUtils.isBlank(newFileName)) {
            return null;
        }
        SysFileExample example = new SysFileExample();
        example.createCriteria().andNewNameEqualTo(newFileName);
        List<SysFile> list = sysFileMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public void deleteImageByUrlList(List<String> imageList) {
        String newName = null;
        for (String url : imageList) {
            newName = FileUtils.getFileNameFromUrl(url);
            if (StringUtils.isBlank(newName)) {
                continue;
            }
            CosUtil.deleteFile(newName, BucketConsts.IMAGE);
            SysFileExample example = new SysFileExample();
            example.createCriteria().andNewNameEqualTo(newName);
            sysFileMapper.deleteByExample(example);
        }
    }

    public void syncImageFileList(List<String> imageList) {
        if (CollectionUtils.isEmpty(imageList)) {
            return;
        }
        List<String> fileNameList = new ArrayList<>();
        for (String url : imageList) {
            fileNameList.add(FileUtils.getFileNameFromUrl(url));
        }
        syncList(fileNameList);
    }

}
