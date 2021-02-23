package com.smart.home.modules.other.dao;

import com.smart.home.modules.other.entity.ReportHistory;
import com.smart.home.modules.other.entity.ReportHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReportHistoryMapper {
    long countByExample(ReportHistoryExample example);

    int deleteByExample(ReportHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ReportHistory record);

    int insertSelective(ReportHistory record);

    List<ReportHistory> selectByExample(ReportHistoryExample example);

    ReportHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ReportHistory record, @Param("example") ReportHistoryExample example);

    int updateByExample(@Param("record") ReportHistory record, @Param("example") ReportHistoryExample example);

    int updateByPrimaryKeySelective(ReportHistory record);

    int updateByPrimaryKey(ReportHistory record);
}