package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.entity.ProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    long countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    int increaseCollectCount(@Param("id") Long id);

    int decreaseCollectCount(@Param("id") Long id);

    Product queryProductCommentCountInfo(@Param("productId") Integer productId);

    long countByCategoryOne(@Param("categoryId") Integer categoryId);
    long countByCategoryTwo(@Param("categoryId") Integer categoryId);
    long countByCategoryThree(@Param("categoryId") Integer categoryId);

    long countByBrandId(@Param("brandId") Integer brandId);

    long countByShopId(@Param("shopId") int shopId);

    int saveParams(@Param("productId") Integer productId,@Param("paramsJson") String paramsJson);

    int saveShops(@Param("productId") Integer productId,@Param("shopsJson") String shopsJson);
}