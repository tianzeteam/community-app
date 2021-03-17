package com.smart.home.modules.product.dao;

import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.dto.ContentAdminAuditSearchTO;
import com.smart.home.dto.ContentAuditAdminRecordTO;
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

    int increaseLikeCount(@Param("id") Long id);

    int decreaseLikeCount(@Param("id") Long id);

    int increaseStampCount(@Param("id") Long id);

    int decreaseStampCount(@Param("id") Long id);

    int increaseFunCount(@Param("id") Long id);

    int decreaseFunCount(@Param("id") Long id);

    int increaseReplyCount(@Param("id") Long id);

    List<ProductComment> queryCommentByPage(@Param("loginUserId") Long loginUserId,@Param("productId") int productId);

    ProductComment queryCommentDetailHead(@Param("loginUserId") Long userId,@Param("productCommentId") Long productCommentId);

    List<ProductComment> queryViaProductIdByPage(@Param("productId") Integer productId);

    int updateAutoAuditFlag(@Param("id") Long id,@Param("autoAuditFlag") int autoAuditFlag, @Param("textExceptionReason") String textExceptionReason);

    int updateAutoAuditFlag4ImageAudit(@Param("id") Long id,@Param("autoAuditFlag") int autoAuditFlag, @Param("imageExceptionReason") String imageExceptionReason);

    int updateAutoAuditFlagAndAuditFlag(@Param("id") Long id,@Param("autoAuditFlag") int autoAuditFlag,@Param("auditFlag") int auditFlag);

    int updateHitSensitiveCount(@Param("id") long id,@Param("count") int count);

    int updateAuditFlag(@Param("id") Long id, @Param("flag") int flag);

    Long findUserIdById(@Param("id") Long id);

    List<ContentAuditAdminRecordTO> selectAllNeedAuditContent(ContentAdminAuditSearchTO to);

    List<ProductComment> queryViaUserIdByPageWhenLogin(@Param("userId") Long userId,@Param("loginUserId") Long loginUserId);

}