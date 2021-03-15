package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.modules.community.dao.CommunityCategoryMapper;
import com.smart.home.modules.community.dao.CommunityMapper;
import com.smart.home.modules.community.entity.CommunityCategory;
import com.smart.home.modules.community.entity.CommunityCategoryExample;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityCategoryService {

    @Resource
    CommunityCategoryMapper communityCategoryMapper;
    @Resource
    CommunityMapper communityMapper;
    @Autowired
    private SysFileService sysFileService;

    public int create(CommunityCategory communityCategory) throws ServiceException {
        // 检查同名
        CommunityCategoryExample example = new CommunityCategoryExample();
        example.createCriteria().andTitleEqualTo(communityCategory.getTitle());
        if (communityCategoryMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该社区类目已经存在");
        }
        communityCategory.setCreatedTime(new Date());
        communityCategory.setRevision(0);
        communityCategory.setState(RecordStatusEnum.NORMAL.getStatus());
        int affectRow = communityCategoryMapper.insertSelective(communityCategory);
        if (StringUtils.isNotBlank(communityCategory.getCoverImage())) {
            sysFileService.sync(FileUtils.getFileNameFromUrl(communityCategory.getCoverImage()));
        }
        return affectRow;
    }

    public int update(CommunityCategory communityCategory) throws ServiceException {
        // 检查同名
        CommunityCategoryExample example = new CommunityCategoryExample();
        example.createCriteria().andTitleEqualTo(communityCategory.getTitle()).andIdNotEqualTo(communityCategory.getId());
        if (communityCategoryMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该社区类目已经存在");
        }
        CommunityCategory dbData = findById(communityCategory.getId());
        String oldImage = dbData.getCoverImage();
        communityCategory.setUpdatedTime(new Date());
        int affectRow = communityCategoryMapper.updateByPrimaryKeySelective(communityCategory);
        if (StringUtils.isBlank(communityCategory.getCoverImage())) {
            if (StringUtils.isNotBlank(oldImage)) {
                sysFileService.deleteByNewName(FileUtils.getFileNameFromUrl(oldImage));
            }
        } else {
            if (!StringUtils.equals(communityCategory.getCoverImage(), oldImage)) {
                sysFileService.deleteByNewName(FileUtils.getFileNameFromUrl(oldImage));
                sysFileService.sync(FileUtils.getFileNameFromUrl(communityCategory.getCoverImage()));
            }
        }
        return affectRow;
    }

    public int deleteById(Long id) {
        return communityCategoryMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) throws ServiceException {
        for (Long id : idList) {
            // 检查有没有关联到社区了
            if (communityMapper.countByCategoryId(id) > 0) {
                throw new ServiceException("社区类目已经关联到社区了，不能删除");
            }
            CommunityCategory communityCategory = findById(id.intValue());
            if (StringUtils.isNotBlank(communityCategory.getCoverImage())) {
                communityCategoryMapper.deleteByPrimaryKey(id.intValue());
                sysFileService.deleteByNewName(FileUtils.getFileNameFromUrl(communityCategory.getCoverImage()));
            } else {
                communityCategoryMapper.deleteByPrimaryKey(id.intValue());
            }
        }
    }

    public List<CommunityCategory> selectByPage(CommunityCategory communityCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityCategoryExample example = new CommunityCategoryExample();
        CommunityCategoryExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(communityCategory.getTitle())) {
            criteria.andTitleEqualTo(communityCategory.getTitle());
        }
        example.setOrderByClause("sort desc");
        return communityCategoryMapper.selectByExample(example);
    }

    public CommunityCategory findById(Integer id) {
        CommunityCategory communityCategory = communityCategoryMapper.selectByPrimaryKey(id);
        return communityCategory;
    }

    public List<CommunityCategory> queryAllValid() {
        CommunityCategoryExample example = new CommunityCategoryExample();
        example.createCriteria().andStateEqualTo(RecordStatusEnum.NORMAL.getStatus());
        example.setOrderByClause("sort desc");
        return communityCategoryMapper.selectByExample(example);
    }
}
