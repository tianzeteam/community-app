package com.smart.home.controller.common;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.CommunityPostReq;
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
import com.smart.home.es.common.EsConstant;
import com.smart.home.es.service.EsCommonService;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.service.CollectService;
import com.smart.home.service.LikeService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Slf4j
@Api(tags = "社区帖子-精华/置顶")
@RestController
@RequestMapping("/api/common/post")
public class CommonPostController {

    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private StampService stampService;
    @Autowired
    private EsCommonService esCommonService;


    @ApiOperation("设置为精华")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/setBoutique")
    public APIResponse setBoutique(Long postId) {
        communityPostService.updateBoutiqueFlag(postId, YesNoEnum.YES.getCode());
        return APIResponse.OK();
    }

    @ApiOperation("取消精华")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelBoutique")
    public APIResponse cancelBoutique(Long postId) {
        communityPostService.updateBoutiqueFlag(postId, YesNoEnum.NO.getCode());
        return APIResponse.OK();
    }

    @ApiOperation("设置为置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/setTop")
    public APIResponse setTop(Long postId) {
        communityPostService.updateTopFlag(postId, YesNoEnum.YES.getCode());
        return APIResponse.OK();
    }

    @ApiOperation("取消置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "帖子主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelTop")
    public APIResponse cancelTop(Long postId) {
        communityPostService.updateTopFlag(postId, YesNoEnum.NO.getCode());
        return APIResponse.OK();
    }


    @ApiOperation("删除帖子-软删除")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/del")
    public APIResponse del(@RequestBody CommunityPostReq communityPostReq) {
        log.info("删除帖子params:{}", JSON.toJSONString(communityPostReq));
        CommunityPost communityPost = new CommunityPost();
        communityPost.setId(communityPostReq.getId());
        communityPost.setState(2);
        communityPost.setReason(JSON.toJSONString(communityPostReq.getList()));
        int ret = communityPostService.update(communityPost);
        esCommonService.deleteOne(EsConstant.communityPostIndex, EsConstant.communityPost, communityPostReq.getId());
        return APIResponse.OK(ret);
    }

    @ApiOperation("点赞帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likePost")
    public APIResponse likePost(Long id) {
        try {
            likeService.like(LikeCategoryEnum.POST, UserUtils.getLoginUserId(), id);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }
    @ApiOperation("取消点赞帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelLikePost")
    public APIResponse cancelLikePost(Long id) {
        likeService.cancelLike(LikeCategoryEnum.POST, UserUtils.getLoginUserId(), id);
        return APIResponse.OK();
    }
    @ApiOperation("点踩帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/stampPost")
    public APIResponse stampPost(Long id) {
        try {
            stampService.stamp(StampCategoryEnum.POST, UserUtils.getLoginUserId(), id);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }
    @ApiOperation("取消点踩帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelStampPost")
    public APIResponse cancelStampPost(Long id) {
        stampService.cancelStamp(StampCategoryEnum.POST, UserUtils.getLoginUserId(), id);
        return APIResponse.OK();
    }
    @ApiOperation("收藏帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/addCollect")
    public APIResponse addCollect(Long id) {
        try {
            collectService.addCollect(CollectTypeEnum.POST, UserUtils.getLoginUserId(), id);
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }
    @ApiOperation("取消收藏帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子主键id", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/cancelAdCollect")
    public APIResponse cancelAdCollect(Long id) {
        collectService.cancelCollect(CollectTypeEnum.POST, UserUtils.getLoginUserId(), id);
        return APIResponse.OK();
    }


}
