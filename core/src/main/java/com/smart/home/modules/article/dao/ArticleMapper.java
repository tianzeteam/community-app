package com.smart.home.modules.article.dao;

import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.enums.ArticleRecommendTypeEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleExample;
import java.util.List;

import com.smart.home.modules.article.po.UserIdAndCategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    long countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    int increaseCollectCount(@Param("id") Long id);

    int decreaseCollectCount(@Param("id") Long id);

    Long findAuthorById(@Param("id") Long id);

    List<Article> queryTestForProductByPage(@Param("productId") Integer productId);

    long countArticleByChannelId(@Param("channelId") Long channelId);

    int updateState(@Param("id") Long id,@Param("state") Integer state);

    List<Article> selectTitleImageCreateIimeByPage(@Param("userId") Long userId,@Param("state") Integer state,@Param("auditState") Integer auditState);

    Article queryDetailByIdNoLogin(@Param("articleId") Long articleId);

    Article queryDetailByIdWhenLogin(@Param("articleId") Long articleId,@Param("userId") Long userId);

    int increaseCommentCount(@Param("articleId") Long articleId);

    int increaseVisitCount(@Param("articleId") Long articleId);

    int increaseLikeCount(@Param("id") Long id);
    int decreaseLikeCount(@Param("id") Long id);
    int increaseStampCount(@Param("id") Long id);
    int decreaseStampCount(@Param("id") Long id);


    int increaseReportCount(@Param("id") Long id);

    int updateTopFlag(@Param("id") Long id,@Param("flag") int flag);

    int updateRecommendType(@Param("id") Long id,@Param("flag") int type);

    List<Article> selectArticleCardByPage(@Param("channelId") Integer channelId);

    List<Article> queryViaProductIdByPage(@Param("productId") Integer productId);

    List<Article> queryViaUserIdByPage(@Param("userId") Long userId);

    List<Article> queryDraftViaUserIdByPage(@Param("userId") Long userId);

    Article queryBigImageCard(@Param("topFlag") Integer topFlag, @Param("recommendType") Integer recommendType, @Param("channelId") Integer channelId, @Param("recommendFlag") Integer recommendFlag);

    List<Article> queryIndexArticleCard(@Param("topFlag") Integer topFlag, @Param("recommendType") Integer recommendType, @Param("channelId") Integer channelId, @Param("recommendFlag") Integer recommendFlag);

    UserIdAndCategoryPO findUserIdAndCategory(@Param("id") Long id);
}