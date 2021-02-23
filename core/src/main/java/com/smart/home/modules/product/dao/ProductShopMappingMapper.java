package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductShopMapping;
import com.smart.home.modules.product.entity.ProductShopMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductShopMappingMapper {
    long countByExample(ProductShopMappingExample example);

    int deleteByExample(ProductShopMappingExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductShopMapping record);

    int insertSelective(ProductShopMapping record);

    List<ProductShopMapping> selectByExample(ProductShopMappingExample example);

    ProductShopMapping selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductShopMapping record, @Param("example") ProductShopMappingExample example);

    int updateByExample(@Param("record") ProductShopMapping record, @Param("example") ProductShopMappingExample example);

    int updateByPrimaryKeySelective(ProductShopMapping record);

    int updateByPrimaryKey(ProductShopMapping record);
}