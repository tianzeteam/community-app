package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.dto.MyFocusDTO;
import com.smart.home.modules.user.dto.MyFollowerDTO;
import com.smart.home.modules.user.entity.UserFollower;
import com.smart.home.modules.user.entity.UserFollowerExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserFollowerMapper {
    long countByExample(UserFollowerExample example);

    int deleteByExample(UserFollowerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserFollower record);

    int insertSelective(UserFollower record);

    List<UserFollower> selectByExample(UserFollowerExample example);

    UserFollower selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserFollower record, @Param("example") UserFollowerExample example);

    int updateByExample(@Param("record") UserFollower record, @Param("example") UserFollowerExample example);

    int updateByPrimaryKeySelective(UserFollower record);

    int updateByPrimaryKey(UserFollower record);

    List<MyFollowerDTO> myFollowerByPage(@Param("userId") Long userId);

    List<MyFocusDTO> myFocusByPage(@Param("userId") Long userId);

    int followEach(@Param("userId") Long userId,@Param("followUserId") Long followUserId);
}