package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.other.dao.ReportHistoryMapper;
import com.smart.home.modules.other.entity.ReportHistory;
import com.smart.home.modules.other.entity.ReportHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ReportHistoryService {

    @Resource
    ReportHistoryMapper reportHistoryMapper;

    public int create(ReportHistory reportHistory) {
        reportHistory.setCreatedTime(new Date());
        return reportHistoryMapper.insertSelective(reportHistory);
    }

    public int update(ReportHistory reportHistory) {
        reportHistory.setUpdatedTime(new Date());
        return reportHistoryMapper.updateByPrimaryKeySelective(reportHistory);
    }

    public int deleteById(Long id) {
        return reportHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            reportHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ReportHistory> selectByPage(ReportHistory reportHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ReportHistoryExample example = new ReportHistoryExample();
        ReportHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return reportHistoryMapper.selectByExample(example);
    }

    public ReportHistory findById(Long id) {
        ReportHistory reportHistory = reportHistoryMapper.selectByPrimaryKey(id);
        return reportHistory;
    }

}
