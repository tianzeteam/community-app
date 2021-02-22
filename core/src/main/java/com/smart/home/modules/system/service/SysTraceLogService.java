package com.smart.home.modules.system.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smart.home.common.bean.RequestErrorInfo;
import com.smart.home.common.bean.RequestInfo;
import com.smart.home.modules.system.dao.SysTraceLogMapper;
import com.smart.home.modules.system.entity.SysTraceLog;
import com.smart.home.modules.system.entity.SysTraceLogExample;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/21
 **/
@Log4j2
@Service
public class SysTraceLogService {

    @Resource
    private SysTraceLogMapper sysTraceLogMapper;
    @Autowired
    private SysConfigService sysConfigService;

    public void recordSuccessLog(String traceId, RequestInfo requestInfo) {
        // 本身查询日志的请求不需要记录
        if (requestInfo.getClassMethod().contains("queryByTraceId")) {
            return;
        }
        String needStoreInDatabase = sysConfigService.findValueFromCache("trace.log.storeType");
        if ("db".equals(needStoreInDatabase)) {
            SysTraceLog sysTraceLog = new SysTraceLog();
            sysTraceLog.setTraceId(traceId);
            sysTraceLog.setCostTime(requestInfo.getTimeCost().intValue());
            sysTraceLog.setState("success");
            sysTraceLog.setCreatedTime(new Date());
            sysTraceLog.setUrl(requestInfo.getUrl());
            sysTraceLog.setLogs(JSON.toJSONString(requestInfo));
            sysTraceLogMapper.insertSelective(sysTraceLog);
        } else {
            log.debug("Request Info      : {}", JSON.toJSONString(requestInfo));
        }
    }

    public void recordFailedLog(String traceId, RequestErrorInfo requestErrorInfo) {
        String needStoreInDatabase = sysConfigService.findValueFromCache("trace.log.storeType");
        if ("db".equals(needStoreInDatabase)) {
            SysTraceLog sysTraceLog = new SysTraceLog();
            sysTraceLog.setTraceId(traceId);
            sysTraceLog.setState("failed");
            sysTraceLog.setCreatedTime(new Date());
            sysTraceLog.setUrl(requestErrorInfo.getUrl());
            sysTraceLog.setLogs(JSON.toJSONString(requestErrorInfo, SerializerFeature.PrettyFormat));
            sysTraceLogMapper.insertSelective(sysTraceLog);
        } else {
            log.debug("Error Request Info      : {}", JSON.toJSONString(requestErrorInfo));
        }
    }

    public SysTraceLog queryByTraceId(String traceId) {
        SysTraceLogExample example = new SysTraceLogExample();
        example.createCriteria().andTraceIdEqualTo(traceId);
        List<SysTraceLog> list = sysTraceLogMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
