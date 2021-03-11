package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.util.FileUtils;
import com.smart.home.modules.other.dao.SubjectCardMapper;
import com.smart.home.modules.other.entity.SubjectCard;
import com.smart.home.modules.other.entity.SubjectCardExample;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class SubjectCardService {

    @Resource
    SubjectCardMapper subjectCardMapper;
    @Autowired
    private SysFileService sysFileService;

    public int create(SubjectCard subjectCard) {
        subjectCard.setCreatedTime(new Date());
        subjectCard.setRevision(0);
        int affectRow = subjectCardMapper.insertSelective(subjectCard);
        if (StringUtils.isNotBlank(subjectCard.getCoverImage())) {
            sysFileService.sync(FileUtils.getFileNameFromUrl(subjectCard.getCoverImage()));
        }
        return affectRow;
    }

    public int update(SubjectCard subjectCard) {
        subjectCard.setUpdatedTime(new Date());
        return subjectCardMapper.updateByPrimaryKeySelective(subjectCard);
    }

    public int deleteById(Long id) {
        return subjectCardMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            subjectCardMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<SubjectCard> selectByPage(SubjectCard subjectCard, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SubjectCardExample example = new SubjectCardExample();
        SubjectCardExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return subjectCardMapper.selectByExample(example);
    }

    public SubjectCard findById(Long id) {
        SubjectCard subjectCard = subjectCardMapper.selectByPrimaryKey(id.intValue());
        return subjectCard;
    }

    public SubjectCard queryLatestSubjectCard() {
        PageHelper.startPage(1, 1);
        SubjectCardExample example = new SubjectCardExample();
        example.createCriteria().andRevisionEqualTo(0);
        example.setOrderByClause("created_time desc");
        List<SubjectCard> list = this.subjectCardMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
