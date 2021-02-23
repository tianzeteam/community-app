package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserArticleChannelPreference;
import com.smart.home.modules.user.entity.UserArticleChannelPreferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserArticleChannelPreferenceMapper {
    long countByExample(UserArticleChannelPreferenceExample example);

    int deleteByExample(UserArticleChannelPreferenceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserArticleChannelPreference record);

    int insertSelective(UserArticleChannelPreference record);

    List<UserArticleChannelPreference> selectByExample(UserArticleChannelPreferenceExample example);

    UserArticleChannelPreference selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserArticleChannelPreference record, @Param("example") UserArticleChannelPreferenceExample example);

    int updateByExample(@Param("record") UserArticleChannelPreference record, @Param("example") UserArticleChannelPreferenceExample example);

    int updateByPrimaryKeySelective(UserArticleChannelPreference record);

    int updateByPrimaryKey(UserArticleChannelPreference record);
}