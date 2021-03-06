package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.DateUtils;
import com.smart.home.controller.common.UserHeartBeatController;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.ArticleCategoryEnum;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.product.service.ProductCommentReplyService;
import com.smart.home.modules.product.service.ProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Api(tags = "管理后台-数据看板")
@RestController
@RequestMapping("/api/pc/dashboard")
public class DashboardController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;
    @Autowired
    private ProductCommentReplyService productCommentReplyService;

    @ApiOperation("获取当前在线人数")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/onlineCount")
    public APIResponse onlineCount() {
        return APIResponse.OK(UserHeartBeatController.atomicLong.get());
    }

    @ApiOperation("当日文章投稿")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayArticle")
    public APIResponse countTodayArticle() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = articleService.countArticleByDateAndCategory(startDate, endDate, ArticleCategoryEnum.CONTENT);
        return APIResponse.OK(count);
    }

    @ApiOperation("当日视频投稿")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayVideo")
    public APIResponse countTodayVideo() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = articleService.countArticleByDateAndCategory(startDate, endDate, ArticleCategoryEnum.VIDEO);
        return APIResponse.OK(count);
    }

    @ApiOperation("当日发帖")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayPost")
    public APIResponse countTodayPost() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = communityPostService.countPostByDate(startDate, endDate);
        return APIResponse.OK(count);
    }

    @ApiOperation("当日产品评价")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayProductComment")
    public APIResponse countTodayProductComment() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = productCommentService.countProductCommentByDate(startDate, endDate);
        return APIResponse.OK(count);
    }

    @ApiOperation("当日文章评论")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayArticleComment")
    public APIResponse countTodayArticleComment() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = articleCommentService.countCommentByDateAndCategory(startDate, endDate, ArticleCategoryEnum.CONTENT);
        return APIResponse.OK(count);
    }

    @ApiOperation("当日视频评论")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayVideoComment")
    public APIResponse countTodayVideoComment() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = articleCommentService.countCommentByDateAndCategory(startDate, endDate, ArticleCategoryEnum.VIDEO);
        return APIResponse.OK(count);
    }

    @ApiOperation("当日帖子回复")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayPostReply")
    public APIResponse countTodayPostReply() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = communityPostReplyService.countReplyByDate(startDate, endDate);
        return APIResponse.OK(count);
    }

    @ApiOperation("当日评价评论")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/countTodayProductCommentReply")
    public APIResponse countTodayProductCommentReply() {
        Date now = new Date();
        Date startDate = DateUtils.getEarliestDate(now);
        Date endDate = DateUtils.getLastDateOfTheDay(now);
        Long count = productCommentReplyService.countReplyByDate(startDate, endDate);
        return APIResponse.OK(count);
    }

}
