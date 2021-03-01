package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductCategoryParam;
import com.smart.home.modules.product.entity.ProductCategoryParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCategoryParamMapper {
    long countByExample(ProductCategoryParamExample example);

    int deleteByExample(ProductCategoryParamExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCategoryParam record);

    int insertSelective(ProductCategoryParam record);

    List<ProductCategoryParam> selectByExample(ProductCategoryParamExample example);

    ProductCategoryParam selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductCategoryParam record, @Param("example") ProductCategoryParamExample example);

    int updateByExample(@Param("record") ProductCategoryParam record, @Param("example") ProductCategoryParamExample example);

    int updateByPrimaryKeySelective(ProductCategoryParam record);

    int updateByPrimaryKey(ProductCategoryParam record);

    List<Integer> findParamIdListByCategoryId(@Param("categoryId") int categoryId);
}