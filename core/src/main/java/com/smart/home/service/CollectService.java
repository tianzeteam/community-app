package com.smart.home.service;

/**
 * @author jason
 * @date 2021/2/24
 **/
import com.smart.home.enums.CollectTypeEnum;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.modules.user.entity.UserCollect;
import com.smart.home.modules.user.service.UserCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectService {

    @Autowired
    private UserCollectService userCollectService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CommunityPostService communityPostService;

    /**
     * 添加到收藏夹
     * @param collectTypeEnum 收藏类型
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void addCollect(CollectTypeEnum collectTypeEnum, Long userId, Long primaryKey) {
        this.createCollect(collectTypeEnum.getType(), collectTypeEnum.getDesc(), userId, primaryKey);
        switch (collectTypeEnum) {
            case ARTICLE:
                articleService.increaseCollectCount(primaryKey);
                break;
            case POST:
                communityPostService.increaseCollectCount(primaryKey);
                break;
            case PRODUCT:
                productService.increaseCollectCount(primaryKey);
                break;
            default:
                break;
        }
    }

    /**
     * 取消收藏
     * @param collectTypeEnum 收藏类型
     * @param userId 用户主键ID
     * @param primaryKey 关联记录主键ID
     */
    public void cancelCollect(CollectTypeEnum collectTypeEnum, Long userId, Long primaryKey) {
        if (deleteCollect(collectTypeEnum.getType(), userId, primaryKey) > 0) {
            switch (collectTypeEnum) {
                case ARTICLE:
                    articleService.decreaseCollectCount(primaryKey);
                    break;
                case POST:
                    communityPostService.decreaseCollectCount(primaryKey);
                    break;
                case PRODUCT:
                    productService.decreaseCollectCount(primaryKey);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 根据置顶的类型和主键ID数组，查询是否存在收藏夹
     * @param collectTypeEnum 收藏类型
     * @param userId 用户主键ID
     * @param primaryKeyList 关联记录主键ID数组
     * @return
     */
    public Map<Long, Boolean> hasCollect(CollectTypeEnum collectTypeEnum, Long userId, List<Long> primaryKeyList) {
        Map<Long, Boolean> resultMap = new HashMap<>();
        List<Long> result = userCollectService.countCollect(collectTypeEnum.getType(), userId, primaryKeyList);
        int index = 0;
        for (Long count : result) {
            resultMap.put(primaryKeyList.get(0), count > 0);
            index ++;
        }
        return resultMap;
    }

    private void createCollect(Integer collectType, String collectTypeName, Long userId, Long primaryKey) {
        UserCollect userCollect = new UserCollect();
        userCollect.withCollectType(collectType)
                .withCollectTypeName(collectTypeName)
                .withCreatedBy(userId)
                .withCreatedTime(new Date())
                .withPrimaryKey(primaryKey)
                .withRevision(0)
                .withUserId(userId);
        userCollectService.create(userCollect);
    }

    private int deleteCollect(Integer collectType, Long userId, Long primaryKey) {
        return userCollectService.delete(collectType, userId, primaryKey);
    }

}
