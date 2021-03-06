package com.smart.home.modules.other.dao;

import com.smart.home.modules.other.entity.RptDashboard;
import com.smart.home.modules.other.entity.RptDashboardExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RptDashboardMapper {
    long countByExample(RptDashboardExample example);

    int deleteByExample(RptDashboardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RptDashboard record);

    int insertSelective(RptDashboard record);

    List<RptDashboard> selectByExample(RptDashboardExample example);

    RptDashboard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RptDashboard record, @Param("example") RptDashboardExample example);

    int updateByExample(@Param("record") RptDashboard record, @Param("example") RptDashboardExample example);

    int updateByPrimaryKeySelective(RptDashboard record);

    int updateByPrimaryKey(RptDashboard record);
}