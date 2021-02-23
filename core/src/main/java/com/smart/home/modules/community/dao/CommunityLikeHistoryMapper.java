package com.smart.home.modules.community.dao;

import com.smart.home.modules.community.entity.CommunityLikeHistory;
import com.smart.home.modules.community.entity.CommunityLikeHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityLikeHistoryMapper {
    long countByExample(CommunityLikeHistoryExample example);

    int deleteByExample(CommunityLikeHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommunityLikeHistory record);

    int insertSelective(CommunityLikeHistory record);

    List<CommunityLikeHistory> selectByExample(CommunityLikeHistoryExample example);

    CommunityLikeHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommunityLikeHistory record, @Param("example") CommunityLikeHistoryExample example);

    int updateByExample(@Param("record") CommunityLikeHistory record, @Param("example") CommunityLikeHistoryExample example);

    int updateByPrimaryKeySelective(CommunityLikeHistory record);

    int updateByPrimaryKey(CommunityLikeHistory record);
}