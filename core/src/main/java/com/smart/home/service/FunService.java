package com.smart.home.service;

import com.smart.home.enums.FunCategoryEnum;
import com.smart.home.modules.product.entity.ProductCommentFunHistory;
import com.smart.home.modules.product.service.ProductCommentFunHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/24
 **/
@Service
public class FunService {

    @Autowired
    private ProductCommentFunHistoryService productCommentFunHistoryService;

    /**
     * 增加一条有趣历史记录
     * @param funCategoryEnum 有趣分类
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void fun(FunCategoryEnum funCategoryEnum, Long userId, Long primaryKey) {
        switch (funCategoryEnum) {
            case PRODUCT_COMMENT:
                createCommentHistory(userId, primaryKey, 0);
                break;
            case PRODUCT_REPLY:
                createCommentHistory(userId, primaryKey, 1);
                break;
            default:
                break;
        }
    }

    private void createCommentHistory(Long userId, Long primaryKey, Integer category) {
        ProductCommentFunHistory productCommentFunHistory  = new ProductCommentFunHistory();
        productCommentFunHistory.withCreatedTime(new Date())
                .withCategory(category)
                .withSourceId(primaryKey)
                .withUserId(userId);
        productCommentFunHistoryService.create(productCommentFunHistory);
    }

    /**
     * 取消一条有趣历史记录
     * @param funCategoryEnum 有趣分类
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void cancelFun(FunCategoryEnum funCategoryEnum, Long userId, Long primaryKey) {
        switch (funCategoryEnum) {
            case PRODUCT_COMMENT:
                productCommentFunHistoryService.unfunCommnet(userId, primaryKey);
                break;
            case PRODUCT_REPLY:
                productCommentFunHistoryService.unfunCommnetReply(userId, primaryKey);
                break;
            default:
                break;
        }
    }

}
