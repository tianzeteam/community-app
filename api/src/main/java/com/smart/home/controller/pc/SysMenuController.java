package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.util.UserUtils;
import com.smart.home.controller.pc.request.SysMenuSearchDTO;
import com.smart.home.modules.system.entity.SysMenu;
import com.smart.home.modules.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Api(tags = "system系统菜单接口")
@RestController
@RequestMapping("/api/pc/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "创建菜单")
    @PostMapping(value = "/create")
    public APIResponse create(SysMenu sysMenu) {
        sysMenu.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(sysMenuService.insert(sysMenu));
    }

    @ApiOperation(value = "更新菜单")
    @PostMapping(value = "/update")
    public APIResponse update(SysMenu sysMenu) {
        sysMenu.setUpdatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(sysMenuService.update(sysMenu));
    }

    @ApiOperation(value = "删除菜单")
    @PostMapping(value = "/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        return APIResponse.OK(sysMenuService.delete(idListBean.getIdList()));
    }

    @ApiOperation(value = "分页查询菜单")
    @PostMapping(value = "selectByPage")
    public APIResponse<ResponsePageBean<SysMenu>> selectByPage(SysMenuSearchDTO sysMenuSearchDTO) {
        SysMenu entity = new SysMenu();
        BeanUtils.copyProperties(sysMenuSearchDTO, entity);
        return APIResponse.OK(ResponsePageBean.restPage(sysMenuService.selectByPage(entity, sysMenuSearchDTO.getPageNum(), sysMenuSearchDTO.getPageSize())));
    }

}
