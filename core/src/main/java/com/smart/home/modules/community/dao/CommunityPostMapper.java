package com.smart.home.modules.community.dao;

import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityPostMapper {
    long countByExample(CommunityPostExample example);

    int deleteByExample(CommunityPostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommunityPost record);

    int insertSelective(CommunityPost record);

    List<CommunityPost> selectByExample(CommunityPostExample example);

    CommunityPost selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommunityPost record, @Param("example") CommunityPostExample example);

    int updateByExample(@Param("record") CommunityPost record, @Param("example") CommunityPostExample example);

    int updateByPrimaryKeySelective(CommunityPost record);

    int updateByPrimaryKey(CommunityPost record);
}