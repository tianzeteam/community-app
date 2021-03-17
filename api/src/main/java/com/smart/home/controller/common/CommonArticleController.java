package com.smart.home.controller.common;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.common.response.ArticleCommentReplyVO;
import com.smart.home.controller.common.response.ArticleCommentVO;
import com.smart.home.controller.common.response.ArticleDetailVO;
import com.smart.home.controller.common.response.ProductTestResultVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.CollectTypeEnum;
import com.smart.home.enums.LikeCategoryEnum;
import com.smart.home.enums.MessageSubTypeEnum;
import com.smart.home.enums.StampCategoryEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.article.entity.ArticleProductMapping;
import com.smart.home.modules.article.service.ArticleCommentReplyService;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.article.service.ArticleProductMappingService;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.user.service.UserFocusService;
import com.smart.home.service.CollectService;
import com.smart.home.service.LikeService;
import com.smart.home.service.MessageService;
import com.smart.home.service.StampService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    @Autowired
    private CollectService collectService;
    @Autowired
    private ArticleProductMappingService articleProductMappingService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserFocusService userFocusService;

    @ApiOperation("根据文章主键id获取详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryDetailById")
    public APIResponse<ArticleDetailVO> queryDetailById(Long articleId) {
        ArticleDetailVO articleDetailVO = new ArticleDetailVO();
        Long userId = UserUtils.getLoginUserId();
        Article article = null;
        if (userId > 0) {
            // 说明是登陆用户
            article = articleService.queryDetailByIdWhenLogin(articleId, userId);
            if (Objects.isNull(article)) {
                return APIResponse.ERROR("数据不存在");
            }
            BeanUtils.copyProperties(article, articleDetailVO);
            if (StringUtils.isNotBlank(article.getBannerImages())) {
                articleDetailVO.setImageList(JSON.parseArray(article.getBannerImages(), String.class));
            }
            // 我有没有关注过作者了
            long count = userFocusService.countByFocusUserId(userId, articleId);
            articleDetailVO.setFocusUserFlag(count > 0 ? YesNoEnum.YES.getCode() : YesNoEnum.NO.getCode());
            // 我有没有评论过了
            count = articleCommentService.countByUserId(userId, articleId);
            articleDetailVO.setCommentFlag(count > 0 ? YesNoEnum.YES.getCode() : YesNoEnum.NO.getCode());
        } else {
            article = articleService.queryDetailByIdNoLogin(articleId);
            if (Objects.isNull(article)) {
                return APIResponse.ERROR("数据不存在");
            }
            BeanUtils.copyProperties(article, articleDetailVO);
            articleDetailVO.setCollectFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setFocusFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setLikeFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setStampFlag(YesNoEnum.NO.getCode());
            if (StringUtils.isNotBlank(article.getBannerImages())) {
                articleDetailVO.setImageList(JSON.parseArray(article.getBannerImages(), String.class));
            }
        }
        if (YesNoEnum.YES.getCode() == article.getTestFlag()) {
            // 附加产品评测
            ArticleProductMapping articleProductMapping = articleProductMappingService.findByArticle(articleDetailVO.getId());
            if (articleProductMapping != null) {
                ProductTestResultVO productTestResultVO = new ProductTestResultVO();
                BeanUtils.copyProperties(articleProductMapping, productTestResultVO);
                articleDetailVO.setProductTestResultVO(productTestResultVO);
            }
        }
        return APIResponse.OK(articleDetailVO);
    }
    @ApiOperation("点赞文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "authorId", value = "作者主键id", required = true)

    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likeArticle")
    public APIResponse likeArticle(Long articleId, Long authorId) {
        try {
            Long fromUserId = UserUtils.getLoginUserId();
            likeService.like(LikeCategoryEnum.ARTICLE, fromUserId, articleId);
            messageService.createLikeMessage(MessageSubTypeEnum.ARTICLE, articleId, fromUserId, authorId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
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
    @ApiOperation("点赞/取消赞文章-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "authorId", value = "作者主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0点赞1取消赞", required = true)

    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likeOrCancelLikeArticle")
    public APIResponse likeArticle(Long articleId, Long authorId, @RequestParam(required = true) Integer action) {
        try {
            Long fromUserId = UserUtils.getLoginUserId();
            if (action == 0) {
                likeService.like(LikeCategoryEnum.ARTICLE, fromUserId, articleId);
                messageService.createLikeMessage(MessageSubTypeEnum.ARTICLE, articleId, fromUserId, authorId);
            } else {
                likeService.cancelLike(LikeCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("点踩文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/stampArticle")
    public APIResponse stampArticle(Long articleId) {
        try {
            stampService.stamp(StampCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
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
    @ApiOperation("点踩/取消踩文章-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0点踩1取消点踩", required = true)

    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/stampOrCancelStampArticle")
    public APIResponse stampArticle(Long articleId,@RequestParam(required = true) Integer action) {
        try {
            if (action == 0) {
                stampService.stamp(StampCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
            } else {
                stampService.cancelStamp(StampCategoryEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("收藏文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addCollect")
    public APIResponse addCollect(Long articleId) {
        try {
            collectService.addCollect(CollectTypeEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }
    @ApiOperation("取消收藏文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelAdCollect")
    public APIResponse cancelAdCollect(Long articleId) {
        collectService.cancelCollect(CollectTypeEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
        return APIResponse.OK();
    }
    @ApiOperation("收藏/取消收藏文章-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0收藏1取消收藏", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addOrCancelAddCollect")
    public APIResponse addCollect(Long articleId, @RequestParam(required = true) Integer action) {
        try {
            if (action == 0) {
                collectService.addCollect(CollectTypeEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
            } else {
                collectService.cancelCollect(CollectTypeEnum.ARTICLE, UserUtils.getLoginUserId(), articleId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
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
            if (CollUtil.isNotEmpty(resultList)) {
                for (ArticleCommentVO articleCommentVO : resultList) {
                    // 加载二级评论
                    List<ArticleCommentReply> replyList = articleCommentReplyService.queryCommentReplyByPageWhenLogin(userId, articleCommentVO.getId(), pageNum, 5);
                    List<ArticleCommentReplyVO> replyResultList = BeanCopyUtils.convertListTo(replyList, ArticleCommentReplyVO::new);
                    articleCommentVO.setReplyResultList(ResponsePageUtil.restPage(replyResultList, replyList));
                }
            }
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        } else {
            List<ArticleComment> list = articleCommentService.queryCommentByPageNoLogin(articleId, pageNum, pageSize);
            List<ArticleCommentVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCommentVO::new);
            if (CollUtil.isNotEmpty(resultList)) {
                for (ArticleCommentVO articleCommentVO : resultList) {
                    // 加载二级评论
                    List<ArticleCommentReply> replyList = articleCommentReplyService.queryCommentReplyByPageNoLogin(articleCommentVO.getId(), pageNum, 5);
                    List<ArticleCommentReplyVO> replyResultList = BeanCopyUtils.convertListTo(replyList, ArticleCommentReplyVO::new);
                    articleCommentVO.setReplyResultList(ResponsePageUtil.restPage(replyResultList, replyList));
                }
            }
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
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
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        } else {
            List<ArticleCommentReply> list = articleCommentReplyService.queryCommentReplyByPageNoLogin(articleCommentId, pageNum, pageSize);
            List<ArticleCommentReplyVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCommentReplyVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        }
    }

    @ApiOperation("发表一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true),
            @ApiImplicitParam(name = "articleAuthorId", value = "文章作者主键id", required = true),
            @ApiImplicitParam(name = "contents", value = "评论内容", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addComment")
    public APIResponse addComment(Long articleId,Long articleAuthorId, String contents) {
        if (Objects.isNull(articleId)) {
            return APIResponse.ERROR("文章主键id不能为空");
        }
        if (StringUtils.isBlank(contents)) {
            return APIResponse.ERROR("评论内容不能为空");
        } else if (contents.length() > 200) {
            return APIResponse.ERROR("评论内容不能超过200字");
        }
        Long fromUserId = UserUtils.getLoginUserId();
        articleCommentService.create(fromUserId, articleId, contents);
        messageService.createReplyMessage(MessageSubTypeEnum.ARTICLE, articleId, fromUserId, articleAuthorId, contents);
        return APIResponse.OK();
    }

    @ApiOperation("点赞一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleCommentId", value = "文章一级评论主键id", required = true),
            @ApiImplicitParam(name = "authorId", value = "评论人的主键id", required = true)

    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likeComment")
    public APIResponse likeComment(Long articleCommentId, Long authorId) {
        try {
            Long fromUserId = UserUtils.getLoginUserId();
            likeService.like(LikeCategoryEnum.ARTICLE_COMMENT, UserUtils.getLoginUserId(), articleCommentId);
            messageService.createLikeMessage(MessageSubTypeEnum.ARTICLE_COMMENT, articleCommentId, fromUserId, authorId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
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
        try {
            stampService.stamp(StampCategoryEnum.ARTICLE_COMMENT, UserUtils.getLoginUserId(), articleCommentId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
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
            @ApiImplicitParam(name = "articleCommentAuthorId", value = "文章一级评论作者主键id", required = true),
            @ApiImplicitParam(name = "contents", value = "回复内容", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addCommentReply")
    public APIResponse addCommentReply(Long articleId, Long articleCommentId,Long articleCommentAuthorId, String contents) {
        Long fromUserId = UserUtils.getLoginUserId();
        articleCommentReplyService.create(fromUserId, articleId, articleCommentId, contents);
        messageService.createReplyMessage(MessageSubTypeEnum.ARTICLE_COMMENT, articleId, fromUserId, articleCommentAuthorId, contents);
        return APIResponse.OK();
    }

}
