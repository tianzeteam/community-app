package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductCommentLikeHistory;
import com.smart.home.modules.product.entity.ProductCommentLikeHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCommentLikeHistoryMapper {
    long countByExample(ProductCommentLikeHistoryExample example);

    int deleteByExample(ProductCommentLikeHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCommentLikeHistory record);

    int insertSelective(ProductCommentLikeHistory record);

    List<ProductCommentLikeHistory> selectByExample(ProductCommentLikeHistoryExample example);

    ProductCommentLikeHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductCommentLikeHistory record, @Param("example") ProductCommentLikeHistoryExample example);

    int updateByExample(@Param("record") ProductCommentLikeHistory record, @Param("example") ProductCommentLikeHistoryExample example);

    int updateByPrimaryKeySelective(ProductCommentLikeHistory record);

    int updateByPrimaryKey(ProductCommentLikeHistory record);
}