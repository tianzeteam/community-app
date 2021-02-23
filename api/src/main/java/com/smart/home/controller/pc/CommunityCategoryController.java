package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.CommunityCategory;
import com.smart.home.modules.community.service.CommunityCategoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "community社区类目接口")
@RestController
@RequestMapping("/api/pc/communityCategory")
public class CommunityCategoryController {

    @Autowired
    private CommunityCategoryService communityCategoryService;

    @ApiOperation("创建社区类目")
    @PostMapping("/create")
    public APIResponse create(CommunityCategory communityCategory) {
        communityCategory.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(communityCategoryService.create(communityCategory));
    }

    @ApiOperation("更新社区类目")
    @PostMapping("/update")
    public APIResponse update(CommunityCategory communityCategory) {
        communityCategory.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(communityCategoryService.update(communityCategory));
    }

    @ApiOperation("删除社区类目")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        communityCategoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询社区类目")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<CommunityCategory>> selectByPage(CommunityCategory communityCategory, int pageNum, int pageSize) {
        List<CommunityCategory> list = communityCategoryService.selectByPage(communityCategory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询社区类目")
    @GetMapping("/selectById")
    public APIResponse<CommunityCategory> selectById(Long id) {
        return APIResponse.OK(communityCategoryService.findById(id));
    }

}