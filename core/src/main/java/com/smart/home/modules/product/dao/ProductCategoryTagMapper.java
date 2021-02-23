package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductCategoryTag;
import com.smart.home.modules.product.entity.ProductCategoryTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCategoryTagMapper {
    long countByExample(ProductCategoryTagExample example);

    int deleteByExample(ProductCategoryTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategoryTag record);

    int insertSelective(ProductCategoryTag record);

    List<ProductCategoryTag> selectByExample(ProductCategoryTagExample example);

    ProductCategoryTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductCategoryTag record, @Param("example") ProductCategoryTagExample example);

    int updateByExample(@Param("record") ProductCategoryTag record, @Param("example") ProductCategoryTagExample example);

    int updateByPrimaryKeySelective(ProductCategoryTag record);

    int updateByPrimaryKey(ProductCategoryTag record);
}