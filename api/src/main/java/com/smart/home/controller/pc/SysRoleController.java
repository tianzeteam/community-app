package com.smart.home.controller.pc;

import com.smart.home.controller.pc.request.system.SysRoleDTO;
import com.smart.home.controller.pc.request.system.SysRoleMenuAssignDTO;
import com.smart.home.controller.pc.request.system.SysRoleSearchDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.system.entity.SysRole;
import com.smart.home.modules.system.service.SysRoleService;
import com.smart.home.util.ResponsePageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Api(tags = "system系统角色接口")
@RestController
@RequestMapping("/api/pc/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "创建角色", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse create(SysRoleDTO sysRoleDTO) {
        SysRole entity = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, entity);
        return APIResponse.OK(sysRoleService.insert(entity));
    }

    @ApiOperation(value = "更新角色", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse update(SysRoleDTO sysRoleDTO) {
        SysRole entity = new SysRole();
        BeanUtils.copyProperties(sysRoleDTO, entity);
        return APIResponse.OK(sysRoleService.update(entity));
    }

    @ApiOperation(value = "删除角色")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        return APIResponse.OK(sysRoleService.delete(idListBean.getIdList()));
    }

    @ApiOperation(value = "分页查询角色", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(value = "selectByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse<ResponsePageBean<SysRole>> selectByPage(SysRoleSearchDTO sysRoleSearchDTO) {
        SysRole entity = new SysRole();
        BeanUtils.copyProperties(sysRoleSearchDTO, entity);
        List<SysRole> list = sysRoleService.selectByPage(entity, sysRoleSearchDTO.getPageNum(), sysRoleSearchDTO.getPageSize());
        return APIResponse.OK(ResponsePageUtil.restPage(list, list));
    }

    @ApiOperation(value = "授权菜单")
    @PostMapping("/assignMenus")
    public APIResponse assignMenus(@Valid @RequestBody SysRoleMenuAssignDTO sysRoleMenuAssignDTO, BindingResult bindingResult) {
        sysRoleService.assignMenus(sysRoleMenuAssignDTO.getRoleId(), sysRoleMenuAssignDTO.getMenuIdList());
        return APIResponse.OK();
    }

    @ApiOperation(value = "查询角色下所有菜单")
    @PostMapping("/queryAllMenus")
    public APIResponse queryAllMenus(@RequestBody IdListBean idListBean) {
        return APIResponse.OK(sysRoleService.queryAllMenus(idListBean.getIdList()));
    }

}
