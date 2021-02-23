package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserPrivacy;
import com.smart.home.modules.user.entity.UserPrivacyExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserPrivacyMapper {
    long countByExample(UserPrivacyExample example);

    int deleteByExample(UserPrivacyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserPrivacy record);

    int insertSelective(UserPrivacy record);

    List<UserPrivacy> selectByExample(UserPrivacyExample example);

    UserPrivacy selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserPrivacy record, @Param("example") UserPrivacyExample example);

    int updateByExample(@Param("record") UserPrivacy record, @Param("example") UserPrivacyExample example);

    int updateByPrimaryKeySelective(UserPrivacy record);

    int updateByPrimaryKey(UserPrivacy record);
}