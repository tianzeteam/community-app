package com.smart.home.service;

import com.smart.home.common.exception.ServiceException;
import com.smart.home.enums.LikeCategoryEnum;
import com.smart.home.modules.article.entity.ArticleLikeHistory;
import com.smart.home.modules.article.service.ArticleLikeHistoryService;
import com.smart.home.modules.community.entity.CommunityLikeHistory;
import com.smart.home.modules.community.service.CommunityLikeHistoryService;
import com.smart.home.modules.product.entity.ProductCommentLikeHistory;
import com.smart.home.modules.product.service.ProductCommentLikeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/24
 **/
@Service
public class LikeService {

    @Autowired
    private ArticleLikeHistoryService articleLikeHistoryService;
    @Autowired
    private ProductCommentLikeHistoryService productCommentLikeHistoryService;
    @Autowired
    private CommunityLikeHistoryService communityLikeHistoryService;

    /**
     * 增加一条赞历史记录
     * @param likeCategoryEnum 赞分类
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void like(LikeCategoryEnum likeCategoryEnum, Long userId, Long primaryKey) throws ServiceException {
        switch (likeCategoryEnum) {
            case ARTICLE:
                createArticleLikeHistory(userId, primaryKey, 0);
                break;
            case ARTICLE_COMMENT:
                createArticleLikeHistory(userId, primaryKey, 1);
                break;
            case PRODUCT_COMMENT:
                createProductCommentLikeHistory(userId, primaryKey, 0);
                break;
            case PRODUCT_REPLY:
                createProductCommentLikeHistory(userId, primaryKey, 1);
                break;
            case POST:
                createPostLikeHistory(userId, primaryKey, 0);
                break;
            case POST_REPLY:
                createPostLikeHistory(userId, primaryKey, 1);
                break;
            default:
                break;
        }
    }

    /**
     * 取消一条赞历史记录
     * @param likeCategoryEnum 赞分类
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void cancelLike(LikeCategoryEnum likeCategoryEnum, Long userId, Long primaryKey) {
        switch (likeCategoryEnum) {
            case ARTICLE:
                articleLikeHistoryService.unlikeArticle(userId, primaryKey);
                break;
            case ARTICLE_COMMENT:
                articleLikeHistoryService.unlikeArticleComment(userId, primaryKey);
                break;
            case PRODUCT_COMMENT:
                productCommentLikeHistoryService.unlikeComment(userId, primaryKey);
                break;
            case PRODUCT_REPLY:
                productCommentLikeHistoryService.unlikeCommentReply(userId, primaryKey);
                break;
            case POST:
                communityLikeHistoryService.unlikePost(userId, primaryKey);
                break;
            case POST_REPLY:
                communityLikeHistoryService.unlikePostReply(userId, primaryKey);
                break;
            default:
                break;
        }
    }

    /**
     * 根据用户主键ID查询对某条记录是不是点过赞了
     * @param likeCategoryEnum 赞分类
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     * @return
     */
    public boolean hasLike(LikeCategoryEnum likeCategoryEnum, Long userId, Long primaryKey) {
        switch (likeCategoryEnum) {
            case ARTICLE:
                return articleLikeHistoryService.countLikeArticle(userId, primaryKey) > 0;
            case PRODUCT_COMMENT:
                return productCommentLikeHistoryService.countLike(userId, primaryKey, 0) > 0;
            case PRODUCT_REPLY:
                return productCommentLikeHistoryService.countLike(userId, primaryKey, 1) > 0;
            case POST:
                return communityLikeHistoryService.countLike(userId, primaryKey, 0) > 0;
            case POST_REPLY:
                return communityLikeHistoryService.countLike(userId, primaryKey, 1) > 0;
            default:
                break;
        }
        return false;
    }

    private void createPostLikeHistory(Long userId, Long primaryKey, Integer category) {
        CommunityLikeHistory communityLikeHistory = new CommunityLikeHistory();
        communityLikeHistory.withCreatedTime(new Date())
                .withPostId(primaryKey)
                .withUserId(userId)
                .withType(category);
        communityLikeHistoryService.create(communityLikeHistory);
    }

    private void createProductCommentLikeHistory(Long userId, Long primaryKey, Integer category) throws ServiceException {
        ProductCommentLikeHistory productCommentLikeHistory = new ProductCommentLikeHistory();
        productCommentLikeHistory.withCategory(category)
                .withCreatedTime(new Date())
                .withSourceId(primaryKey)
                .withUserId(userId);
        productCommentLikeHistoryService.create(productCommentLikeHistory);
    }

    private void createArticleLikeHistory(Long userId, Long primaryKey, Integer category) {
        ArticleLikeHistory articleLikeHistory = new ArticleLikeHistory();
        articleLikeHistory.withCategory(category)
                .withCreatedTime(new Date())
                .withSourceId(primaryKey)
                .withUserId(userId);
        articleLikeHistoryService.create(articleLikeHistory);
    }

}
