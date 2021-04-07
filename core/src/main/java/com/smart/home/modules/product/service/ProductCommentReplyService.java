package com.smart.home.modules.product.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.cloud.qcloud.auditor.ContentAuditor;
import com.smart.home.cloud.qcloud.auditor.ContentAuditorResult;
import com.smart.home.cloud.qcloud.auditor.ImageAuditor;
import com.smart.home.cloud.qcloud.auditor.ImageAuditorResult;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import com.smart.home.cloud.qcloud.enums.ImageAuditorSuggestionEnum;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.modules.product.dao.ProductCommentMapper;
import com.smart.home.modules.product.dao.ProductCommentReplyMapper;
import com.smart.home.modules.product.entity.ProductCommentReply;
import com.smart.home.modules.product.entity.ProductCommentReplyExample;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author jason
 **/
@Service
public class ProductCommentReplyService {

    @Resource
    ProductCommentReplyMapper productCommentReplyMapper;
    @Resource
    ProductCommentMapper productCommentMapper;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private SysDictService sysDictService;

    public void increaseLikeCount(Long id) {
        productCommentReplyMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        productCommentReplyMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        productCommentReplyMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        productCommentReplyMapper.decreaseStampCount(id);
    }

    public void increaseFunCount(Long id) {
        productCommentReplyMapper.increaseFunCount(id);
    }

    public void decreaseFunCount(Long id) {
        productCommentReplyMapper.decreaseFunCount(id);
    }

    public List<ProductCommentReply> queryCommentDetailReplyByPage(Long userId, Long productCommentId, Long pid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productCommentReplyMapper.queryCommentDetailReplyByPage(userId, productCommentId, pid);
    }

    public void create(Long userId, String details, Long productCommentId, Long pid) {
        ProductCommentReply productCommentReply = new ProductCommentReply();
        productCommentReply.withCommentId(productCommentId)
                .withCreatedTime(new Date())
                .withDetails(details)
                .withLikeCount(0)
                .withFunCount(0)
                .withPid(pid)
                .withRevision(0)
                .withStampCount(0)
                .withUserId(userId);
        UserAccount userAccount = productCommentReplyMapper.findUserAccountByCommentId(productCommentId);
        productCommentReply.setToUserId(userAccount.getId());
        productCommentReply.setToUserName(userAccount.getNickName());
        productCommentReplyMapper.insertSelective(productCommentReply);
        CompletableFuture.runAsync(()->{
            // 增加一个回复数量
            productCommentMapper.increaseReplyCount(productCommentId);
        });
        long id = productCommentReply.getId();
        processAutoAudit(userId, details, id, null);
    }

    public Long countReplyByDate(Date startDate, Date endDate) {
        ProductCommentReplyExample example  = new ProductCommentReplyExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate);
        return productCommentReplyMapper.countByExample(example);
    }

    private void processAutoAudit(Long loginUserId, String details, long id, List<String> imageList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean contentPass = false;
                ContentAuditorResult contentAuditorResult = ContentAuditor.auditorResult(details);
                if (contentAuditorResult == null) {
                    // 机审文本失败，进入人工审核
                    productCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                    return;
                }
                if (contentAuditorResult.getContentAuditorEvilEnum() == ContentAuditorEvilEnum.NORMAL) {
                    // 机审成功， 标记为成功， 还要等图片审核结果
                    contentPass = true;
                }
                // 图片审核，如果有的话
                boolean hasImage = false;
                ImageAuditorResult imageAuditorResult = null;
                if (!CollectionUtils.isEmpty(imageList)) {
                    hasImage = true;
                    for (String image : imageList) {
                        imageAuditorResult = ImageAuditor.auditorResult(image);
                        if (imageAuditorResult == null) {
                            break;
                        }
                        if (imageAuditorResult.getImageAuditorSuggestionEnum() != ImageAuditorSuggestionEnum.Pass) {
                            break;
                        }
                    }
                    if (Objects.isNull(imageAuditorResult)) {
                        // 机审图片失败，进入人工审核
                        productCommentReplyMapper.updateAutoAuditFlag4ImageAudit(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                        return;
                    }
                    if (imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Pass) {
                        // 图片机审成功 加上 文本审核成功
                        if (contentPass) {
                            // 判断机审核成功是否需要自动成功
                            if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                                productCommentReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                                return;
                            } else {
                                // 还是要进入人工审核的
                                productCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步人工审核");
                                return;
                            }
                        }
                    }
                } else {
                    // 没有图片的话判断机审结果，成功的话直接更新成成功
                    if (contentPass) {
                        // 判断机审核成功是否需要自动成功
                        if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                            productCommentReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                            return;
                        } else {
                            // 还是要进入人工审核的
                            productCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步人工审核");
                            return;
                        }
                    }
                }

                // 机器审核不通过，文本异常
                // 先看看建议通过不通过
                ContentAuditorSuggestionEnum contentAuditorSuggestionEnum = contentAuditorResult.getContentAuditorSuggestionEnum();
                if (contentAuditorSuggestionEnum == null
                        || ContentAuditorSuggestionEnum.Block == contentAuditorSuggestionEnum
                        || ContentAuditorSuggestionEnum.Review == contentAuditorSuggestionEnum) {
                    productCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), contentAuditorResult.getContentAuditorEvilTypeEnum().getDesc());
                    List<String> keywordsList = contentAuditorResult.getKeywordsList();
                    if (!CollectionUtils.isEmpty(keywordsList)) {
                        productCommentReplyMapper.updateHitSensitiveCount(id, keywordsList.size());
                        // 增加用户的总敏感词数量
                        userDataService.increaseHitSensitiveCount(loginUserId, keywordsList.size());
                    }
                    userDataService.increaseTextExceptionCount(loginUserId);
                } else if (ContentAuditorSuggestionEnum.Normal == contentAuditorSuggestionEnum) {
                    // 正常，视为通过
                    contentPass = true;
                }
                if (hasImage) {
                    // 机器审核不通过，图片异常
                    if (imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Block
                            || imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Review) {
                        String reason = imageAuditorResult.getImageAuditorLabelEnum().getDesc();
                        if (contentPass) {
                            productCommentReplyMapper.updateAutoAuditFlag4ImageAudit(id, AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), reason);
                        } else {
                            productCommentReplyMapper.updateAutoAuditFlag4ImageAudit(id, AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode(), reason);
                        }
                        userDataService.increaseImageExceptionCount(loginUserId);
                    }
                } else if (contentPass) {
                    // 判断机审核成功是否需要自动成功
                    if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                        productCommentReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                    } else  {
                        // 还是要进入人工审核的
                        productCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步人工审核");
                        return;
                    }
                }
            }
        }).start();
    }
}
