package com.smart.home.modules.article.dao;

import com.smart.home.modules.article.entity.ArticleProductMapping;
import com.smart.home.modules.article.entity.ArticleProductMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleProductMappingMapper {
    long countByExample(ArticleProductMappingExample example);

    int deleteByExample(ArticleProductMappingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleProductMapping record);

    int insertSelective(ArticleProductMapping record);

    List<ArticleProductMapping> selectByExample(ArticleProductMappingExample example);

    ArticleProductMapping selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleProductMapping record, @Param("example") ArticleProductMappingExample example);

    int updateByExample(@Param("record") ArticleProductMapping record, @Param("example") ArticleProductMappingExample example);

    int updateByPrimaryKeySelective(ArticleProductMapping record);

    int updateByPrimaryKey(ArticleProductMapping record);
}