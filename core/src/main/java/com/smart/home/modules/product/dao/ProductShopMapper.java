package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductShop;
import com.smart.home.modules.product.entity.ProductShopExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductShopMapper {
    long countByExample(ProductShopExample example);

    int deleteByExample(ProductShopExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductShop record);

    int insertSelective(ProductShop record);

    List<ProductShop> selectByExample(ProductShopExample example);

    ProductShop selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductShop record, @Param("example") ProductShopExample example);

    int updateByExample(@Param("record") ProductShop record, @Param("example") ProductShopExample example);

    int updateByPrimaryKeySelective(ProductShop record);

    int updateByPrimaryKey(ProductShop record);
}