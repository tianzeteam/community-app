package com.smart.home.controller.pc;

import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.community.CommunityCategoryCreateDTO;
import com.smart.home.controller.pc.request.community.CommunityCategoryUpdateDTO;
import com.smart.home.controller.pc.response.community.CommunityCategorySelectVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.community.entity.CommunityCategory;
import com.smart.home.modules.community.service.CommunityCategoryService;
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
@Api(tags = "配置中心-社区类目")
@RestController
@RequestMapping("/api/pc/communityCategory")
public class CommunityCategoryController {

    @Autowired
    private CommunityCategoryService communityCategoryService;

    @ApiOperation("创建社区类目")
    @PostMapping("/create")
    public APIResponse create(@Valid CommunityCategoryCreateDTO communityCategoryCreateDTO, BindingResult bindingResult) {
        CommunityCategory communityCategory = new CommunityCategory();
        BeanUtils.copyProperties(communityCategoryCreateDTO, communityCategory);
        communityCategory.setCreatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(communityCategoryService.create(communityCategory));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("更新社区类目")
    @PostMapping("/update")
    public APIResponse update(@Valid CommunityCategoryUpdateDTO communityCategoryUpdateDTO, BindingResult bindingResult) {
        CommunityCategory communityCategory = new CommunityCategory();
        BeanUtils.copyProperties(communityCategoryUpdateDTO, communityCategory);
        communityCategory.setCreatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(communityCategoryService.update(communityCategory));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("删除社区类目")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        try {
            communityCategoryService.delete(idListBean.getIdList());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("分页查询社区类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "按名称查询", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<CommunityCategory>> selectByPage(String title, int pageNum, int pageSize) {
        CommunityCategory communityCategory = new CommunityCategory();
        communityCategory.setTitle(title);
        List<CommunityCategory> list = communityCategoryService.selectByPage(communityCategory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageUtil.restPage(list, list));
    }

    @ApiOperation("按主键ID查询社区类目")
    @GetMapping("/selectById")
    public APIResponse<CommunityCategory> selectById(Integer id) {
        return APIResponse.OK(communityCategoryService.findById(id));
    }

    @ApiOperation("下拉选择社区类目")
    @GetMapping("/selectSelectItems")
    public APIResponse<List<CommunityCategorySelectVO>> selectSelectItems() {
        List<CommunityCategory> list = communityCategoryService.queryAllValid();
        List<CommunityCategorySelectVO> resultList = BeanCopyUtils.convertListTo(list, CommunityCategorySelectVO::new);
        return APIResponse.OK(resultList);
    }

}