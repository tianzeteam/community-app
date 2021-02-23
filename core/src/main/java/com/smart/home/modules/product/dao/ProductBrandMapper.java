package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductBrand;
import com.smart.home.modules.product.entity.ProductBrandExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductBrandMapper {
    long countByExample(ProductBrandExample example);

    int deleteByExample(ProductBrandExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductBrand record);

    int insertSelective(ProductBrand record);

    List<ProductBrand> selectByExample(ProductBrandExample example);

    ProductBrand selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductBrand record, @Param("example") ProductBrandExample example);

    int updateByExample(@Param("record") ProductBrand record, @Param("example") ProductBrandExample example);

    int updateByPrimaryKeySelective(ProductBrand record);

    int updateByPrimaryKey(ProductBrand record);
}