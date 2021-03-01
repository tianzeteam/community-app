package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductParamValue;
import com.smart.home.modules.product.entity.ProductParamValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductParamValueMapper {
    long countByExample(ProductParamValueExample example);

    int deleteByExample(ProductParamValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductParamValue record);

    int insertSelective(ProductParamValue record);

    List<ProductParamValue> selectByExample(ProductParamValueExample example);

    ProductParamValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductParamValue record, @Param("example") ProductParamValueExample example);

    int updateByExample(@Param("record") ProductParamValue record, @Param("example") ProductParamValueExample example);

    int updateByPrimaryKeySelective(ProductParamValue record);

    int updateByPrimaryKey(ProductParamValue record);

    long countByParamId(@Param("paramId") int paramId);

    int deleteByProductId(@Param("productId") Integer productId);
}