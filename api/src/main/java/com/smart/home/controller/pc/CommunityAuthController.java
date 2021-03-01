package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.response.community.CommunityAdminUserVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserCommunityAuth;
import com.smart.home.modules.user.service.UserCommunityAuthService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/1
 **/
@Api(tags = "社区管理-版务权限")
@RestController
@RequestMapping("/api/pc/communityAuth")
public class CommunityAuthController {

    @Autowired
    private UserCommunityAuthService userCommunityAuthService;

    @ApiOperation("给用户赋予版务权限")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/assign")
    public APIResponse assign(@RequestBody IdListBean idListBean) {
        userCommunityAuthService.setAsAdmin(idListBean.getIdList(), UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

    @ApiOperation("撤回用户的版务权限")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/remove")
    public APIResponse remove(@RequestBody IdListBean idListBean) {
        userCommunityAuthService.cancelAdmin(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("查询所有拥有版务权限的用户")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/queryAllAdminUser")
    public APIResponse<List<CommunityAdminUserVO>> queryAllAdminUser() {
        List<UserCommunityAuth> list = userCommunityAuthService.queryAllAdminUser();
        List<CommunityAdminUserVO> resultList = BeanCopyUtils.convertListTo(list, CommunityAdminUserVO::new);
        return APIResponse.OK(resultList);
    }

}
