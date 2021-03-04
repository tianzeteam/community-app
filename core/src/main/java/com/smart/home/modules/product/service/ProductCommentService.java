package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.cloud.qcloud.auditor.ContentAuditor;
import com.smart.home.cloud.qcloud.auditor.ContentAuditorResult;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.modules.product.dao.ProductCommentMapper;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.entity.ProductCommentExample;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 **/
@Service
public class ProductCommentService {

    @Resource
    ProductCommentMapper productCommentMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;

    public int create(ProductComment productComment) {
        productComment.setCreatedTime(new Date());
        return productCommentMapper.insertSelective(productComment);
    }

    public int update(ProductComment productComment) {
        return productCommentMapper.updateByPrimaryKeySelective(productComment);
    }

    public int deleteById(Long id) {
        return productCommentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            productCommentMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ProductComment> selectByPage(ProductComment productComment, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ProductCommentExample example = new ProductCommentExample();
        ProductCommentExample.Criteria criteria = example.createCriteria();
        if (productComment.getUserId() != null) {
            criteria.andUserIdEqualTo(productComment.getUserId());
        }
        example.setOrderByClause("created_time desc");
        List<ProductComment> list = productCommentMapper.selectByExample(example);
        for (ProductComment comment : list) {
            comment.setNickName(userAccountService.findNicknameByUserId(comment.getUserId()));
        }
        return list;
    }

    public ProductComment findById(Long id) {
        ProductComment productComment = productCommentMapper.selectByPrimaryKey(id);
        return productComment;
    }

    public void increaseLikeCount(Long id) {
        productCommentMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        productCommentMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        productCommentMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        productCommentMapper.decreaseStampCount(id);
    }

    public void increaseFunCount(Long id) {
        productCommentMapper.increaseFunCount(id);
    }

    public void decreaseFunCount(Long id) {
        productCommentMapper.decreaseFunCount(id);
    }

    public List<ProductComment> queryCommentByPage(Long loginUserId, int productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productCommentMapper.queryCommentByPage(loginUserId, productId);
    }

    public ProductComment queryCommentDetailHead(Long userId, Long productCommentId) {
        return productCommentMapper.queryCommentDetailHead(userId, productCommentId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void create(Long loginUserId, BigDecimal startCount, String details, Integer productId) {
        ProductComment productComment = new ProductComment();
        productComment.withCreatedTime(new Date())
                .withDetails(details)
                .withFunCount(0)
                .withLikeCount(0)
                .withProductId(productId)
                .withReplyCount(0)
                .withStampCount(0)
                .withStarCount(startCount)
                .withUserId(loginUserId);
        productCommentMapper.insertSelective(productComment);
        final long id = productComment.getId();
        // 对评论进行审核，审核通过后计算平均分
        processAutoAudit(loginUserId, startCount, details, productId, id);
    }

    public List<ProductComment> queryViaProductIdByPage(Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productCommentMapper.queryViaProductIdByPage(productId);
    }

    private void processAutoAudit(Long loginUserId, BigDecimal startCount, String details, Integer productId, long id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentAuditorResult contentAuditorResult = ContentAuditor.auditorResult(details);
                if (contentAuditorResult == null) {
                    // 机审失败，进入人工审核
                    productCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode());
                    return;
                }

                if (contentAuditorResult.getContentAuditorEvilEnum() == ContentAuditorEvilEnum.NORMAL) {
                    // 机审成功， 直接通过
                    productCommentMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                    calculateProductAverageScore(productId, startCount);
                    return;
                }
                // 机器审核不通过，文本异常
                // 先看看建议通过不通过
                ContentAuditorSuggestionEnum contentAuditorSuggestionEnum = contentAuditorResult.getContentAuditorSuggestionEnum();
                if (contentAuditorSuggestionEnum == null
                        ||ContentAuditorSuggestionEnum.Block == contentAuditorSuggestionEnum
                        || ContentAuditorSuggestionEnum.Review == contentAuditorSuggestionEnum) {
                    productCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode());
                    List<String> keywordsList = contentAuditorResult.getKeywordsList();
                    if (!CollectionUtils.isEmpty(keywordsList)) {
                        productCommentMapper.updateHitSensitiveCount(id, keywordsList.size());
                        // 增加用户的总敏感词数量
                        userDataService.increaseHitSensitiveCount(loginUserId, keywordsList.size());
                    }
                    return;
                }
                if (ContentAuditorSuggestionEnum.Normal == contentAuditorSuggestionEnum) {
                    // 正常，视为通过
                    productCommentMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                    calculateProductAverageScore(productId, startCount);
                }
            }
        }).start();
    }

    /**
     * 极端产品平均分数，并更新到数据库
     * @param productId
     * @param starCount
     */
    private void calculateProductAverageScore(Integer productId, BigDecimal starCount) {
        // 如果评价合法，计算其他数据
        Product product = productService.findById(productId);
        // 超出产品，计算产品的平均分
        BigDecimal averageScore = product.getAverageScore();
        if (Objects.isNull(averageScore)) {
            averageScore = new BigDecimal(10);
        }
        averageScore = starCount.multiply(new BigDecimal(2)).add(averageScore).divide(new BigDecimal(2), 1, RoundingMode.HALF_UP);
        if (starCount.compareTo(new BigDecimal(4)) > 0) {
            product.setFiveStarCount(product.getFiveStarCount() + 1);
        } else if (starCount.compareTo(new BigDecimal(3)) > 0) {
            product.setFourStarCount(product.getFourStarCount() + 1);
        } else if (starCount.compareTo(new BigDecimal(2)) > 0) {
            product.setThreeStarCount(product.getThreeStarCount() + 1);
        } else if (starCount.compareTo(new BigDecimal(1)) > 0) {
            product.setTwoStarCount(product.getTwoStarCount() + 1);
        } else {
            product.setOneStarCount(product.getOneStarCount() + 1);
        }
        product.setAverageScore(averageScore);
        productService.update(product);
    }
}
