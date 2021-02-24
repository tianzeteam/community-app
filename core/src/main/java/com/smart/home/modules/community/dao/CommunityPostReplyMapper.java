package com.smart.home.modules.community.dao;

import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.entity.CommunityPostReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityPostReplyMapper {
    long countByExample(CommunityPostReplyExample example);

    int deleteByExample(CommunityPostReplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommunityPostReply record);

    int insertSelective(CommunityPostReply record);

    List<CommunityPostReply> selectByExample(CommunityPostReplyExample example);

    CommunityPostReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommunityPostReply record, @Param("example") CommunityPostReplyExample example);

    int updateByExample(@Param("record") CommunityPostReply record, @Param("example") CommunityPostReplyExample example);

    int updateByPrimaryKeySelective(CommunityPostReply record);

    int updateByPrimaryKey(CommunityPostReply record);

    int increaseLikeCount(@Param("id") Long id);

    int decreaseLikeCount(@Param("id") Long id);

    int increaseStampCount(@Param("id") Long id);

    int decreaseStampCount(@Param("id") Long id);

}