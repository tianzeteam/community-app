package com.smart.home.controller.app;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.community.CommunityDetailVO;
import com.smart.home.controller.app.response.community.CommunityInfoVo;
import com.smart.home.controller.pc.response.community.CommunitySelectVO;
import com.smart.home.controller.pc.response.community.CommunityUserMappingVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.es.common.PageBean;
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
import java.util.stream.Collectors;

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
        if (CollUtil.isEmpty(communitySelectVOS)) {
            return APIResponse.OK(ResponsePageUtil.restPage(communitySelectVOS, communities));
        }
        Long loginUserId = UserUtils.getLoginUserId();
        if (loginUserId != null) {
            List<CommunityUserMapping> communityUserMappings = communityUserMappingService.getByUserId(loginUserId);
            if (CollUtil.isNotEmpty(communityUserMappings)) {
                communitySelectVOS.stream().forEach(x -> {
                    communityUserMappings.stream().forEach(y -> {
                        if (x.getId().equals(y.getCommunityId())) {
                            x.setJoinFlag(1);
                        }
                    });
                });
            }
        }
        return APIResponse.OK(ResponsePageUtil.restPage(communitySelectVOS, communities));
    }

    @AnonAccess
    @ApiOperation(value = "获取所有社区")
    @GetMapping("/query/all")
    public APIResponse<List<CommunitySelectVO>> queryAll() {
        List<Community> communities = communityService.selectAll();
        List<CommunitySelectVO> communitySelectVOS = BeanCopyUtils.convertListTo(communities, CommunitySelectVO::new);
        return APIResponse.OK(communitySelectVOS);
    }

    @RoleAccess(RoleConsts.REGISTER)
    @ApiOperation("加入/不加入")
    @PostMapping("/userMapping/join")
    public APIResponse add(@RequestBody String idStr) {
        log.info("社区，加入：{}", idStr);
        Long loginUserId = UserUtils.getLoginUserId();
        CommunityUserMapping communityUserMapping = new CommunityUserMapping();
        JSONObject jsonObject = JSONObject.parseObject(idStr);
        Integer id = jsonObject.getInteger("id");
        CommunityUserMapping userMapping = communityUserMappingService.getByUserIdAndCommunityId(loginUserId, id);
        if (userMapping != null) {
            int i = communityUserMappingService.deleteById(Long.valueOf(id));
            return APIResponse.OK(i);
        }
        communityUserMapping.setCommunityId(jsonObject.getInteger("id"));
        communityUserMapping.setUserId(loginUserId);
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
        PageBean<List<CommunityUserMappingDTO>> listPageBean = communityUserMappingService.selectByPage(communityUserMapping, pageNum, pageSize);
        List<CommunityUserMappingVO> communityUserMappingVOS = BeanCopyUtils.convertListTo(listPageBean.getList(), CommunityUserMappingVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(communityUserMappingVOS, listPageBean));
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
    public APIResponse<ResponsePageBean<CommunityDetailVO>> detailPost(Long communityId, Integer boutiqueFlag, Integer pageNum, Integer pageSize) {
        List<CommunityPostDTO> communityPostDTOS = communityPostService.queryCommunityDetailPostList(communityId, boutiqueFlag, pageNum, pageSize);
        List<CommunityDetailVO> detailVOS = BeanCopyUtils.convertListTo(communityPostDTOS, CommunityDetailVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(detailVOS, communityPostDTOS));
    }

    @AnonAccess
    @ApiOperation("根据社区id查找社区信息以及是否加入")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "社区id", required = true)
    )
    @GetMapping("/info")
    public APIResponse<CommunityInfoVo> info(Long id) {
        Community community = communityService.findById(id);
        if (community == null) {
            return APIResponse.ERROR("没有此社区");
        }
        CommunityInfoVo communityInfoVo = new CommunityInfoVo();
        BeanUtils.copyProperties(community, communityInfoVo);
        Long loginUserId = UserUtils.getLoginUserId();
        if (loginUserId != null) {
            CommunityUserMapping communityUserMapping = communityUserMappingService.getByUserIdAndCommunityId(loginUserId, id.intValue());
            communityInfoVo.setIfJoin(communityUserMapping == null ? false : true);
        }
        return APIResponse.OK(communityInfoVo);
    }


}
