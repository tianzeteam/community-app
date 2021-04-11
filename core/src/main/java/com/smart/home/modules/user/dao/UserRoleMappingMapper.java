package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserRoleMapping;
import com.smart.home.modules.user.entity.UserRoleMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMappingMapper {
    long countByExample(UserRoleMappingExample example);

    int deleteByExample(UserRoleMappingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleMapping record);

    int insertSelective(UserRoleMapping record);

    List<UserRoleMapping> selectByExample(UserRoleMappingExample example);

    UserRoleMapping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRoleMapping record, @Param("example") UserRoleMappingExample example);

    int updateByExample(@Param("record") UserRoleMapping record, @Param("example") UserRoleMappingExample example);

    int updateByPrimaryKeySelective(UserRoleMapping record);

    int updateByPrimaryKey(UserRoleMapping record);

    int deleteByUserId(@Param("userId") Long userId);

    List<String> findUserRoleCodeList(@Param("userId") Long userId);

    Integer existsByRoleCodeAndUserId(@Param("roleCode") String roleCode,@Param("userId") Long userId);
}