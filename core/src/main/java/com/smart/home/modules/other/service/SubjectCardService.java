package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.other.dao.SubjectCardMapper;
import com.smart.home.modules.other.entity.SubjectCard;
import com.smart.home.modules.other.entity.SubjectCardExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public int create(SubjectCard subjectCard) {
        subjectCard.setCreatedTime(new Date());
        subjectCard.setRevision(0);
        return subjectCardMapper.insertSelective(subjectCard);
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

}
