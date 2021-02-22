package com.smart.home.modules.system.dao;

import com.smart.home.modules.system.entity.SysTraceLog;
import com.smart.home.modules.system.entity.SysTraceLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysTraceLogMapper {
    long countByExample(SysTraceLogExample example);

    int deleteByExample(SysTraceLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysTraceLog record);

    int insertSelective(SysTraceLog record);

    List<SysTraceLog> selectByExample(SysTraceLogExample example);

    SysTraceLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysTraceLog record, @Param("example") SysTraceLogExample example);

    int updateByExample(@Param("record") SysTraceLog record, @Param("example") SysTraceLogExample example);

    int updateByPrimaryKeySelective(SysTraceLog record);

    int updateByPrimaryKey(SysTraceLog record);
}