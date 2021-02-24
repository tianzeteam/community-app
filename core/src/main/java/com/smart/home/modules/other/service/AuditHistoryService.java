package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.AuditCategoryEnum;
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

    /**
     * 创建审核历史记录
     * @param auditCategoryEnum 审核类型
     * @param primaryKey 审查了哪一条内容，该内容记录主键ID
     * @param logs 操作日志，自己拼接内容
     * @param yesNoEnum 是否通过 0否1是
     * @param userId 审核人主键ID
     * @return
     */
    public int create(AuditCategoryEnum auditCategoryEnum, Long primaryKey, String logs, YesNoEnum yesNoEnum, Long userId) {
        AuditHistory auditHistory = new AuditHistory();
        auditHistory.withAuditFlag(yesNoEnum.getCode())
                .withCategory(auditCategoryEnum.getCode())
                .withCategoryName(auditCategoryEnum.getDesc())
                .withCreatedBy(userId)
                .withCreatedTime(new Date())
                .withDetails(logs)
                .withRevision(0)
                .withSourceId(primaryKey);
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
        if (auditHistory.getCategory() != null) {
            criteria.andCategoryEqualTo(auditHistory.getCategory());
        }
        if (auditHistory.getSourceId() != null) {
            criteria.andSourceIdEqualTo(auditHistory.getSourceId());
        }
        return auditHistoryMapper.selectByExample(example);
    }

    public AuditHistory findById(Long id) {
        AuditHistory auditHistory = auditHistoryMapper.selectByPrimaryKey(id);
        return auditHistory;
    }

}
