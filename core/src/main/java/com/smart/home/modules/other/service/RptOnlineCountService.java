package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.util.DateUtils;
import com.smart.home.modules.other.dao.RptOnlineCountMapper;
import com.smart.home.modules.other.entity.RptOnlineCount;
import com.smart.home.modules.other.entity.RptOnlineCountExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class RptOnlineCountService {

    @Resource
    RptOnlineCountMapper rptOnlineCountMapper;

    public int create(RptOnlineCount rptOnlineCount) {
        rptOnlineCount.setCreatedTime(new Date());
        return rptOnlineCountMapper.insertSelective(rptOnlineCount);
    }

    public List<RptOnlineCount> selectByPage(RptOnlineCount rptOnlineCount, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        RptOnlineCountExample example = new RptOnlineCountExample();
        RptOnlineCountExample.Criteria criteria = example.createCriteria();
        if (rptOnlineCount.getCreatedTime() != null) {
            Date startDate = DateUtils.getEarliestDate(rptOnlineCount.getCreatedTime());
            Date endDate = DateUtils.getLastDateOfTheDay(rptOnlineCount.getCreatedTime());
            criteria.andCreatedTimeBetween(startDate, endDate);
        }
        return rptOnlineCountMapper.selectByExample(example);
    }

}
