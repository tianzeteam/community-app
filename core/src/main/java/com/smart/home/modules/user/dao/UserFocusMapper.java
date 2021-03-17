package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserFocus;
import com.smart.home.modules.user.entity.UserFocusExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserFocusMapper {
    long countByExample(UserFocusExample example);

    int deleteByExample(UserFocusExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserFocus record);

    int insertSelective(UserFocus record);

    List<UserFocus> selectByExample(UserFocusExample example);

    UserFocus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserFocus record, @Param("example") UserFocusExample example);

    int updateByExample(@Param("record") UserFocus record, @Param("example") UserFocusExample example);

    int updateByPrimaryKeySelective(UserFocus record);

    int updateByPrimaryKey(UserFocus record);

    List<UserFocus> selectByUserId(@Param("userId") Long userId);
}