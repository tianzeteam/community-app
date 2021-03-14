package com.smart.home.controller.app;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.community.CommunityPostReplyVO;
import com.smart.home.controller.app.response.community.RecommendCommunityPostVO;
import com.smart.home.controller.common.response.ArticleCommentReplyVO;
import com.smart.home.controller.common.response.ArticleCommentVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.community.dto.CommunityPostReplyDTO;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("帖子评论及回复")
@Slf4j
@RestController
@RequestMapping("/api/app/communityPostReply")
public class AppCommunityPostReplyController {

    @Autowired
    private CommunityPostReplyService communityPostReplyService;


    @ApiOperation("获取一级评论-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子主键id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true),
            @ApiImplicitParam(name = "hotSortFlag", value = "热门", required = false),
            @ApiImplicitParam(name = "sortFlag", value = "正序1，倒序0", required = false)
    })
    @AnonAccess
    @GetMapping("/queryCommentByPage")
    public APIResponse<ResponsePageBean<CommunityPostReplyVO>> queryCommentByPage(Long id, Integer hotSortFlag, Integer sortFlag, int pageNum, int pageSize) {
        CommunityPostReplyDTO communityPostReplyDTO = new CommunityPostReplyDTO();
        Long userId = UserUtils.getLoginUserId();
        communityPostReplyDTO.setHotSortFlag(hotSortFlag);
        communityPostReplyDTO.setSortFlag(sortFlag);
        communityPostReplyDTO.setUserId(userId);
        communityPostReplyDTO.setId(id);
        communityPostReplyDTO.setReplyType(0);
        if (userId > 0) {
            // 说明是登陆的
            List<CommunityPostReply> list = communityPostReplyService.queryPageById(communityPostReplyDTO, pageNum, pageSize, true);
            List<CommunityPostReplyVO> resultList = BeanCopyUtils.convertListTo(list, CommunityPostReplyVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        } else {
            List<CommunityPostReply> list = communityPostReplyService.queryPageById(communityPostReplyDTO, pageNum, pageSize, false);
            List<CommunityPostReplyVO> resultList = BeanCopyUtils.convertListTo(list, CommunityPostReplyVO::new);
            return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
        }
    }

    /*@ApiOperation("获取一级评论的回复-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "一级评论主键id", required = true),
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
    }*/


}
