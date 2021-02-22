package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.other.dao.AuditHistoryMapper;
import com.smart.home.modules.other.entity.AuditHistory;
import com.smart.home.modules.other.entity.AuditHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class AuditHistoryService {

    @Resource
    AuditHistoryMapper auditHistoryMapper;

    public int create(AuditHistory auditHistory) {
        auditHistory.setCreatedTime(new Date());
        return auditHistoryMapper.insertSelective(auditHistory);
    }

    public int update(AuditHistory auditHistory) {
        auditHistory.setUpdatedTime(new Date());
        return auditHistoryMapper.updateByPrimaryKeySelective(auditHistory);
    }

    public int deleteById(Long id) {
        return auditHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            auditHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<AuditHistory> selectByPage(AuditHistory auditHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        AuditHistoryExample example = new AuditHistoryExample();
        AuditHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return auditHistoryMapper.selectByExample(example);
    }

    public AuditHistory findById(Long id) {
        AuditHistory auditHistory = auditHistoryMapper.selectByPrimaryKey(id);
        return auditHistory;
    }

}
