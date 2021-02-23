package com.smart.home.modules.community.dao;

import com.smart.home.modules.community.entity.CommunityStampHistory;
import com.smart.home.modules.community.entity.CommunityStampHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityStampHistoryMapper {
    long countByExample(CommunityStampHistoryExample example);

    int deleteByExample(CommunityStampHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommunityStampHistory record);

    int insertSelective(CommunityStampHistory record);

    List<CommunityStampHistory> selectByExample(CommunityStampHistoryExample example);

    CommunityStampHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommunityStampHistory record, @Param("example") CommunityStampHistoryExample example);

    int updateByExample(@Param("record") CommunityStampHistory record, @Param("example") CommunityStampHistoryExample example);

    int updateByPrimaryKeySelective(CommunityStampHistory record);

    int updateByPrimaryKey(CommunityStampHistory record);
}