package com.smart.home.modules.other.dao;

import com.smart.home.modules.other.entity.StatisticPage;
import com.smart.home.modules.other.entity.StatisticPageExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StatisticPageMapper {
    long countByExample(StatisticPageExample example);

    int deleteByExample(StatisticPageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StatisticPage record);

    int insertSelective(StatisticPage record);

    List<StatisticPage> selectByExample(StatisticPageExample example);

    StatisticPage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StatisticPage record, @Param("example") StatisticPageExample example);

    int updateByExample(@Param("record") StatisticPage record, @Param("example") StatisticPageExample example);

    int updateByPrimaryKeySelective(StatisticPage record);

    int updateByPrimaryKey(StatisticPage record);

    List<StatisticPage> selectSummaryByPage(@Param("name") String name);
}