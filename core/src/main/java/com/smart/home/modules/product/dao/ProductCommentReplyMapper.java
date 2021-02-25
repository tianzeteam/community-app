package com.smart.home.modules.product.dao;

import com.smart.home.modules.product.entity.ProductCommentReply;
import com.smart.home.modules.product.entity.ProductCommentReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductCommentReplyMapper {
    long countByExample(ProductCommentReplyExample example);

    int deleteByExample(ProductCommentReplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCommentReply record);

    int insertSelective(ProductCommentReply record);

    List<ProductCommentReply> selectByExample(ProductCommentReplyExample example);

    ProductCommentReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductCommentReply record, @Param("example") ProductCommentReplyExample example);

    int updateByExample(@Param("record") ProductCommentReply record, @Param("example") ProductCommentReplyExample example);

    int updateByPrimaryKeySelective(ProductCommentReply record);

    int updateByPrimaryKey(ProductCommentReply record);

    int increaseLikeCount(@Param("id") Long id);

    int decreaseLikeCount(@Param("id") Long id);

    int increaseStampCount(@Param("id") Long id);

    int decreaseStampCount(@Param("id") Long id);

    int increaseFunCount(@Param("id") Long id);

    int decreaseFunCount(@Param("id") Long id);

    List<ProductCommentReply> queryCommentDetailReplyByPage(@Param("loginUserId") Long loginUserId,@Param("productCommentId") Long productCommentId,@Param("pid") Long pid);
}