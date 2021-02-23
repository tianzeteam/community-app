package com.smart.home.modules.article.dao;

import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.article.entity.ArticleChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleChannelMapper {
    long countByExample(ArticleChannelExample example);

    int deleteByExample(ArticleChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleChannel record);

    int insertSelective(ArticleChannel record);

    List<ArticleChannel> selectByExample(ArticleChannelExample example);

    ArticleChannel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleChannel record, @Param("example") ArticleChannelExample example);

    int updateByExample(@Param("record") ArticleChannel record, @Param("example") ArticleChannelExample example);

    int updateByPrimaryKeySelective(ArticleChannel record);

    int updateByPrimaryKey(ArticleChannel record);
}