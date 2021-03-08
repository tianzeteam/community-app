package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.cloud.qcloud.auditor.ContentAuditor;
import com.smart.home.cloud.qcloud.auditor.ContentAuditorResult;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.dto.ContentAdminAuditSearchTO;
import com.smart.home.dto.ContentAuditAdminRecordTO;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.es.bean.ProductCommentBean;
import com.smart.home.es.service.ProductCommentEsService;
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
import java.util.Arrays;
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
    @Autowired
    private ProductCommentEsService productCommentEsServiceImpl;

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
                    productCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                    return;
                }

                if (contentAuditorResult.getContentAuditorEvilEnum() == ContentAuditorEvilEnum.NORMAL) {
                    // 机审成功， 直接通过
                    productCommentMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                    calculateProductAverageScore(productId, startCount, loginUserId,id, details);
                    return;
                }
                // 机器审核不通过，文本异常
                // 先看看建议通过不通过
                ContentAuditorSuggestionEnum contentAuditorSuggestionEnum = contentAuditorResult.getContentAuditorSuggestionEnum();
                if (contentAuditorSuggestionEnum == null
                        ||ContentAuditorSuggestionEnum.Block == contentAuditorSuggestionEnum
                        || ContentAuditorSuggestionEnum.Review == contentAuditorSuggestionEnum) {
                    productCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), contentAuditorResult.getContentAuditorEvilTypeEnum().getDesc());
                    List<String> keywordsList = contentAuditorResult.getKeywordsList();
                    if (!CollectionUtils.isEmpty(keywordsList)) {
                        productCommentMapper.updateHitSensitiveCount(id, keywordsList.size());
                        // 增加用户的总敏感词数量
                        userDataService.increaseHitSensitiveCount(loginUserId, keywordsList.size());
                    }
                    userDataService.increaseTextExceptionCount(loginUserId);
                    return;
                }
                if (ContentAuditorSuggestionEnum.Normal == contentAuditorSuggestionEnum) {
                    // 正常，视为通过
                    productCommentMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                    calculateProductAverageScore(productId, startCount, loginUserId, id, details);
                }
            }
        }).start();
    }

    /**
     * 极端产品平均分数，并更新到数据库
     * @param productId
     * @param starCount
     */
    private void calculateProductAverageScore(Integer productId, BigDecimal starCount,Long userId, Long productCommentId, String details) {
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
        productService.updateCommentScore(productId, averageScore,
                product.getFiveStarCount(), product.getFourStarCount(),product.getThreeStarCount(),
                product.getTwoStarCount(), product.getOneStarCount());
        userDataService.increaseEvaluateCount(userId);
        // 加入到es
        ProductCommentBean productCommentBean = new ProductCommentBean();
        productCommentBean.setId(productCommentId);
        productCommentBean.setCreatedTime(new Date());
        productCommentBean.setDetails(details);
        productCommentBean.setStarCount(starCount);
        productCommentBean.setProductId(productId);
        productCommentEsServiceImpl.save(productCommentBean);
    }

    public Long countWaitAudit() {
        ProductCommentExample example = new ProductCommentExample();
        example.createCriteria().andAuditFlagEqualTo(AuditStatusEnum.WAIT_AUDIT.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.WAIT_AUDIT.getCode());
        return productCommentMapper.countByExample(example);
    }

    public Long countTextException() {
        ProductCommentExample example = new ProductCommentExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return productCommentMapper.countByExample(example);
    }

    public Long countImageException() {
        ProductCommentExample example = new ProductCommentExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return productCommentMapper.countByExample(example);
    }

    public Long countHasReport() {
        ProductCommentExample example = new ProductCommentExample();
        example.createCriteria().andReportCountGreaterThan(0);
        return productCommentMapper.countByExample(example);
    }

    public Long countHitSensitive() {
        ProductCommentExample example = new ProductCommentExample();
        example.createCriteria().andHitSensitiveCountGreaterThan(0);
        return productCommentMapper.countByExample(example);
    }

    public Long countTotalNormal() {
        ProductCommentExample example = new ProductCommentExample();
        example.createCriteria().andAuditFlagEqualTo(AuditStatusEnum.APPROVED.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.APPROVE.getCode());
        return productCommentMapper.countByExample(example);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void manuallyReject(Long id) {
        Long userId = productCommentMapper.findUserIdById(id);
        userDataService.increaseManuallyExceptionCount(userId);
        productCommentMapper.updateAuditFlag(id, AuditStatusEnum.REJECT.getCode());
    }

    public void manuallyApprove(Long id) {
        int affectRow = productCommentMapper.updateAuditFlag(id, AuditStatusEnum.APPROVED.getCode());
        if (affectRow > 0) {
            ProductComment productComment = findById(id);
            Long userId = productCommentMapper.findUserIdById(id);
            // 计算平均分，并增加一次评论数量
            calculateProductAverageScore(productComment.getProductId(), productComment.getStarCount(), userId, productComment.getId(), productComment.getDetails());
        }
    }

    public List<ContentAuditAdminRecordTO> selectAllNeedAuditContent(ContentAdminAuditSearchTO to) {
        return productCommentMapper.selectAllNeedAuditContent(to);
    }

    public Long countProductCommentByDate(Date startDate, Date endDate) {
        ProductCommentExample example = new ProductCommentExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate);
        return productCommentMapper.countByExample(example);
    }
}
