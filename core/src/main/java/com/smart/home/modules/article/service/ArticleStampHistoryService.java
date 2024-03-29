package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleStampHistoryMapper;
import com.smart.home.modules.article.entity.ArticleStampHistory;
import com.smart.home.modules.article.entity.ArticleStampHistoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class ArticleStampHistoryService {

    @Resource
    ArticleStampHistoryMapper articleStampHistoryMapper;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleService articleService;

    public boolean create(ArticleStampHistory articleStampHistory) {
        boolean success = false;
        articleStampHistory.setCreatedTime(new Date());
        int affectRow = 0;
        try {
            affectRow = articleStampHistoryMapper.insertSelective(articleStampHistory);
        } catch (Throwable e) {
            return false;
        }
        if (affectRow > 0) {
            // 增加点赞数量
            if (articleStampHistory.getType() == 0) {
                articleService.increaseStampCount(articleStampHistory.getSourceId());
            }
            if (articleStampHistory.getType() == 1) {
                articleCommentService.increaseStampCount(articleStampHistory.getSourceId());
            }
            success = true;
        }
        return success;
    }

    public void unstampArticle(Long userId, Long id) {
        ArticleStampHistoryExample example = new ArticleStampHistoryExample();
        example.createCriteria().andSourceIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(0);
        if (articleStampHistoryMapper.deleteByExample(example) > 0) {
            articleService.decreaseStampCount(id);
        }
    }

    public void unstampArticleComment(Long userId, Long id) {
        ArticleStampHistoryExample example = new ArticleStampHistoryExample();
        example.createCriteria().andSourceIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(1);
        if (articleStampHistoryMapper.deleteByExample(example) > 0) {
            articleCommentService.decreaseStampCount(id);
        }
    }

    public long countStamp(Long userId, Long id) {
        ArticleStampHistoryExample example = new ArticleStampHistoryExample();
        example.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userId).andTypeEqualTo(0);
        return articleStampHistoryMapper.countByExample(example);
    }

    public List<ArticleStampHistory> selectByPage(ArticleStampHistory articleStampHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleStampHistoryExample example = new ArticleStampHistoryExample();
        ArticleStampHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleStampHistoryMapper.selectByExample(example);
    }

    public ArticleStampHistory findById(Long id) {
        ArticleStampHistory articleStampHistory = articleStampHistoryMapper.selectByPrimaryKey(id);
        return articleStampHistory;
    }

}
