package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleLikeHistoryMapper;
import com.smart.home.modules.article.entity.ArticleLikeHistory;
import com.smart.home.modules.article.entity.ArticleLikeHistoryExample;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class ArticleLikeHistoryService {

    @Resource
    ArticleLikeHistoryMapper articleLikeHistoryMapper;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserDataService userDataService;

    public boolean create(ArticleLikeHistory articleLikeHistory) {
        boolean success = false;
        articleLikeHistory.setCreatedTime(new Date());
        int affectRow = articleLikeHistoryMapper.insertSelective(articleLikeHistory);
        if (affectRow > 0) {
            // 增加点赞数量
            if (articleLikeHistory.getCategory() == 0) {
                articleService.increaseLikeCount(articleLikeHistory.getSourceId());
                Long authorId = articleService.findAuthorById(articleLikeHistory.getSourceId());
                // 给作者的获赞数量加1
                userDataService.increaseLikeCount(authorId);
            }
            if (articleLikeHistory.getCategory() == 1) {
                articleCommentService.increaseLikeCount(articleLikeHistory.getSourceId());
                Long authorId = articleService.findAuthorById(articleLikeHistory.getSourceId());
                // 给作者的获赞数量加1
                userDataService.increaseLikeCount(authorId);
            }
            success = true;
        }
        return success;
    }

    public boolean unlikeArticle(Long userId, Long articleId) {
        boolean success = false;
        ArticleLikeHistoryExample example = new ArticleLikeHistoryExample();
        example.createCriteria().andUserIdEqualTo(userId).andSourceIdEqualTo(articleId).andCategoryEqualTo(0);
        int affectRow = articleLikeHistoryMapper.deleteByExample(example);
        if (affectRow > 0) {
            articleService.decreaseLikeCount(articleId);
            Long authorId = articleService.findAuthorById(articleId);
            // 给作者的获赞数量减1
            userDataService.decreaseLikeCount(authorId);
            success = true;
        }
        return success;
    }

    public void unlikeArticleComment(Long userId, Long id) {
        ArticleLikeHistoryExample example = new ArticleLikeHistoryExample();
        example.createCriteria().andUserIdEqualTo(userId).andSourceIdEqualTo(id).andCategoryEqualTo(1);
        int affectRow = articleLikeHistoryMapper.deleteByExample(example);
        if (affectRow > 0) {
            articleCommentService.decreaseLikeCount(id);
            Long authorId = articleService.findAuthorById(id);
            // 给作者的获赞数量减1
            userDataService.decreaseLikeCount(authorId);
        }
    }

    public long countLikeArticle(Long userId, Long id) {
        ArticleLikeHistoryExample example = new ArticleLikeHistoryExample();
        example.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(id).andCategoryEqualTo(0);
        return articleLikeHistoryMapper.countByExample(example);
    }

    public List<ArticleLikeHistory> selectByPage(ArticleLikeHistory articleLikeHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleLikeHistoryExample example = new ArticleLikeHistoryExample();
        ArticleLikeHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleLikeHistoryMapper.selectByExample(example);
    }

    public ArticleLikeHistory findById(Long id) {
        ArticleLikeHistory articleLikeHistory = articleLikeHistoryMapper.selectByPrimaryKey(id);
        return articleLikeHistory;
    }

}
