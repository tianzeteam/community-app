package com.smart.home.modules.other.dao;

import com.smart.home.modules.other.entity.AuditHistory;
import com.smart.home.modules.other.entity.AuditHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuditHistoryMapper {
    long countByExample(AuditHistoryExample example);

    int deleteByExample(AuditHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AuditHistory record);

    int insertSelective(AuditHistory record);

    List<AuditHistory> selectByExample(AuditHistoryExample example);

    AuditHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AuditHistory record, @Param("example") AuditHistoryExample example);

    int updateByExample(@Param("record") AuditHistory record, @Param("example") AuditHistoryExample example);

    int updateByPrimaryKeySelective(AuditHistory record);

    int updateByPrimaryKey(AuditHistory record);
}