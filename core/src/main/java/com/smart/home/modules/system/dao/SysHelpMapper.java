package com.smart.home.modules.system.dao;

import com.smart.home.modules.system.entity.SysHelp;
import com.smart.home.modules.system.entity.SysHelpExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysHelpMapper {
    long countByExample(SysHelpExample example);

    int deleteByExample(SysHelpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysHelp record);

    int insertSelective(SysHelp record);

    List<SysHelp> selectByExample(SysHelpExample example);

    SysHelp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysHelp record, @Param("example") SysHelpExample example);

    int updateByExample(@Param("record") SysHelp record, @Param("example") SysHelpExample example);

    int updateByPrimaryKeySelective(SysHelp record);

    int updateByPrimaryKey(SysHelp record);
}