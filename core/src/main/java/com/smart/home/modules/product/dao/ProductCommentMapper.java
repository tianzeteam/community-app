package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.entity.ProductCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCommentMapper {
    long countByExample(ProductCommentExample example);

    int deleteByExample(ProductCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductComment record);

    int insertSelective(ProductComment record);

    List<ProductComment> selectByExample(ProductCommentExample example);

    ProductComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductComment record, @Param("example") ProductCommentExample example);

    int updateByExample(@Param("record") ProductComment record, @Param("example") ProductCommentExample example);

    int updateByPrimaryKeySelective(ProductComment record);

    int updateByPrimaryKey(ProductComment record);
}