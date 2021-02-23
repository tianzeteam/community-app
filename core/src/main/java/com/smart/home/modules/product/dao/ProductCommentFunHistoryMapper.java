package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductCommentFunHistory;
import com.smart.home.modules.product.entity.ProductCommentFunHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCommentFunHistoryMapper {
    long countByExample(ProductCommentFunHistoryExample example);

    int deleteByExample(ProductCommentFunHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCommentFunHistory record);

    int insertSelective(ProductCommentFunHistory record);

    List<ProductCommentFunHistory> selectByExample(ProductCommentFunHistoryExample example);

    ProductCommentFunHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductCommentFunHistory record, @Param("example") ProductCommentFunHistoryExample example);

    int updateByExample(@Param("record") ProductCommentFunHistory record, @Param("example") ProductCommentFunHistoryExample example);

    int updateByPrimaryKeySelective(ProductCommentFunHistory record);

    int updateByPrimaryKey(ProductCommentFunHistory record);
}