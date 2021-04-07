package com.smart.home.modules.article.service;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.smart.home.cloud.qcloud.auditor.ContentAuditor;
import com.smart.home.cloud.qcloud.auditor.ContentAuditorResult;
import com.smart.home.cloud.qcloud.auditor.ImageAuditor;
import com.smart.home.cloud.qcloud.auditor.ImageAuditorResult;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import com.smart.home.cloud.qcloud.enums.ImageAuditorSuggestionEnum;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.ArticleCategoryEnum;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.modules.article.dao.ArticleCommentMapper;
import com.smart.home.modules.article.dao.ArticleMapper;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentExample;
import com.smart.home.modules.article.po.UserIdAndCategoryPO;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.modules.system.service.SysFileService;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 **/
@Service
public class ArticleCommentService {

    @Resource
    ArticleCommentMapper articleCommentMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private SysDictService sysDictService;

    @Transactional(rollbackFor = RuntimeException.class)
    public void create(Long loginUserId, Long articleId, String contents, Long articleAuthorId, List<String> imageList) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.withArticleId(articleId)
                .withContents(contents)
                .withLikeCount(0)
                .withStampCount(0)
                .withCreatedTime(new Date())
                .withUserId(loginUserId);
        articleComment.setToUserId(articleAuthorId);
        if (CollUtil.isNotEmpty(imageList)) {
            articleComment.setImages(JSON.toJSONString(imageList));
            sysFileService.syncImageFileList(imageList);
        }
        UserIdAndCategoryPO userIdAndCategoryPO = articleMapper.findUserIdAndCategory(articleId);
        Long authorId = userIdAndCategoryPO.getUserId();
        Integer articleCategory = userIdAndCategoryPO.getCategory();
        if (authorId.equals(loginUserId)) {
            articleComment.setAuthorFlag(YesNoEnum.YES.getCode());
        } else {
            articleComment.setAuthorFlag(YesNoEnum.NO.getCode());
        }
        articleComment.setArticleCategory(articleCategory);
        articleCommentMapper.insertSelective(articleComment);
        final long id = articleComment.getId();
        processAutoAudit(id, articleId, contents, loginUserId, imageList);

    }

    private void processAutoAudit(long id, Long articleId, String contents, Long loginUserId, List<String> imageList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean contentPass = false;
                ContentAuditorResult contentAuditorResult = ContentAuditor.auditorResult(contents);
                if (contentAuditorResult == null) {
                    // 机审失败，进入人工审核
                    articleCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
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
                        articleCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                        return;
                    }
                    if (imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Pass) {
                        // 图片机审成功 加上 文本审核成功
                        if (contentPass) {
                            if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                                articleCommentMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                                // 文章增加一次评论数量
                                articleMapper.increaseCommentCount(articleId);
                                userDataService.increaseCommentCount(loginUserId);
                                return;
                            } else {
                                // 还是需要人工审核的
                                articleCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步进行人工审核");
                                return;
                            }

                        }
                    }
                } else {
                    // 没有图片的话判断机审结果，成功的话直接更新成成功
                    if (contentPass) {
                        if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                            articleCommentMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                            // 文章增加一次评论数量
                            articleMapper.increaseCommentCount(articleId);
                            userDataService.increaseCommentCount(loginUserId);
                            return;
                        } else {
                            // 还是需要人工审核的
                            articleCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步进行人工审核");
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
                    articleCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), contentAuditorResult.getContentAuditorEvilTypeEnum().getDesc());
                    List<String> keywordsList = contentAuditorResult.getKeywordsList();
                    if (!CollectionUtils.isEmpty(keywordsList)) {
                        articleCommentMapper.updateHitSensitiveCount(id, keywordsList.size());
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
                            articleCommentMapper.updateAutoAuditFlag4ImageAudit(id, AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), reason);
                        } else {
                            articleCommentMapper.updateAutoAuditFlag4ImageAudit(id, AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode(), reason);
                        }
                        userDataService.increaseImageExceptionCount(loginUserId);
                    }
                } else if (contentPass) {
                    if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                        // 正常，视为通过
                        articleCommentMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                        // 文章增加一次评论数量
                        articleMapper.increaseCommentCount(articleId);
                        userDataService.increaseCommentCount(loginUserId);
                    } else {
                        // 还是需要人工审核的
                        articleCommentMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步进行人工审核");
                        return;
                    }
                }

            }
        }).start();
    }

    public int update(ArticleComment articleComment) {
        return articleCommentMapper.updateByPrimaryKeySelective(articleComment);
    }

    public int deleteById(Long id) {
        return articleCommentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            articleCommentMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ArticleComment> selectByPage(ArticleComment articleComment, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();
        if (articleComment.getUserId() != null) {
            criteria.andUserIdEqualTo(articleComment.getUserId());
        }
        example.setOrderByClause("created_time desc");
        List<ArticleComment> list = articleCommentMapper.selectByExample(example);
        for (ArticleComment comment : list) {
            comment.setNickName(userAccountService.findNicknameByUserId(comment.getUserId()));
        }
        return list;
    }

    public ArticleComment findById(Long id) {
        ArticleComment articleComment = articleCommentMapper.selectByPrimaryKey(id);
        return articleComment;
    }

    public void increaseLikeCount(Long id) {
        articleCommentMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        articleCommentMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        articleCommentMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        articleCommentMapper.decreaseStampCount(id);
    }

    public List<ArticleComment> queryCommentByPageNoLogin(Long articleId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentMapper.queryCommentByPageNoLogin(articleId);
    }

    public List<ArticleComment> queryCommentByPageWhenLogin(Long userId, Long articleId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentMapper.queryCommentByPageWhenLogin(userId, articleId);
    }

    public Long countWaitAudit() {
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria().andAuditFlagEqualTo(AuditStatusEnum.WAIT_AUDIT.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.WAIT_AUDIT.getCode());
        return articleCommentMapper.countByExample(example);
    }

    public Long countTextException() {
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return articleCommentMapper.countByExample(example);
    }

    public Long countImageException() {
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return articleCommentMapper.countByExample(example);
    }

    public Long countHasReport() {
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria().andReportCountGreaterThan(0);
        return articleCommentMapper.countByExample(example);
    }

    public Long countHitSensitive() {
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria().andHitSensitiveCountGreaterThan(0);
        return articleCommentMapper.countByExample(example);
    }

    public Long countTotalNormal() {
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria().andAuditFlagEqualTo(AuditStatusEnum.APPROVED.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.APPROVE.getCode());
        return articleCommentMapper.countByExample(example);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void manuallyReject(Long id) {
        Long userId = articleCommentMapper.findUserIdById(id);
        userDataService.increaseManuallyExceptionCount(userId);
        articleCommentMapper.updateAuditFlag(id, AuditStatusEnum.REJECT.getCode());
    }

    public void manuallyApprove(Long id) {
        int affectRow = articleCommentMapper.updateAuditFlag(id, AuditStatusEnum.APPROVED.getCode());
        if (affectRow > 0) {
            // 增加一次评论数量
            articleMapper.increaseCommentCount(id);
            Long userId = articleCommentMapper.findUserIdById(id);
            userDataService.increaseCommentCount(userId);
        }
    }

    public Long countCommentByDateAndCategory(Date startDate, Date endDate, ArticleCategoryEnum categoryEnum) {
        int category = categoryEnum.getCode();
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate).andArticleCategoryEqualTo(category);
        return articleCommentMapper.countByExample(example);
    }

    public List<ArticleComment> queryViaUserIdByPageWhenLogin(Long userId, int pageNum, int pageSize, Long loginUserId) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentMapper.queryViaUserIdByPageWhenLogin(userId, loginUserId);
    }

    public List<ArticleComment> queryViaUserIdByPageNoLogin(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentMapper.queryViaUserIdByPageNoLogin(userId);
    }

    public long countByUserId(Long userId, Long articleId) {
        ArticleCommentExample example = new ArticleCommentExample();
        example.createCriteria().andUserIdEqualTo(userId).andArticleIdEqualTo(articleId);
        return articleCommentMapper.countByExample(example);
    }
}
