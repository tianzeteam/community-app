package com.smart.home.modules.article.dao;

import com.smart.home.modules.article.entity.ArticleStampHistory;
import com.smart.home.modules.article.entity.ArticleStampHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleStampHistoryMapper {
    long countByExample(ArticleStampHistoryExample example);

    int deleteByExample(ArticleStampHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleStampHistory record);

    int insertSelective(ArticleStampHistory record);

    List<ArticleStampHistory> selectByExample(ArticleStampHistoryExample example);

    ArticleStampHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleStampHistory record, @Param("example") ArticleStampHistoryExample example);

    int updateByExample(@Param("record") ArticleStampHistory record, @Param("example") ArticleStampHistoryExample example);

    int updateByPrimaryKeySelective(ArticleStampHistory record);

    int updateByPrimaryKey(ArticleStampHistory record);
}