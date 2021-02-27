package com.smart.home.controller.pc;

import com.alibaba.fastjson.annotation.JSONField;
import com.smart.home.assembler.UserAssembler;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.UserRole;
import com.smart.home.util.UserUtils;
import com.smart.home.controller.pc.request.user.UserAccountCreateDTO;
import com.smart.home.controller.pc.request.user.UserAccountSearchDTO;
import com.smart.home.controller.pc.request.user.UserRoleAssignDTO;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserRoleMapping;
import com.smart.home.modules.user.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Api(tags = "user用户账户接口")
@RestController
@RequestMapping("/api/pc/userAccount")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @ApiOperation(value = "创建用户账户")
    @PostMapping(value = "/create")
    public APIResponse create(@RequestBody UserAccountCreateDTO userAccountCreateDTO) {
        UserAccount entity = new UserAccount();
        BeanUtils.copyProperties(userAccountCreateDTO, entity);
        entity.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userAccountService.insert(entity, userAccountCreateDTO.getRoleIdList()));
    }

    @ApiOperation(value = "删除用户账户")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        return APIResponse.OK(userAccountService.delete(idListBean.getIdList()));
    }

    @ApiOperation(value = "分页查询用户账户", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(value = "selectByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse<ResponsePageBean<UserAccount>> selectByPage(UserAccountSearchDTO userAccountSearchDTO) {
        UserAccount entity = new UserAccount();
        BeanUtils.copyProperties(userAccountSearchDTO, entity);
        return APIResponse.OK(ResponsePageBean.restPage(userAccountService.selectByPage(entity, userAccountSearchDTO.getPageNum(), userAccountSearchDTO.getPageSize())));
    }

    @ApiOperation(value = "授权用户角色")
    @PostMapping(value = "/assignRoles", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse assignRoles(@Valid @RequestBody UserRoleAssignDTO userRoleAssignDTO, @JSONField(serialize=false) BindingResult bindingResult) {
        userAccountService.assignRoles(userRoleAssignDTO.getUserId(), userRoleAssignDTO.getRoleIdList());
        return APIResponse.OK();
    }

    @ApiOperation(value = "查询用户角色")
    @GetMapping(value = "/queryUserRoles", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse<List<UserRole>> queryUserRoles(@RequestParam Long userId) {
        List<UserRoleMapping> list = userAccountService.findUserRoles(userId);
        List<UserRole> userRoles = UserAssembler.assemblerUserRoles(list);
        return APIResponse.OK(userRoles);
    }

}
