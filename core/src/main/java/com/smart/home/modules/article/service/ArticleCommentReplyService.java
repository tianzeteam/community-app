package com.smart.home.modules.article.service;

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
import com.smart.home.modules.article.dao.ArticleCommentMapper;
import com.smart.home.modules.article.dao.ArticleCommentReplyMapper;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author jason
 **/
@Service
public class ArticleCommentReplyService {

    @Resource
    ArticleCommentReplyMapper articleCommentReplyMapper;
    @Resource
    ArticleCommentMapper articleCommentMapper;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private UserDataService userDataService;

    public ArticleCommentReply findById(Long id) {
        ArticleCommentReply articleCommentReply = articleCommentReplyMapper.selectByPrimaryKey(id);
        return articleCommentReply;
    }

    public void create(Long loginUserId, Long articleId, Long articleCommentId, String contents) {
        ArticleCommentReply articleCommentReply = new ArticleCommentReply();
        articleCommentReply.withArticleCommentId(articleCommentId)
                .withArticleId(articleId)
                .withContents(contents)
                .withCreatedTime(new Date())
                .withPid(0L)
                .withUserId(loginUserId);
        UserAccount userAccount = articleCommentMapper.findUserAccountIdById(articleCommentId);
        articleCommentReply.setToUserId(userAccount.getId());
        articleCommentReply.setToUserName(userAccount.getNickName());
        articleCommentReplyMapper.insertSelective(articleCommentReply);
        CompletableFuture.runAsync(()->{
            // 回复数量加1
            articleCommentMapper.increaseReplyCount(articleCommentId);
        });
        long id = articleCommentReply.getId();
        processAutoAudit(id, contents, loginUserId, null);
    }

    public List<ArticleCommentReply> queryCommentReplyByPageNoLogin(Long articleCommentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentReplyMapper.queryCommentReplyByPageNoLogin(articleCommentId);
    }

    public List<ArticleCommentReply> queryCommentReplyByPageWhenLogin(Long userId, Long articleCommentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentReplyMapper.queryCommentReplyByPageNoLogin(articleCommentId);
    }

    private void processAutoAudit(long id, String contents, Long loginUserId, List<String> imageList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean contentPass = false;
                ContentAuditorResult contentAuditorResult = ContentAuditor.auditorResult(contents);
                if (contentAuditorResult == null) {
                    // 机审失败，进入人工审核
                    articleCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
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
                        articleCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                        return;
                    }
                    if (imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Pass) {
                        // 图片机审成功 加上 文本审核成功
                        if (contentPass) {
                            if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                                articleCommentReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                                // 文章增加一次评论数量
                                userDataService.increaseCommentCount(loginUserId);
                                return;
                            } else {
                                // 还是需要人工审核的
                                articleCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步进行人工审核");
                                return;
                            }

                        }
                    }
                } else {
                    // 没有图片的话判断机审结果，成功的话直接更新成成功
                    if (contentPass) {
                        if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                            articleCommentReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                            userDataService.increaseCommentCount(loginUserId);
                            return;
                        } else {
                            // 还是需要人工审核的
                            articleCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步进行人工审核");
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
                    articleCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), contentAuditorResult.getContentAuditorEvilTypeEnum().getDesc());
                    List<String> keywordsList = contentAuditorResult.getKeywordsList();
                    if (!CollectionUtils.isEmpty(keywordsList)) {
                        articleCommentReplyMapper.updateHitSensitiveCount(id, keywordsList.size());
                        // 增加用户的总敏感词数量
                        userDataService.increaseHitSensitiveCount(loginUserId, keywordsList.size());
                    }
                    userDataService.increaseTextExceptionCount(loginUserId);
                    return;
                } else if (ContentAuditorSuggestionEnum.Normal == contentAuditorSuggestionEnum) {
                    contentPass = true;
                }
                if (hasImage) {
                    // 机器审核不通过，图片异常
                    if (imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Block
                            || imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Review) {
                        String reason = imageAuditorResult.getImageAuditorLabelEnum().getDesc();
                        if (contentPass) {
                            articleCommentReplyMapper.updateAutoAuditFlag4ImageAudit(id, AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), reason);
                        } else {
                            articleCommentReplyMapper.updateAutoAuditFlag4ImageAudit(id, AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode(), reason);
                        }
                        userDataService.increaseImageExceptionCount(loginUserId);
                    }
                } else if (contentPass) {
                    if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                        // 正常，视为通过
                        articleCommentReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                        userDataService.increaseCommentCount(loginUserId);
                    } else {
                        // 还是需要人工审核的
                        articleCommentReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步进行人工审核");
                        return;
                    }
                }

            }
        }).start();
    }

}
