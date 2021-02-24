package com.smart.home.service;

import com.smart.home.enums.LikeCategoryEnum;
import com.smart.home.modules.article.entity.ArticleStampHistory;
import com.smart.home.modules.article.service.ArticleStampHistoryService;
import com.smart.home.modules.community.entity.CommunityStampHistory;
import com.smart.home.modules.community.service.CommunityStampHistoryService;
import com.smart.home.modules.product.entity.ProductCommentStampHistory;
import com.smart.home.modules.product.service.ProductCommentStampHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/24
 **/
@Service
public class StampService {

    @Autowired
    private ArticleStampHistoryService articleStampHistoryService;
    @Autowired
    private ProductCommentStampHistoryService productCommentStampHistoryService;
    @Autowired
    private CommunityStampHistoryService communityStampHistoryService;

    /**
     * 增加一条赞历史记录
     * @param likeCategoryEnum 赞分类
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void stamp(LikeCategoryEnum likeCategoryEnum, Long userId, Long primaryKey) {
        switch (likeCategoryEnum) {
            case ARTICLE:
                createArticleStampHistory(userId, primaryKey, 0);
                break;
            case PRODUCT_COMMENT:
                createProductCommentStampHistory(userId, primaryKey, 0);
                break;
            case PRODUCT_REPLY:
                createProductCommentStampHistory(userId, primaryKey, 1);
                break;
            case POST:
                createPostStampHistory(userId, primaryKey, 0);
                break;
            case POST_REPLY:
                createPostStampHistory(userId, primaryKey, 1);
                break;
            default:
                break;
        }
    }

    private void createPostStampHistory(Long userId, Long primaryKey, Integer category) {
        CommunityStampHistory communityStampHistory = new CommunityStampHistory();
        communityStampHistory.withCreatedTime(new Date())
                .withPostId(primaryKey)
                .withUserId(userId)
                .withType(category);
        communityStampHistoryService.create(communityStampHistory);
    }

    private void createProductCommentStampHistory(Long userId, Long primaryKey, Integer category) {
        ProductCommentStampHistory productCommentStampHistory = new ProductCommentStampHistory();
        productCommentStampHistory.withCategory(category)
                .withCreatedTime(new Date())
                .withSourceId(primaryKey)
                .withUserId(userId);
        productCommentStampHistoryService.create(productCommentStampHistory);
    }

    private void createArticleStampHistory(Long userId, Long primaryKey, Integer category) {
        ArticleStampHistory articleStampHistory = new ArticleStampHistory();
        articleStampHistory.withType(category)
                .withCreatedTime(new Date())
                .withSourceId(primaryKey)
                .withUserId(userId);
        articleStampHistoryService.create(articleStampHistory);
    }

    /**
     * 取消一条赞历史记录
     * @param likeCategoryEnum 赞分类
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void cancelStamp(LikeCategoryEnum likeCategoryEnum, Long userId, Long primaryKey) {
        switch (likeCategoryEnum) {
            case ARTICLE:
                articleStampHistoryService.unstampArticle(userId, primaryKey);
                break;
            case PRODUCT_COMMENT:
                productCommentStampHistoryService.unstampComment(userId, primaryKey);
                break;
            case PRODUCT_REPLY:
                productCommentStampHistoryService.unstampCommentReply(userId, primaryKey);
                break;
            case POST:
                communityStampHistoryService.unstampPost(userId, primaryKey);
                break;
            case POST_REPLY:
                communityStampHistoryService.unstampPostReply(userId, primaryKey);
                break;
            default:
                break;
        }
    }

}
