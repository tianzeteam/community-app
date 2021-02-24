package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.entity.UserDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDataMapper {
    long countByExample(UserDataExample example);

    int deleteByExample(UserDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserData record);

    int insertSelective(UserData record);

    List<UserData> selectByExample(UserDataExample example);

    UserData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserData record, @Param("example") UserDataExample example);

    int updateByExample(@Param("record") UserData record, @Param("example") UserDataExample example);

    int updateByPrimaryKeySelective(UserData record);

    int updateByPrimaryKey(UserData record);

    int updateSign(@Param("userId") Long userId,@Param("sign") String sign);

    int increaseLikeCount(@Param("userId") Long userId);

    int decreaseLikeCount(@Param("userId") Long userId);

}