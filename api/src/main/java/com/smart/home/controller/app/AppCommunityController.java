package com.smart.home.controller.app;

import com.alibaba.fastjson.JSONObject;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.community.CommunityDetailVO;
import com.smart.home.controller.pc.response.community.CommunitySelectVO;
import com.smart.home.controller.pc.response.community.CommunityUserMappingVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.es.dto.CommunitySearchDTO;
import com.smart.home.modules.community.dto.CommunityPostDTO;
import com.smart.home.modules.community.dto.CommunityUserMappingDTO;
import com.smart.home.modules.community.entity.Community;
import com.smart.home.modules.community.entity.CommunityUserMapping;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.community.service.CommunityService;
import com.smart.home.modules.community.service.CommunityUserMappingService;
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
@Api(tags = "app-社区")
@RestController
@RequestMapping("/api/app/community")
public class AppCommunityController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunityUserMappingService communityUserMappingService;
    @Autowired
    private CommunityPostService communityPostService;


    @AnonAccess
    @ApiOperation("社区列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @GetMapping("/queryList")
    public APIResponse<ResponsePageBean<CommunitySelectVO>> queryList(CommunitySearchDTO communitySearchDTO, Integer pageNum, Integer pageSize) {
        Community community = new Community();
        BeanUtils.copyProperties(communitySearchDTO, community);
        List<Community> communities = communityService.selectByPage(community, pageNum, pageSize);
        List<CommunitySelectVO> communitySelectVOS = BeanCopyUtils.convertListTo(communities, CommunitySelectVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(communitySelectVOS, communities));
    }

    @AnonAccess
    @ApiOperation("加入")
    @PostMapping("/userMapping/join")
    public APIResponse add(@RequestBody String idStr) {
        log.info("社区，加入：{}", idStr);
        CommunityUserMapping communityUserMapping = new CommunityUserMapping();
        JSONObject jsonObject = JSONObject.parseObject(idStr);
        communityUserMapping.setCommunityId(jsonObject.getInteger("id"));
        communityUserMapping.setUserId(UserUtils.getLoginUserId());
        int i = communityUserMappingService.create(communityUserMapping);
        return APIResponse.OK(i);
    }

    @AnonAccess
    @ApiOperation("我加入的社区列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码a", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @GetMapping("/mine/queryList")
    public APIResponse<ResponsePageBean<CommunityUserMappingVO>> myQueryList(Integer pageNum, Integer pageSize) {
        CommunityUserMapping communityUserMapping = new CommunityUserMapping();
        communityUserMapping.setUserId(UserUtils.getLoginUserId());
        List<CommunityUserMappingDTO> userMappingDTOS = communityUserMappingService.selectByPage(communityUserMapping, pageNum, pageSize);
        List<CommunityUserMappingVO> communityUserMappingVOS = BeanCopyUtils.convertListTo(userMappingDTOS, CommunityUserMappingVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(communityUserMappingVOS));
    }

    /**
     * 社区下有哪些帖子，精华帖子
     */
    @AnonAccess
    @ApiOperation("社区详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "社区id", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码a", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true),
            @ApiImplicitParam(name = "boutiqueFlag", value = "是否是精品，0否1是，无选择默认为null", required = false)
    })
    @GetMapping("/detail/post")
    public APIResponse<ResponsePageBean<CommunityDetailVO>> detailPost(Long communityId, Integer boutiqueFlag, Integer pageNum, Integer pageSize){
        List<CommunityPostDTO> communityPostDTOS = communityPostService.queryCommunityDetailPostList(communityId, boutiqueFlag, pageNum, pageSize);
        List<CommunityDetailVO> detailVOS = BeanCopyUtils.convertListTo(communityPostDTOS, CommunityDetailVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(detailVOS, communityPostDTOS));
    }


}
