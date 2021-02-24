package com.smart.home.modules.article.dao;

import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.article.entity.ArticleCommentReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleCommentReplyMapper {
    long countByExample(ArticleCommentReplyExample example);

    int deleteByExample(ArticleCommentReplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleCommentReply record);

    int insertSelective(ArticleCommentReply record);

    List<ArticleCommentReply> selectByExample(ArticleCommentReplyExample example);

    ArticleCommentReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleCommentReply record, @Param("example") ArticleCommentReplyExample example);

    int updateByExample(@Param("record") ArticleCommentReply record, @Param("example") ArticleCommentReplyExample example);

    int updateByPrimaryKeySelective(ArticleCommentReply record);

    int updateByPrimaryKey(ArticleCommentReply record);

    int increaseLikeCount(@Param("id") Long id);
}