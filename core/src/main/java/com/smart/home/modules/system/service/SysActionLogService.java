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

    public List<SysActionLog> selectByPage(SysActionLog sysActionLog, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SysActionLogExample example = new SysActionLogExample();
        SysActionLogExample.Criteria criteria = example.createCriteria();
        if (sysActionLog.getUserId() != null) {
            criteria.andCreatedByEqualTo(sysActionLog.getUserId());
        }
        if (sysActionLog.getStartDate() != null) {
            criteria.andCreatedTimeGreaterThanOrEqualTo(sysActionLog.getStartDate());
        }
        if (sysActionLog.getEndDate() != null) {
            criteria.andCreatedTimeLessThanOrEqualTo(sysActionLog.getEndDate());
        }
        example.orderBy("created_time desc");
        return sysActionLogMapper.selectByExample(example);
    }

    public SysActionLog findById(Long id) {
        SysActionLog sysActionLog = sysActionLogMapper.selectByPrimaryKey(id);
        return sysActionLog;
    }

    public void loginLog(String clientIpAddr, Long userId, String message) {
        SysActionLog sysActionLog = new SysActionLog();
        sysActionLog.withCreatedBy(userId)
                .withCreatedTime(new Date())
                .withIp(clientIpAddr)
                .withName("用户登陆").withMessage(message);
        sysActionLogMapper.insertSelective(sysActionLog);
    }
}
