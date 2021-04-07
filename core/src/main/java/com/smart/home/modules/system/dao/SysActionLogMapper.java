package com.smart.home.modules.system.dao;

import com.smart.home.modules.system.entity.SysActionLog;
import com.smart.home.modules.system.entity.SysActionLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysActionLogMapper {
    long countByExample(SysActionLogExample example);

    int deleteByExample(SysActionLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysActionLog record);

    int insertSelective(SysActionLog record);

    List<SysActionLog> selectByExample(SysActionLogExample example);

    SysActionLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysActionLog record, @Param("example") SysActionLogExample example);

    int updateByExample(@Param("record") SysActionLog record, @Param("example") SysActionLogExample example);

    int updateByPrimaryKeySelective(SysActionLog record);

    int updateByPrimaryKey(SysActionLog record);
}