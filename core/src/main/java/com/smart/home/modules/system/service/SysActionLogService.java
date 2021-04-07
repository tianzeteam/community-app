package com.smart.home.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.system.dao.SysActionLogMapper;
import com.smart.home.modules.system.entity.SysActionLog;
import com.smart.home.modules.system.entity.SysActionLogExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
* @author jason
**/
@Service
public class SysActionLogService {

    @Resource
    SysActionLogMapper sysActionLogMapper;

    public int create(SysActionLog sysActionLog) {
                                                                                                                                                                                        sysActionLog.setCreatedTime(new Date());
                                            return sysActionLogMapper.insertSelective(sysActionLog);
    }

    public int update(SysActionLog sysActionLog) {
                                                                                                                                                                                                return sysActionLogMapper.updateByPrimaryKeySelective(sysActionLog);
    }

    public int deleteById(Long id) {
        return sysActionLogMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            sysActionLogMapper.deleteByPrimaryKey(id);
        }
    }

    public List<SysActionLog> selectByPage(SysActionLog sysActionLog, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SysActionLogExample example = new SysActionLogExample();
        SysActionLogExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
                                                                                                                                        return sysActionLogMapper.selectByExample(example);
    }

    public SysActionLog findById(Long id) {
        SysActionLog sysActionLog = sysActionLogMapper.selectByPrimaryKey(id);
        return sysActionLog;
    }

}
