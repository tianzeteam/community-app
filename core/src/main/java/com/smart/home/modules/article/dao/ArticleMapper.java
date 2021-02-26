package com.smart.home.modules.article.dao;

import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleExample;
import java.util.List;
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
}