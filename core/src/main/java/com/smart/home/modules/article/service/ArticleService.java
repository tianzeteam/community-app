package com.smart.home.modules.article.service;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.FileUtils;
import com.smart.home.enums.*;
import com.smart.home.es.bean.ArticleBean;
import com.smart.home.es.service.ArticleEsService;
import com.smart.home.modules.article.dao.ArticleMapper;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleExample;
import com.smart.home.modules.other.service.AuditHistoryService;
import com.smart.home.modules.system.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author jason
 **/
@Service
public class ArticleService {

    @Resource
    ArticleMapper articleMapper;
    @Autowired
    private AuditHistoryService auditHistoryService;
    @Autowired
    private SysFileService sysFileService;
    @Autowired
    private ArticleProductMappingService articleProductMappingService;
    @Autowired
    private ArticleEsService articleEsServiceImpl;

    public int create(Article article, Integer productId, String testResult, Integer recommendFlag) {
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
        article.setUpdatedTime(new Date());
        int affectRow = articleMapper.insertSelective(article);
        Long articleId = article.getId();
        syncUploadFiles(article.getCoverImage(), article.getBannerImages(), article.getImageList());
        if (productId != null) {
            // 插入关系表
            articleProductMappingService.create(articleId, productId, testResult, recommendFlag);
            // 标记为评测文章
            articleMapper.markAsTestArticle(articleId, testResult, recommendFlag);
        }
        return affectRow;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int update(Article article) {
        int affectRow = articleMapper.updateByPrimaryKeySelective(article);
        syncUploadFiles(article.getCoverImage(), article.getBannerImages(), article.getImageList());
        Article dbArticle = findById(article.getId());
        if (AuditStatusEnum.APPROVED.getCode() == dbArticle.getAuditState() && RecordStatusEnum.NORMAL.getStatus() == dbArticle.getOnlineStatus()) {
            ArticleBean articleBean = new ArticleBean();
            BeanUtils.copyProperties(dbArticle, articleBean);
            articleBean.setSaveType(EsSaveTypeEnum.ARTICLE.getType());
            articleEsServiceImpl.save(articleBean);
        }
        return affectRow;
    }

    public int deleteDraftById(Long articleId) {
        Article article = findById(articleId);
        List<String> imageList = new ArrayList<>();
        if(StringUtils.isNotBlank(article.getCoverImage())) {
            imageList.add(article.getCoverImage());
        }
        if (StringUtils.isNotBlank(article.getBannerImages())) {
            imageList.addAll(JSON.parseArray(article.getBannerImages(), String.class));
        }
        int affectRow = articleMapper.deleteByPrimaryKey(articleId);
        sysFileService.deleteImageByUrlList(imageList);
        return affectRow;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 软删除
            articleMapper.updateState(id, RecordStatusEnum.DELETE.getStatus());
            // 同步es
            articleEsServiceImpl.deleteById(id);
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
                // 同步到es
                ArticleBean articleBean = new ArticleBean();
                BeanUtils.copyProperties(article, articleBean);
                articleBean.setSaveType(EsSaveTypeEnum.ARTICLE.getType());
                if (article.getCreatedTime() == null) {
                    articleBean.setCreatedTime(new Date());
                } else {
                    articleBean.setCreatedTime(article.getCreatedTime());
                }
                articleEsServiceImpl.save(articleBean);
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
    public void recommend(Map<Long, Integer> map, Long userId) throws ServiceException {
        map.forEach((articleId, recommendType)->{
            Article article = findById(articleId);
            if(article.getOnlineStatus().intValue()==RecordStatusEnum.PAUSED.getStatus()) {
                throw new ServiceException("撤稿的状态的记录不能设置为推荐");
            }
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
            article.setTopFlag(YesNoEnum.NO.getCode());
            article.setRecommendFlag(YesNoEnum.NO.getCode());
            articleMapper.updateByPrimaryKeySelective(article);
            // 同步es
            articleEsServiceImpl.deleteById(id);
        }
    }
    @Transactional(rollbackFor = RuntimeException.class)
    public void cancelOffLine(List<Long> idList, Long userId) {
        for (Long id : idList) {
            Article article = findById(id);
            article.setOnlineStatus(RecordStatusEnum.NORMAL.getStatus());
            article.setUpdatedBy(userId);
            articleMapper.updateByPrimaryKeySelective(article);
            // 同步es
            ArticleBean articleBean = new ArticleBean();
            BeanUtils.copyProperties(article, articleBean);
            articleBean.setSaveType(EsSaveTypeEnum.ARTICLE.getType());
            articleEsServiceImpl.save(articleBean);
        }
    }

    public Article queryDetailByIdNoLogin(Long articleId) {
        // 增加一次浏览量
        CompletableFuture.runAsync(()->{
            articleMapper.increaseVisitCount(articleId);
        });
        return articleMapper.queryDetailByIdNoLogin(articleId);
    }

    public Article queryDetailByIdWhenLogin(Long articleId, Long userId) {
        // 增加一次浏览量
        CompletableFuture.runAsync(()->{
            articleMapper.increaseVisitCount(articleId);
        });
        return articleMapper.queryDetailByIdWhenLogin(articleId, userId);
    }

    public void increaseLikeCount(Long id) {
        articleMapper.increaseLikeCount(id);
    }
    public void decreaseLikeCount(Long id) {
        articleMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        articleMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        articleMapper.decreaseStampCount(id);
    }

    public List<Article> selectAllTopRecommend() {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andStateEqualTo(ArticleStateEnum.PUBLISH.getState())
                .andAuditStateEqualTo(AuditStatusEnum.APPROVED.getCode())
                .andTopFlagEqualTo(YesNoEnum.YES.getCode())
                .andRecommendFlagEqualTo(YesNoEnum.YES.getCode());
        return articleMapper.selectByExample(example);
    }

    public void cancelSetTop(Long articleId) {
        Integer recommendType = articleMapper.findRecommendTypeById(articleId);
        if (Objects.isNull(recommendType)) {
            articleMapper.updateTopFlag(articleId, YesNoEnum.NO.getCode());
            return;
        }
        if(ArticleRecommendTypeEnum.BIG_IMAGE_TOP.getCode() == recommendType.intValue()) {
            articleMapper.updateTopFlagAndRecommendType(articleId, YesNoEnum.NO.getCode(),ArticleRecommendTypeEnum.BIG_IMAGE_CARD.getCode());
            return;
        }
        articleMapper.updateTopFlag(articleId, YesNoEnum.NO.getCode());
    }

    public void setTop(Long articleId) throws ServiceException {
        Long channelId = articleMapper.findChannelIdById(articleId);
        ArticleExample example = new ArticleExample();
        example.createCriteria().andChannelIdEqualTo(channelId).andTopFlagEqualTo(YesNoEnum.YES.getCode());
        if (articleMapper.countByExample(example) > 0) {
            throw new ServiceException("该频道下只能存在一个置顶文章");
        }
        Integer recommendType = articleMapper.findRecommendTypeById(articleId);
        if (Objects.isNull(recommendType)) {
            articleMapper.updateTopFlag(articleId, YesNoEnum.YES.getCode());
            return;
        }
        if (ArticleRecommendTypeEnum.BIG_IMAGE_CARD.getCode() == recommendType.intValue()) {
            articleMapper.updateTopFlagAndRecommendType(articleId, YesNoEnum.YES.getCode(), ArticleRecommendTypeEnum.BIG_IMAGE_TOP.getCode());
            return;
        }
        articleMapper.updateTopFlag(articleId, YesNoEnum.YES.getCode());
    }

    public void setBigImage(Long articleId) {
        articleMapper.updateRecommendType(articleId, ArticleRecommendTypeEnum.BIG_IMAGE_CARD.getCode());
    }

    public void setAsArticle(Long articleId) {
        articleMapper.updateRecommendType(articleId, ArticleRecommendTypeEnum.ARTCILE_CARD.getCode());
    }

    public List<Article> selectArticleCardByPage(Integer channelId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.selectArticleCardByPage(channelId);
    }

    public List<Article> queryViaProductIdByPage(Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.queryViaProductIdByPage(productId);
    }

    public List<Article> queryCollectViaUserIdByPage(Long loginUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.queryCollectViaUserIdByPage(loginUserId);
    }

    public List<Article> queryViaUserIdByPageWhenLogin(Long userId, int pageNum, int pageSize, Long loginUserId) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.queryViaUserIdByPageWhenLogin(userId, loginUserId);
    }

    public List<Article> queryDraftViaUserIdByPage(Long loginUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleMapper.queryDraftViaUserIdByPage(loginUserId);
    }

    /**
     * 获取推荐下面的置顶的大图
     * topFlag = 1
     * recommendFlag = 1
     * @return
     */
    public Article queryIndexTopBigImageCard() {
        // 1 是推荐的频道id
        Article article = articleMapper.queryBigImageCard(YesNoEnum.YES.getCode(), ArticleRecommendTypeEnum.BIG_IMAGE_TOP.getCode(), null, YesNoEnum.YES.getCode());
        if (Objects.isNull(article)) {
            // 如果不存在置顶内容，则第一个内容默认置顶
            article = articleMapper.queryBigImageCard(YesNoEnum.NO.getCode(), null, null, null);
        }
        return article;
    }

    /**
     * 获取其他频道下的置顶大图
     * topFlag = 1
     * recommendFlag = 0
     * @param articleChannelId
     * @return
     */
    public Article queryBigImageCardByChannelId(Integer articleChannelId) {
        return articleMapper.queryBigImageCard(YesNoEnum.YES.getCode(), ArticleRecommendTypeEnum.BIG_IMAGE_CARD.getCode(), articleChannelId, YesNoEnum.NO.getCode());
    }

    /**
     * 获取推荐下面的文章卡片
     * topFlag = 1
     * recommendFlag = 1
     * @return
     */
    public List<Article> queryIndexArticleCard() {
        return articleMapper.queryIndexArticleCard(YesNoEnum.NO.getCode(), ArticleRecommendTypeEnum.ARTCILE_CARD.getCode(), null, YesNoEnum.YES.getCode());
    }

    public List<Article> queryIndexArticleCardByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return queryIndexArticleCard();
    }

    public List<Article> queryArticleCardByChannelId(Integer articleChannelId) {
        return articleMapper.queryIndexArticleCard(YesNoEnum.NO.getCode(), ArticleRecommendTypeEnum.ARTCILE_CARD.getCode(), articleChannelId, YesNoEnum.NO.getCode());
    }

    private void syncUploadFiles(String coverImage, String bannerImages, List<String> imageList) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(coverImage)) {
            list.add(FileUtils.getFileNameFromUrl(coverImage));
        }
        if (StringUtils.isNotBlank(bannerImages)) {
            for (String s : JSON.parseArray(bannerImages, String.class)) {
                list.add(FileUtils.getFileNameFromUrl(s));
            }
        }
        if (CollUtil.isNotEmpty(imageList)) {
            for (String image : imageList) {
                list.add(FileUtils.getFileNameFromUrl(image));
            }
        }
        sysFileService.syncList(list);
    }

    public Long countArticleByDateAndCategory(Date startDate, Date endDate, ArticleCategoryEnum categoryEnum) {
        int category = categoryEnum.getCode();
        ArticleExample example = new ArticleExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate).andCategoryEqualTo(category);
        return articleMapper.countByExample(example);
    }

    public String queryChannelNameByChannelId(Long channelId) {
        if (Objects.isNull(channelId)) {
            return "";
        }
        return articleMapper.queryChannelNameByChannelId(channelId);
    }
}
