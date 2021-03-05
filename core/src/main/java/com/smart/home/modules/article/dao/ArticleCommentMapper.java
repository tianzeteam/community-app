package com.smart.home.modules.article.dao;

import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleCommentMapper {
    long countByExample(ArticleCommentExample example);

    int deleteByExample(ArticleCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    List<ArticleComment> selectByExample(ArticleCommentExample example);

    ArticleComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByExample(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    int increaseLikeCount(@Param("id") Long id);

    int decreaseLikeCount(@Param("id") Long id);

    int increaseStampCount(@Param("id") Long id);

    int decreaseStampCount(@Param("id") Long id);

    List<ArticleComment> queryCommentByPageNoLogin(@Param("articleId") Long articleId);

    List<ArticleComment> queryCommentByPageWhenLogin(@Param("userId") Long userId,@Param("articleId") Long articleId);

    int updateAutoAuditFlag(@Param("id") Long id,@Param("autoAuditFlag") int autoAuditFlag,@Param("textExceptionReason") String textExceptionReason);

    int updateAutoAuditFlagAndAuditFlag(@Param("id") Long id,@Param("autoAuditFlag") int autoAuditFlag,@Param("auditFlag") int auditFlag);

    int updateHitSensitiveCount(@Param("id") long id,@Param("count") int count);
}