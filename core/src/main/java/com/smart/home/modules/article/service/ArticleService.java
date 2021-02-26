package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.ArticleCategoryEnum;
import com.smart.home.modules.article.dao.ArticleMapper;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ArticleService {

    @Resource
    ArticleMapper articleMapper;

    public int create(Article article) {
        article.setCreatedTime(new Date());
        article.setAuditState(AuditStatusEnum.WAIT_AUDIT.getCode());
        article.setRecommendFlag(YesNoEnum.NO.getCode());
        article.setTopFlag(YesNoEnum.NO.getCode());
        article.setTestFlag(YesNoEnum.NO.getCode());
        article.setLikeCount(0);
        article.setStampCount(0);
        article.setCommentCount(0);
        article.setVisitCount(0);
        article.setReportCount(0);
        article.setRevision(0);
        article.setCollectCount(0);
        return articleMapper.insertSelective(article);
    }

    public int update(Article article) {
        article.setUpdatedTime(new Date());
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    public int deleteById(Long id) {
        return articleMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 软删除
            articleMapper.updateState(id, RecordStatusEnum.DELETE.getStatus());
        }
    }

    public List<Article> selectByPage(Article article, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleMapper.selectByExample(example);
    }

    public Article findById(Long id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    public void increaseCollectCount(Long id) {
        articleMapper.increaseCollectCount(id);
    }
    public void decreaseCollectCount(Long id) {
        articleMapper.decreaseCollectCount(id);
    }

    public Long findAuthorById(Long id) {
        return articleMapper.findAuthorById(id);
    }

    /**
     * 产品页面的评测tab下的评测列表数据
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<Article> queryTestForProductByPage(Integer productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.queryTestForProductByPage(productId);
    }
}
