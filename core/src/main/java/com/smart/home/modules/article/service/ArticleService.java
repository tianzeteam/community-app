package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.ArticleRecommendTypeEnum;
import com.smart.home.enums.ArticleStateEnum;
import com.smart.home.enums.AuditCategoryEnum;
import com.smart.home.modules.article.dao.ArticleMapper;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleExample;
import com.smart.home.modules.other.service.AuditHistoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * @author jason
 **/
@Service
public class ArticleService {

    @Resource
    ArticleMapper articleMapper;
    @Autowired
    private AuditHistoryService auditHistoryService;

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
        article.setOnlineStatus(RecordStatusEnum.NORMAL.getStatus());
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

    public List<Article> selectByPage(Article article, int pageNum, int pageSize, String sortType, String sortField) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        if (article.getState() != null) {
            criteria.andStateEqualTo(article.getState());
        }
        if (article.getAuditState() != null) {
            criteria.andAuditStateEqualTo(article.getAuditState());
        }
        if (article.getRecommendFlag() != null) {
            criteria.andRecommendFlagEqualTo(article.getRecommendFlag());
        }
        if (StringUtils.isBlank(sortField)) {
            sortField = "created_time";
        }
        if (StringUtils.isBlank(sortType)) {
            sortType = "desc";
        }
        example.setOrderByClause(sortField + " " + sortType);
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

    public Long countByUserIdAndState(Long userId, int state) {
        ArticleExample example = new ArticleExample();
        example.createCriteria()
                .andUserIdEqualTo(userId)
                .andStateEqualTo(state);
        return articleMapper.countByExample(example);
    }

    public List<Article> selectTitleImageCreateIimeByPage(Long userId, Integer state, Integer auditState, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.selectTitleImageCreateIimeByPage(userId, state, auditState);
    }

    /**
     * 人工审核通过
     * @param idList
     * @param userId
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void approveArticleManually(List<Long> idList, Long userId) {
        for (Long id : idList) {
            Article article = findById(id);
            // 草稿状态不做处理
            if (ArticleStateEnum.DRAFT.getState() == article.getState().intValue()) {
                continue;
            }
            // 已经审核通过的不做处理
            if (AuditStatusEnum.APPROVED.getCode() == article.getAuditState().intValue()) {
                continue;
            }
            // 已经拒绝的不做处理
            if (AuditStatusEnum.REJECT.getCode() == article.getAuditState().intValue()) {
                continue;
            }
            article.setAuditState(AuditStatusEnum.APPROVED.getCode());
            article.setUpdatedBy(userId);
            article.setUpdatedTime(new Date());
            article.setAuditTime(new Date());
            int affectRow = update(article);
            if (affectRow > 0) {
                // 增加一条审核记录
                auditHistoryService.create(AuditCategoryEnum.ARTICLE_AUDIT, id, "文章审核通过", YesNoEnum.YES, userId);
            }
        }
    }

    /**
     * 人工拒绝通过
     * @param idList
     * @param rejectReason
     * @param userId
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void rejectArticleManually(List<Long> idList, String rejectReason, Long userId) {
        for (Long id : idList) {
            Article article = findById(id);
            // 草稿状态不做处理
            if (ArticleStateEnum.DRAFT.getState() == article.getState().intValue()) {
                continue;
            }
            // 已经审核通过的不做处理
            if (AuditStatusEnum.APPROVED.getCode() == article.getAuditState().intValue()) {
                continue;
            }
            // 已经拒绝的不做处理
            if (AuditStatusEnum.REJECT.getCode() == article.getAuditState().intValue()) {
                continue;
            }
            article.setAuditState(AuditStatusEnum.REJECT.getCode());
            article.setUpdatedBy(userId);
            article.setUpdatedTime(new Date());
            article.setRejectReason(rejectReason);
            int affectRow = update(article);
            if (affectRow > 0) {
                // 增加一条审核记录
                auditHistoryService.create(AuditCategoryEnum.ARTICLE_AUDIT, id, "文章审核未通过", YesNoEnum.NO, userId);
            }
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void recommend(Map<Long, Integer> map, Long userId) {
        map.forEach((articleId, recommendType)->{
            Article article = findById(articleId);
            article.setRecommendFlag(YesNoEnum.YES.getCode());
            article.setRecommendType(recommendType);
            article.setUpdatedBy(userId);
            article.setRecommendTime(new Date());
            if (ArticleRecommendTypeEnum.BIG_IMAGE_TOP.getCode() == recommendType.intValue()) {
                // 设置为置顶
                article.setTopFlag(YesNoEnum.YES.getCode());
            }
            articleMapper.updateByPrimaryKeySelective(article);
        });
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public void cancelRecommend(List<Long> idList, Long userId) {
        for (Long id : idList) {
            Article article = findById(id);
            article.setRecommendFlag(YesNoEnum.NO.getCode());
            article.setUpdatedBy(userId);
            if (ArticleRecommendTypeEnum.BIG_IMAGE_TOP.getCode() == article.getRecommendType().intValue()) {
                // 取消设置为置顶
                article.setTopFlag(YesNoEnum.NO.getCode());
            }
            articleMapper.updateByPrimaryKeySelective(article);
        }
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public void offLine(List<Long> idList, Long userId) {
        for (Long id : idList) {
            Article article = findById(id);
            article.setOnlineStatus(RecordStatusEnum.PAUSED.getStatus());
            article.setUpdatedBy(userId);
            articleMapper.updateByPrimaryKeySelective(article);
        }
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public void cancelOffLine(List<Long> idList, Long userId) {
        for (Long id : idList) {
            Article article = findById(id);
            article.setOnlineStatus(RecordStatusEnum.NORMAL.getStatus());
            article.setUpdatedBy(userId);
            articleMapper.updateByPrimaryKeySelective(article);
        }
    }

    public Article queryDetailByIdNoLogin(Long articleId) {
        return articleMapper.queryDetailByIdNoLogin(articleId);
    }

    public Article queryDetailByIdWhenLogin(Long articleId, Long userId) {
        return articleMapper.queryDetailByIdWhenLogin(articleId, userId);
    }
}
