package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserPrivacySetting;
import com.smart.home.modules.user.entity.UserPrivacySettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserPrivacySettingMapper {
    long countByExample(UserPrivacySettingExample example);

    int deleteByExample(UserPrivacySettingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserPrivacySetting record);

    int insertSelective(UserPrivacySetting record);

    List<UserPrivacySetting> selectByExample(UserPrivacySettingExample example);

    UserPrivacySetting selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserPrivacySetting record, @Param("example") UserPrivacySettingExample example);

    int updateByExample(@Param("record") UserPrivacySetting record, @Param("example") UserPrivacySettingExample example);

    int updateByPrimaryKeySelective(UserPrivacySetting record);

    int updateByPrimaryKey(UserPrivacySetting record);

    List<Integer> queryCheckedSettingList(@Param("userId") Long userId);
}