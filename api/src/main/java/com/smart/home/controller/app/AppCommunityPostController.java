package com.smart.home.controller.app;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.community.CommunityPostDetailVO;
import com.smart.home.controller.app.response.community.RecommendCommunityPostVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.community.dto.CommunityPostDTO;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "app-社区帖子")
@RestController
@RequestMapping("/api/app/communityPost")
public class AppCommunityPostController {

    @Autowired
    private CommunityPostService communityPostService;


    /**
     * @Author: lǎo xiāng
     * @Date: 2021/3/10 22:53
     * @Describe: 点击帖子+1，浏览 异步
     */
    @ApiOperation("浏览-点击帖子")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子id", required = true)
    })
    @GetMapping("/sync/click")
    public APIResponse syncClick(Long id) {
        communityPostService.syncClick(id);
        return APIResponse.OK();
    }

    @AnonAccess
    @ApiOperation("推荐帖子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @GetMapping("/recommend/queryList")
    public APIResponse<ResponsePageBean<RecommendCommunityPostVO>> recommendQueryList(Integer pageNum, Integer pageSize) {
        log.info("推荐帖子列表:");
        List<CommunityPostDTO> communityPosts = communityPostService.queryRecommendPostList(pageNum, pageSize);
        List<RecommendCommunityPostVO> recommendCommunityPostVOS = BeanCopyUtils.convertListTo(communityPosts, RecommendCommunityPostVO::new);
        return APIResponse.OK(ResponsePageBean.restPage(recommendCommunityPostVOS));
    }

    //暂时先用一个返回vo
    @AnonAccess
    @ApiOperation("热议帖子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @GetMapping("/hot/queryList")
    public APIResponse<ResponsePageBean<RecommendCommunityPostVO>> hotQueryList(Integer pageNum, Integer pageSize) {
        log.info("热议帖子列表:");
        List<CommunityPostDTO> communityPosts = communityPostService.queryHotPostList(pageNum, pageSize);
        List<RecommendCommunityPostVO> recommendCommunityPostVOS = BeanCopyUtils.convertListTo(communityPosts, RecommendCommunityPostVO::new);
        return APIResponse.OK(ResponsePageBean.restPage(recommendCommunityPostVOS));
    }

    @AnonAccess
    @ApiOperation("帖子详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "帖子id", required = true)
    })
    @GetMapping("/detail")
    public APIResponse<CommunityPostDetailVO> detail(Long id){
        CommunityPostDetailVO communityPostDetailVO = new CommunityPostDetailVO();
        Long userId = UserUtils.getLoginUserId();
        if (userId > 0) {
            //用户已登录
            CommunityPostDTO communityPostDTO = communityPostService.queryDetailWithLogin(id, userId);
            BeanUtils.copyProperties(communityPostDTO, communityPostDetailVO);
        }else {
            //用户未登录

        }
        return null;
    }


}
