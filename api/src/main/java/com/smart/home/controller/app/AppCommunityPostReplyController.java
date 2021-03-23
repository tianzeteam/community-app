package com.smart.home.controller.app;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.CommunityPostReplyReq;
import com.smart.home.controller.app.response.community.CommunityPostReplyVO;
import com.smart.home.controller.app.response.community.RecommendCommunityPostVO;
import com.smart.home.controller.common.response.ArticleCommentReplyVO;
import com.smart.home.controller.common.response.ArticleCommentVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.CollectTypeEnum;
import com.smart.home.enums.LikeCategoryEnum;
import com.smart.home.enums.MessageSubTypeEnum;
import com.smart.home.enums.StampCategoryEnum;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.community.dto.CommunityPostReplyDTO;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.service.LikeService;
import com.smart.home.service.MessageService;
import com.smart.home.service.StampService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Api(tags = "app-帖子评论及回复")
@Slf4j
@RestController
@RequestMapping("/api/app/communityPostReply")
public class AppCommunityPostReplyController {

    @Autowired
    private CommunityPostReplyService communityPostReplyService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private StampService stampService;
    @Autowired
    private CommunityPostService communityPostService;


    @ApiOperation("发表一级评论")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addComment")
    public APIResponse addComment(@Valid @RequestBody CommunityPostReplyReq communityPostReplyReq, BindingResult bindingResult) {
        log.info("发表一级评论params：{}", JSON.toJSONString(communityPostReplyReq));
        CommunityPost communityPost = communityPostService.findById(communityPostReplyReq.getId());
        if (communityPost == null) {
            return APIResponse.ERROR("没有此文章");
        }
        Long fromUserId = UserUtils.getLoginUserId();
        communityPostReplyService.create(fromUserId, communityPostReplyReq.getId(), communityPostReplyReq.getContents());
        messageService.createReplyMessage(MessageSubTypeEnum.COMMUNITY_POST_REPLY, communityPostReplyReq.getId(), fromUserId, communityPost.getUserId(), communityPostReplyReq.getContents());
        return APIResponse.OK();
    }

