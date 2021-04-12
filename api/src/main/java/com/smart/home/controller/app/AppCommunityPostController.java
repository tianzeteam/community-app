package com.smart.home.controller.app;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.CommunityPostReq;
import com.smart.home.controller.app.response.community.CommunityPostDetailVO;
import com.smart.home.controller.app.response.community.CommunityPostVO;
import com.smart.home.controller.app.response.community.RecommendCommunityPostVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.community.dto.CommunityPostDTO;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "app-社区帖子")
@RestController
@RequestMapping("/api/app/communityPost")
public class AppCommunityPostController {

    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private SysDictService sysDictService;


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
        List<CommunityPostDTO> list = communityPostService.queryRecommendPostList(pageNum, pageSize);
        if (CollUtil.isEmpty(list)) {
            return APIResponse.OK(new ResponsePageBean<>());
        }
        List<RecommendCommunityPostVO> resultList = BeanCopyUtils.convertListTo(list, RecommendCommunityPostVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
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
        if (CollUtil.isEmpty(communityPosts)) {
            return APIResponse.OK(new ResponsePageBean<>());
        }
        List<RecommendCommunityPostVO> recommendCommunityPostVOS = BeanCopyUtils.convertListTo(communityPosts, RecommendCommunityPostVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(recommendCommunityPostVOS, communityPosts));
    }

    @RoleAccess(RoleConsts.REGISTER)
    @ApiOperation(value = "我的帖子列表(草稿/非草稿)", notes = "state 0草稿，1已发布 2已删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @GetMapping("/mine/page/{state}")
    public APIResponse<ResponsePageBean<CommunityPostVO>> draftList(@PathVariable Integer state, int pageNum, int pageSize){
        List<CommunityPost> page = communityPostService.page(UserUtils.getLoginUserId(), state, pageNum, pageSize);
        if (CollUtil.isEmpty(page)) {
            return APIResponse.OK(new ResponsePageBean<>());
        }
        List<CommunityPostVO> communityPostVOS = BeanCopyUtils.convertListTo(page, CommunityPostVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(communityPostVOS, page));
    }

    @AnonAccess
    @ApiOperation("帖子详情（草稿/发布都可查询）")
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
            if (communityPostDTO != null) {
                BeanUtils.copyProperties(communityPostDTO, communityPostDetailVO);
            }
            boolean hasFocusThisAuthor = communityPostService.hasFocusThisAuthor(userId, communityPostDTO.getUserId());
            if (hasFocusThisAuthor) {
                communityPostDetailVO.setFocusFlag(YesNoEnum.YES.getCode());
            } else {
                communityPostDetailVO.setFocusFlag(YesNoEnum.NO.getCode());
            }
        }else {
            //用户未登录
            CommunityPostDTO communityPostDTO = communityPostService.queryDetailNotLogin(id);
            if (communityPostDTO != null) {
                BeanUtils.copyProperties(communityPostDTO, communityPostDetailVO);
            }
            communityPostDetailVO.setFocusFlag(YesNoEnum.NO.getCode());
        }
        if (communityPostDetailVO.getReportCount() == null) {
            communityPostDetailVO.setReportCount(0);
        }
        if (communityPostDetailVO.getReplyCount() == null) {
            communityPostDetailVO.setReplyCount(0);
        }
        if (communityPostDetailVO.getShareCount() == null) {
            communityPostDetailVO.setShareCount(0);
        }
        communityPostDetailVO.setHref(sysDictService.queryValueByDictCode("share.link.prefix.post")+"?id="+communityPostDetailVO.getId());
        return APIResponse.OK(communityPostDetailVO);
    }

    @RoleAccess(RoleConsts.REGISTER)
    @ApiOperation(value = "保存帖子为草稿", notes = "注意：传帖子id代表原草稿更新，不传id代表新生成一条草稿记录。用于控制是新建保存或者是修改保存")
    @PostMapping("/draft/save")
    public APIResponse draftSave(@RequestBody CommunityPostReq communityPostReq){
        log.info("发帖params：{}", JSON.toJSONString(communityPostReq));
        //用户是否存在，检查是否禁言
        CommunityPostDTO communityPostDTO = new CommunityPostDTO();
        BeanUtils.copyProperties(communityPostReq, communityPostDTO);
        communityPostDTO.setUserId(UserUtils.getLoginUserId());
        communityPostDTO.setImages(JSON.toJSONString(communityPostReq.getList()));
        communityPostDTO.setImagesList(communityPostReq.getList());
        Long id = communityPostService.executeSavePost(communityPostDTO);
        return APIResponse.OK(id);
    }


    @RoleAccess(RoleConsts.REGISTER)
    @ApiOperation(value = "发布帖子", notes = "注意：在点击发布帖子前需要最后一次调用保存帖子的接口")
    @PostMapping("/release/{id}")
    public APIResponse release(@PathVariable Long id){
        log.info("发布帖子接受：{}", id);
        communityPostService.release(id);
        return APIResponse.OK();
    }



}
