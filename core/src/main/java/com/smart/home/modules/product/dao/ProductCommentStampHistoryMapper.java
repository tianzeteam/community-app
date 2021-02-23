package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductCommentStampHistory;
import com.smart.home.modules.product.entity.ProductCommentStampHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCommentStampHistoryMapper {
    long countByExample(ProductCommentStampHistoryExample example);

    int deleteByExample(ProductCommentStampHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCommentStampHistory record);

    int insertSelective(ProductCommentStampHistory record);

    List<ProductCommentStampHistory> selectByExample(ProductCommentStampHistoryExample example);

    ProductCommentStampHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductCommentStampHistory record, @Param("example") ProductCommentStampHistoryExample example);

    int updateByExample(@Param("record") ProductCommentStampHistory record, @Param("example") ProductCommentStampHistoryExample example);

    int updateByPrimaryKeySelective(ProductCommentStampHistory record);

    int updateByPrimaryKey(ProductCommentStampHistory record);
}