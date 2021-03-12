package com.smart.home.task;

import cn.hutool.core.date.DateUtil;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.DateUtils;
import com.smart.home.modules.system.entity.SysFile;
import com.smart.home.modules.system.service.SysFileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/12
 **/
@Log4j2
@Component
public class DeleteUnSyncFiles {

    @Autowired
    private SysFileService sysFileService;

    /**
     * 删除未同步的图片
     */
    @Scheduled(cron = "0 5 3 * * ?")
    public void deleteUnSyncFiles() {
        Date date = DateUtil.offsetDay(new Date(), -1);
        Date startDate = DateUtils.getEarliestDate(date);
        Date endDate = DateUtils.getLastDateOfTheDay(date);
        SysFile sysFile = new SysFile();
        sysFile.setSyncFlag(YesNoEnum.NO.getCode());
        List<SysFile> list = sysFileService.selectAll(sysFile, startDate, endDate);
        for (SysFile file : list) {
            try {
            sysFileService.deleteByNewName(file.getNewName());
            Thread.sleep(10);
            } catch (Throwable e) {
                log.error(e);
            }
        }
    }

}
