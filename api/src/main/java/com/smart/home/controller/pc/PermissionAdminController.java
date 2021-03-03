package com.smart.home.controller.pc;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.user.UserAdminPermitUpdateDTO;
import com.smart.home.controller.pc.request.user.UserAdminSearchDTO;
import com.smart.home.controller.pc.response.user.AdminPermissionVO;
import com.smart.home.controller.pc.response.user.UserAccountWithAdminPermitVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.system.entity.SysMenu;
import com.smart.home.modules.system.service.SysMenuService;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Api(tags = "权限管理")
@RestController
@RequestMapping("/api/pc/permission")
public class PermissionAdminController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private UserAccountService userAccountService;

    @ApiOperation("查询所有后台管理权限-下拉框用")
    @GetMapping("/selectAllPermit")
    public APIResponse<List<AdminPermissionVO>> selectAllPermit() {
        List<SysMenu> list = sysMenuService.selectAllValidByPid(0);
        List<AdminPermissionVO> resultList = BeanCopyUtils.convertListTo(list, AdminPermissionVO::new,(s, t)->{
           t.setPermitName(s.getName());
           t.setPermitCode(s.getPermit());
        });
        return APIResponse.OK(resultList);
    }

    @ApiOperation("查询当前登陆用户的后台权限列表")
    @GetMapping("/selectMyAdminPermit")
    public APIResponse selectMyAdminPermit() {
        return APIResponse.OK(userAccountService.selectMyAdminPermit(UserUtils.getLoginUserId()));
    }

    @ApiOperation("查询带后台权限设置的用户列表-分页")
    @PostMapping("/selectUserWithPermitByPage")
    public APIResponse<ResponsePageBean<UserAccountWithAdminPermitVO>> selectUserWithPermitByPage(@RequestBody UserAdminSearchDTO userAdminSearchDTO) {
        List<Long> idList = userAdminSearchDTO.getIdList();
        String nickName = userAdminSearchDTO.getNickName();
        String permitCode = userAdminSearchDTO.getPermitCode();
        int pageNum = userAdminSearchDTO.getPageNum();
        int pageSize = userAdminSearchDTO.getPageSize();
        List<UserAccount> list = userAccountService.selectByIdAndNicknameAndPermit(idList, nickName, permitCode, pageNum, pageSize);
        List<UserAccountWithAdminPermitVO> resultList = BeanCopyUtils.convertListTo(list, UserAccountWithAdminPermitVO::new, (s, t)->{
            t.setPermissions(JSON.parseObject(s.getPermits(), Map.class));
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("更新用户后台权限-授权/撤销")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/updateUserAdminPermit")
    public APIResponse updateUserAdminPermit(@RequestBody List<UserAdminPermitUpdateDTO> userAdminPermitUpdateDTOList) {
        for (UserAdminPermitUpdateDTO userAdminPermitUpdateDTO : userAdminPermitUpdateDTOList) {
            userAccountService.updateUserAdminPermit(userAdminPermitUpdateDTO.getId(), userAdminPermitUpdateDTO.getPermits());
        }
        return APIResponse.OK();
    }

}
