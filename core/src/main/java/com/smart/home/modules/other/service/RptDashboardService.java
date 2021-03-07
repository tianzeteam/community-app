package com.smart.home.modules.other.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.other.dao.RptDashboardMapper;
import com.smart.home.modules.other.entity.RptDashboard;
import com.smart.home.modules.other.entity.RptDashboardExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class RptDashboardService {

    @Resource
    RptDashboardMapper rptDashboardMapper;

    public int create(RptDashboard rptDashboard) {
        return rptDashboardMapper.insertSelective(rptDashboard);
    }

    public List<RptDashboard> selectByPage(RptDashboard rptDashboard, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        RptDashboardExample example = new RptDashboardExample();
        RptDashboardExample.Criteria criteria = example.createCriteria();
        if (rptDashboard.getCreatedTime() != null) {
            criteria.andCreatedTimeEqualTo(rptDashboard.getCreatedTime());
        }
        example.setOrderByClause("created_time desc");
        return rptDashboardMapper.selectByExample(example);
    }

}
