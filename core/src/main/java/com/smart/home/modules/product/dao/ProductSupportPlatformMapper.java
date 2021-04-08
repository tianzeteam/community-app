package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductSupportPlatform;
import com.smart.home.modules.product.entity.ProductSupportPlatformExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductSupportPlatformMapper {
    long countByExample(ProductSupportPlatformExample example);

    int deleteByExample(ProductSupportPlatformExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductSupportPlatform record);

    int insertSelective(ProductSupportPlatform record);

    List<ProductSupportPlatform> selectByExample(ProductSupportPlatformExample example);

    ProductSupportPlatform selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductSupportPlatform record, @Param("example") ProductSupportPlatformExample example);

    int updateByExample(@Param("record") ProductSupportPlatform record, @Param("example") ProductSupportPlatformExample example);

    int updateByPrimaryKeySelective(ProductSupportPlatform record);

    int updateByPrimaryKey(ProductSupportPlatform record);
}