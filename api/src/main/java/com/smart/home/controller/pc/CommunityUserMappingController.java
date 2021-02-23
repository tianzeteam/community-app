package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.CommunityUserMapping;
import com.smart.home.modules.community.service.CommunityUserMappingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "community用户加入的社区接口")
@RestController
@RequestMapping("/api/pc/communityUserMapping")
public class CommunityUserMappingController {

    @Autowired
    private CommunityUserMappingService communityUserMappingService;

    @ApiOperation("创建用户加入的社区")
    @PostMapping("/create")
    public APIResponse create(CommunityUserMapping communityUserMapping) {
        return APIResponse.OK(communityUserMappingService.create(communityUserMapping));
    }

    @ApiOperation("更新用户加入的社区")
    @PostMapping("/update")
    public APIResponse update(CommunityUserMapping communityUserMapping) {
        return APIResponse.OK(communityUserMappingService.update(communityUserMapping));
    }

    @ApiOperation("删除用户加入的社区")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityUserMappingService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户加入的社区")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<CommunityUserMapping>> selectByPage(CommunityUserMapping communityUserMapping, int pageNum, int pageSize) {
        List<CommunityUserMapping> list = communityUserMappingService.selectByPage(communityUserMapping, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户加入的社区")
    @GetMapping("/selectById")
    public APIResponse<CommunityUserMapping> selectById(Long id) {
        return APIResponse.OK(communityUserMappingService.findById(id));
    }

}