    @ApiOperation("点赞一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "一级评论主键id", required = true),
            @ApiImplicitParam(name = "authorId", value = "评论人的主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likeComment")
    public APIResponse likeComment(Long commentId, Long authorId) {
        try {
            Long fromUserId = UserUtils.getLoginUserId();
            likeService.like(LikeCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), commentId);
            messageService.createLikeMessage(MessageSubTypeEnum.COMMUNITY_POST_REPLY, commentId, fromUserId, authorId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }


    @ApiOperation("取消点赞一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "一级评论主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelLikeComment")
    public APIResponse cancelLikeComment(Long commentId) {
        likeService.cancelLike(LikeCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), commentId);
        return APIResponse.OK();
    }



    @ApiOperation("设置/取消点赞一级评论-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0设置1取消", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addOrCancelCommentLike")
    public APIResponse addOrCancelCommentLike(Long id, Integer action) {
        try {
            CommunityPostReply communityPostReply = communityPostReplyService.getById(id);
            if (communityPostReply == null) {
                return APIResponse.ERROR("没有此帖子");
            }
            if (action == 0) {
                likeService.like(LikeCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), id);
            } else {
                likeService.cancelLike(LikeCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), id);
            }
            messageService.createLikeMessage(MessageSubTypeEnum.COMMUNITY_POST_REPLY, id, UserUtils.getLoginUserId(), communityPostReply.getUserId());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }


    @ApiOperation("点踩一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "一级评论主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/stampComment")
    public APIResponse stampComment(Long commentId) {
        try {
            stampService.stamp(StampCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), commentId);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }


    @ApiOperation("取消点踩一级评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "一级评论主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelStampComment")
    public APIResponse cancelStampComment(Long commentId) {
        stampService.cancelStamp(StampCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), commentId);
        return APIResponse.OK();
    }

    @ApiOperation("设置/取消点踩一级评论-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "评论主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0设置1取消", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addOrCancelCommentStamp")
    public APIResponse addOrCancelCommentStamp(Long id, Integer action) {
        try {
            if (action == 0) {
                stampService.stamp(StampCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), id);
            } else {
                stampService.cancelStamp(StampCategoryEnum.POST_REPLY, UserUtils.getLoginUserId(), id);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    /**
     * 可能回复一级的评论，可能回复二级的评论
     * 添加的记录一定为二级
     */
    @ApiOperation("回复评论(包括一二级)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论主键id", required = true),
            @ApiImplicitParam(name = "contents", value = "回复内容", required = true),
            @ApiImplicitParam(name = "postId", value = "帖子id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addCommentReply")
    public APIResponse addCommentReply(Long commentId, Long postId, String contents) {
        CommunityPostReply postReply = communityPostReplyService.getById(commentId);
        if (postReply == null) {
            return APIResponse.ERROR("无此评论");
        }
        CommunityPost communityPost = communityPostService.findById(postId);
        if (communityPost == null) {
            return APIResponse.ERROR("没有此文章");
        }
        Long fromUserId = UserUtils.getLoginUserId();
        CommunityPostReply communityPostReply = new CommunityPostReply();
        communityPostReply.setUserId(fromUserId);
        communityPostReply.setPostId(postReply.getPostId());
        communityPostReply.setPostReplyId(commentId);
        communityPostReply.setReplyType(1);
        communityPostReply.setContents(contents);
        communityPostReply.setToUserId(postReply.getUserId());
        communityPostReply.setLikeCount(0);
        communityPostReply.setStampCount(0);
        if (fromUserId.equals(communityPost.getUserId())) {
            communityPostReply.setAuthorFlag(1);
        }else {
            communityPostReply.setAuthorFlag(0);
        }
        communityPostReplyService.create(communityPostReply);
        messageService.createReplyMessage(MessageSubTypeEnum.COMMUNITY_POST_REPLY, postReply.getPostId(), fromUserId, postReply.getUserId(), contents);
        return APIResponse.OK();
    }


    @ApiOperation("获取一级和二级评论-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true),
            @ApiImplicitParam(name = "hotSortFlag", value = "热门", required = false),
            @ApiImplicitParam(name = "sortFlag", value = "正序1，倒序0", required = false)
    })
    @AnonAccess
    @GetMapping("/queryCommentByPage")
    public APIResponse<ResponsePageBean<CommunityPostReplyVO>> queryCommentByPage(Long postId, Integer hotSortFlag, Integer sortFlag, int pageNum, int pageSize) {
        CommunityPostReplyDTO communityPostReplyDTO = new CommunityPostReplyDTO();
        Long userId = UserUtils.getLoginUserId();
        communityPostReplyDTO.setHotSortFlag(hotSortFlag);
        communityPostReplyDTO.setSortFlag(sortFlag);
        communityPostReplyDTO.setUserId(userId);
        communityPostReplyDTO.setPostId(postId);
        communityPostReplyDTO.setReplyType(0);
        List<CommunityPostReply> list = null;
        List<CommunityPostReplyVO> resultList = null;
        if (userId > 0) {
            // 说明是登陆的
            list = communityPostReplyService.queryPageById(communityPostReplyDTO, pageNum, pageSize, true);
            resultList = BeanCopyUtils.convertListTo(list, CommunityPostReplyVO::new);
            communityPostReplyDTO.setReplyType(1);
            resultList.stream().forEach(x->{
                communityPostReplyDTO.setPostReplyId(x.getId());
                List<CommunityPostReply> twoLevelPostReplys = communityPostReplyService.queryPageByCommentId(communityPostReplyDTO, 1, 2, true);
                List<CommunityPostReplyVO> twoResultList = BeanCopyUtils.convertListTo(twoLevelPostReplys, CommunityPostReplyVO::new);
                x.setList(twoResultList);
            });
        } else {
            list = communityPostReplyService.queryPageById(communityPostReplyDTO, pageNum, pageSize, false);
            resultList = BeanCopyUtils.convertListTo(list, CommunityPostReplyVO::new);
            resultList.stream().forEach(x->{
                communityPostReplyDTO.setPostReplyId(x.getId());
                List<CommunityPostReply> twoLevelPostReplys = communityPostReplyService.queryPageByCommentId(communityPostReplyDTO, 1, 2, false);
                List<CommunityPostReplyVO> twoResultList = BeanCopyUtils.convertListTo(twoLevelPostReplys, CommunityPostReplyVO::new);
                x.setList(twoResultList);
            });
        }
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("获取一级评论的回复-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "一级评论主键id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @AnonAccess
    @GetMapping("/queryCommentReplyByPage")
    public APIResponse<ResponsePageBean<CommunityPostReplyVO>> queryCommentReplyByPage(Long commentId, int pageNum, int pageSize) {
        CommunityPostReplyDTO communityPostReplyDTO = new CommunityPostReplyDTO();
        Long userId = UserUtils.getLoginUserId();
        communityPostReplyDTO.setUserId(userId);
        communityPostReplyDTO.setReplyType(1);
        communityPostReplyDTO.setPostReplyId(commentId);
        if (userId > 0) {
            // 说明是登陆的
            List<CommunityPostReply> list = communityPostReplyService.queryPageByCommentId(communityPostReplyDTO, pageNum, pageSize, true);
            List<CommunityPostReplyVO> resultList = BeanCopyUtils.convertListTo(list, CommunityPostReplyVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        } else {
            List<CommunityPostReply> list = communityPostReplyService.queryPageByCommentId(communityPostReplyDTO, pageNum, pageSize, false);
            List<CommunityPostReplyVO> resultList = BeanCopyUtils.convertListTo(list, CommunityPostReplyVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        }
    }



}
