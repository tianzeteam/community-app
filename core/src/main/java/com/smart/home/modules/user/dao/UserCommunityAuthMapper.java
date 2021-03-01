package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserCommunityAuth;
import com.smart.home.modules.user.entity.UserCommunityAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserCommunityAuthMapper {
    long countByExample(UserCommunityAuthExample example);

    int deleteByExample(UserCommunityAuthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserCommunityAuth record);

    int insertSelective(UserCommunityAuth record);

    List<UserCommunityAuth> selectByExample(UserCommunityAuthExample example);

    UserCommunityAuth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserCommunityAuth record, @Param("example") UserCommunityAuthExample example);

    int updateByExample(@Param("record") UserCommunityAuth record, @Param("example") UserCommunityAuthExample example);

    int updateByPrimaryKeySelective(UserCommunityAuth record);

    int updateByPrimaryKey(UserCommunityAuth record);

    int updateAdminFlag(@Param("userId") Long userId,@Param("flag") int flag);

    List<UserCommunityAuth> queryAllAdminUser(@Param("userId") Long userId, @Param("nickName") String nickName);
}