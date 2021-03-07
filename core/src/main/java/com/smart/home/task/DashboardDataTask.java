package com.smart.home.task;

import com.smart.home.cache.OnlineUserCache;
import com.smart.home.cache.UserTokenCache;
import com.smart.home.common.util.DateUtils;
import com.smart.home.enums.ArticleCategoryEnum;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.other.entity.RptDashboard;
import com.smart.home.modules.other.entity.RptOnlineCount;
import com.smart.home.modules.other.service.RptDashboardService;
import com.smart.home.modules.other.service.RptOnlineCountService;
import com.smart.home.modules.product.service.ProductCommentReplyService;
import com.smart.home.modules.product.service.ProductCommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/6
 **/
@Log4j2
@Component
public class DashboardDataTask {

    @Autowired
    private RptDashboardService rptDashboardService;
    @Autowired
    private RptOnlineCountService rptOnlineCountService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductCommentReplyService productCommentReplyService;

    @Scheduled(cron = "0 5 0 * * ?")
    public void storeData() {
        Date preDate = DateUtils.getPreDate();
        Date startDate = DateUtils.getEarliestDate(preDate);
        Date endDate = DateUtils.getLastDateOfTheDay(preDate);
        try {
            // 日活
            int liveData = OnlineUserCache.countOnline();
            // 当日文章投稿
            Long countArticleByDateAndCategory0 = articleService.countArticleByDateAndCategory(startDate, endDate, ArticleCategoryEnum.CONTENT);
            // 当日视频投稿
            Long countArticleByDateAndCategory1 = articleService.countArticleByDateAndCategory(startDate, endDate, ArticleCategoryEnum.VIDEO);
            // 当日发帖
            Long countPostByDate = communityPostService.countPostByDate(startDate, endDate);
            // 当日产品评价
            Long countProductCommentByDate = productCommentService.countProductCommentByDate(startDate, endDate);
            // 当日文章评论
            Long countArticleCommentByDateAndCategory0 = articleCommentService.countCommentByDateAndCategory(startDate, endDate, ArticleCategoryEnum.CONTENT);
            // 当日视频评论
            Long countArticleCommentByDateAndCategory1 = articleCommentService.countCommentByDateAndCategory(startDate, endDate, ArticleCategoryEnum.VIDEO);
            // 当日帖子回复
            Long countPostReplyByDate = communityPostReplyService.countReplyByDate(startDate, endDate);
            // 当日评论评价
            Long countProductCommentReplyByDate = productCommentReplyService.countReplyByDate(startDate, endDate);

            RptDashboard rptDashboard = new RptDashboard();
            rptDashboard.withArticleCommentCount(countArticleCommentByDateAndCategory0)
                    .withVideoCommentCount(countArticleCommentByDateAndCategory1)
                    .withArticleCount(countArticleByDateAndCategory0)
                    .withVideoCount(countArticleByDateAndCategory1)
                    .withCreatedTime(preDate)
                    .withLiveCount(liveData)
                    .withPostCount(countPostByDate)
                    .withPostReplyCount(countPostReplyByDate)
                    .withProductCommentCount(countProductCommentByDate)
                    .withProductCommentReplyCount(countProductCommentReplyByDate);
            rptDashboardService.create(rptDashboard);
        } catch (Throwable e) {
            log.error(e);
        } finally {
            OnlineUserCache.removeAll();
        }
    }

    /**
     * 计算每小时的在线人数，每隔一小时跑一次
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void countOnlineUserPerHour() {
        try {
            long currentOnline = UserTokenCache.size();
            RptOnlineCount rptOnlineCount = new RptOnlineCount();
            rptOnlineCount.setOnlineCount((int) currentOnline);
            rptOnlineCountService.create(rptOnlineCount);
        } catch (Throwable e) {
            log.error(e);
        }
    }

}
