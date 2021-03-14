package com.smart.home.controller.pc;

import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.community.CommunityCreateDTO;
import com.smart.home.controller.pc.request.community.CommunityUpdateDTO;
import com.smart.home.controller.pc.response.community.CommunitySelectVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.Community;
import com.smart.home.modules.community.service.CommunityService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "配置中心-社区")
@RestController
@RequestMapping("/api/pc/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @ApiOperation("创建社区")
    @PostMapping("/create")
    public APIResponse create(@Valid CommunityCreateDTO communityCreateDTO, BindingResult bindingResult) {
        Community community = new Community();
        BeanUtils.copyProperties(communityCreateDTO, community);
        community.setCreatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(communityService.create(community));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("更新社区")
    @PostMapping("/update")
    public APIResponse update(@Valid CommunityUpdateDTO communityUpdateDTO, BindingResult bindingResult) {
        Community community = new Community();
        community.setCreatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(communityService.update(community));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("删除社区")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        try {
            communityService.delete(idListBean.getIdList());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("分页查询社区")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "按名称查询", required = false),
            @ApiImplicitParam(name = "categoryId", value = "社区类目主键id", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<Community>> selectByPage(String title,Long categoryId, int pageNum, int pageSize) {
        Community community = new Community();
        community.setTitle(title);
        community.setCategoryId(categoryId);
        List<Community> list = communityService.selectByPage(community, pageNum, pageSize);
        return APIResponse.OK(ResponsePageUtil.restPage(list, list));
    }

    @ApiOperation("按主键ID查询社区")
    @GetMapping("/selectById")
    public APIResponse<Community> selectById(Long id) {
        return APIResponse.OK(communityService.findById(id));
    }

    @ApiOperation("下拉选择社区")
    @GetMapping("/selectSelectItems")
    public APIResponse<List<CommunitySelectVO>> selectSelectItems() {
        List<Community> list = communityService.queryAllValid();
        List<CommunitySelectVO> resultList = BeanCopyUtils.convertListTo(list, CommunitySelectVO::new);
        return APIResponse.OK(resultList);
    }

}