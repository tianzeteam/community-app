package com.smart.home.task;

import com.smart.home.common.util.DateUtils;
import com.smart.home.modules.system.service.SysTraceLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/7
 **/
@Log4j2
@Component
public class ClearTraceLogTask {

    @Autowired
    private SysTraceLogService sysTraceLogService;

    @Scheduled(cron = "0 5 0 * * ?")
    public void clearTraceLog() {
        try {
            Date preDate = DateUtils.getPreDate();
            Date startDate = DateUtils.getEarliestDate(preDate);
            Date endDate = DateUtils.getLastDateOfTheDay(preDate);
            sysTraceLogService.deleteLogByDate(startDate, endDate);
        } catch (Throwable e) {
            log.error(e);
        }
    }

}
