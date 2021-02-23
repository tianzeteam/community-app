package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductTag;
import com.smart.home.modules.product.entity.ProductTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductTagMapper {
    long countByExample(ProductTagExample example);

    int deleteByExample(ProductTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductTag record);

    int insertSelective(ProductTag record);

    List<ProductTag> selectByExample(ProductTagExample example);

    ProductTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductTag record, @Param("example") ProductTagExample example);

    int updateByExample(@Param("record") ProductTag record, @Param("example") ProductTagExample example);

    int updateByPrimaryKeySelective(ProductTag record);

    int updateByPrimaryKey(ProductTag record);
}