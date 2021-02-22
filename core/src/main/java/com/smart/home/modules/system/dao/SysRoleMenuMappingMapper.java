package com.smart.home.modules.system.dao;

import com.smart.home.modules.system.entity.SysRoleMenuMapping;
import com.smart.home.modules.system.entity.SysRoleMenuMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuMappingMapper {
    long countByExample(SysRoleMenuMappingExample example);

    int deleteByExample(SysRoleMenuMappingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleMenuMapping record);

    int insertSelective(SysRoleMenuMapping record);

    List<SysRoleMenuMapping> selectByExample(SysRoleMenuMappingExample example);

    SysRoleMenuMapping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysRoleMenuMapping record, @Param("example") SysRoleMenuMappingExample example);

    int updateByExample(@Param("record") SysRoleMenuMapping record, @Param("example") SysRoleMenuMappingExample example);

    int updateByPrimaryKeySelective(SysRoleMenuMapping record);

    int updateByPrimaryKey(SysRoleMenuMapping record);

    List<Integer> findMenuIdList(@Param("roleIdList") List<Long> roleIdList);
}