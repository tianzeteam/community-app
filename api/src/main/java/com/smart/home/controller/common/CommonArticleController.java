package com.smart.home.controller.common;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.common.response.ArticleCommentReplyVO;
import com.smart.home.controller.common.response.ArticleCommentVO;
import com.smart.home.controller.common.response.ArticleDetailVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.LikeCategoryEnum;
import com.smart.home.enums.StampCategoryEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.article.service.ArticleCommentReplyService;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.service.LikeService;
import com.smart.home.service.StampService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/27
 **/
@Api(tags = "文章页")
@RestController
@RequestMapping("/api/common/article")
public class CommonArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleCommentReplyService articleCommentReplyService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private StampService stampService;

    @ApiOperation("根据文章主键id获取详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryDetailById")
    public APIResponse<ArticleDetailVO> queryDetailById(Long articleId) {
        ArticleDetailVO articleDetailVO = new ArticleDetailVO();
        Long userId = UserUtils.getLoginUserId();
        if (userId > 0) {
            // 说明是登陆用户
            Article article = articleService.queryDetailByIdWhenLogin(articleId, userId);
            BeanUtils.copyProperties(article, articleDetailVO);
        } else {
            Article article = articleService.queryDetailByIdNoLogin(articleId);
            BeanUtils.copyProperties(article, articleDetailVO);
            articleDetailVO.setCollectFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setFocusFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setLikeFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setStampFlag(YesNoEnum.NO.getCode());
        }
        return APIResponse.OK(articleDetailVO);
    }
    @ApiOperation("点赞文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likeArticle")
    public APIResponse likeArticle(Long articleId) {
        likeService.like(LikeCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
        return APIResponse.OK();
    }
    @ApiOperation("取消点赞文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelLikeArticle")
    public APIResponse cancelLikeArticle(Long articleId) {
        likeService.cancelLike(LikeCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
        return APIResponse.OK();
    }
    @ApiOperation("点踩文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/stampArticle")
    public APIResponse stampArticle(Long articleId) {
        stampService.stamp(StampCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
        return APIResponse.OK();
    }
    @ApiOperation("取消点踩文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelStampArticle")
    public APIResponse cancelStampArticle(Long articleId) {
        stampService.cancelStamp(StampCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
        return APIResponse.OK();
    }

    @ApiOperation("获取一级评论-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @AnonAccess
    @GetMapping("/queryCommentByPage")
    public APIResponse<ResponsePageBean<ArticleCommentVO>> queryCommentByPage(Long articleId, int pageNum, int pageSize) {
        Long userId = UserUtils.getLoginUserId();
        if (userId > 0) {
            // 说明是登陆的
            List<ArticleComment> list = articleCommentService.queryCommentByPageWhenLogin(userId, articleId, pageNum, pageSize);
            List<ArticleCommentVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCommentVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList));
        } else {
            List<ArticleComment> list = articleCommentService.queryCommentByPageNoLogin(articleId, pageNum, pageSize);
            List<ArticleCommentVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCommentVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList));
        }
    }

    @ApiOperation("获取一级评论的回复-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleCommentId", value = "文章一级评论主键id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @AnonAccess
    @GetMapping("/queryCommentReplyByPage")
    public APIResponse<ResponsePageBean<ArticleCommentReplyVO>> queryCommentReplyByPage(Long articleCommentId, int pageNum, int pageSize) {
        Long userId = UserUtils.getLoginUserId();
        if (userId > 0) {
            // 说明是登陆的
            List<ArticleCommentReply> list = articleCommentReplyService.queryCommentReplyByPageWhenLogin(userId, articleCommentId, pageNum, pageSize);
            List<ArticleCommentReplyVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCommentReplyVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList));
        } else {
            List<ArticleCommentReply> list = articleCommentReplyService.queryCommentReplyByPageNoLogin(articleCommentId, pageNum, pageSize);
            List<ArticleCommentReplyVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCommentReplyVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList));
        }
    }

    @ApiOperation("发表一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "contents", value = "评论内容", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addComment")
    public APIResponse addComment(Long articleId, String contents) {
        articleCommentService.create(UserUtils.getLoginUserId(), articleId, contents);
        return APIResponse.OK();
    }

    @ApiOperation("点赞一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleCommentId", value = "文章一级评论主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likeComment")
    public APIResponse likeComment(Long articleCommentId) {
        likeService.like(LikeCategoryEnum.ARTICLE_COMMENT, UserUtils.getLoginUserId(), articleCommentId);
        return APIResponse.OK();
    }
    @ApiOperation("取消点赞一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleCommentId", value = "文章一级评论主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelLikeComment")
    public APIResponse cancelLikeComment(Long articleCommentId) {
        likeService.cancelLike(LikeCategoryEnum.ARTICLE_COMMENT, UserUtils.getLoginUserId(), articleCommentId);
        return APIResponse.OK();
    }
    @ApiOperation("点踩一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleCommentId", value = "文章一级评论主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/stampComment")
    public APIResponse stampComment(Long articleCommentId) {
        stampService.stamp(StampCategoryEnum.ARTICLE_COMMENT, UserUtils.getLoginUserId(), articleCommentId);
        return APIResponse.OK();
    }
    @ApiOperation("取消点踩一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleCommentId", value = "文章一级评论主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelStampComment")
    public APIResponse cancelStampComment(Long articleCommentId) {
        stampService.cancelStamp(StampCategoryEnum.ARTICLE_COMMENT, UserUtils.getLoginUserId(), articleCommentId);
        return APIResponse.OK();
    }

    @ApiOperation("回复一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "articleCommentId", value = "文章一级评论主键id", required = true),
            @ApiImplicitParam(name = "contents", value = "回复内容", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addCommentReply")
    public APIResponse addCommentReply(Long articleId, Long articleCommentId, String contents) {
        articleCommentReplyService.create(UserUtils.getLoginUserId(), articleId, articleCommentId, contents);
        return APIResponse.OK();
    }

}
