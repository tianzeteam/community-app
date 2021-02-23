package com.smart.home.modules.article.dao;

import com.smart.home.modules.article.entity.ArticleLikeHistory;
import com.smart.home.modules.article.entity.ArticleLikeHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleLikeHistoryMapper {
    long countByExample(ArticleLikeHistoryExample example);

    int deleteByExample(ArticleLikeHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleLikeHistory record);

    int insertSelective(ArticleLikeHistory record);

    List<ArticleLikeHistory> selectByExample(ArticleLikeHistoryExample example);

    ArticleLikeHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleLikeHistory record, @Param("example") ArticleLikeHistoryExample example);

    int updateByExample(@Param("record") ArticleLikeHistory record, @Param("example") ArticleLikeHistoryExample example);

    int updateByPrimaryKeySelective(ArticleLikeHistory record);

    int updateByPrimaryKey(ArticleLikeHistory record);
}