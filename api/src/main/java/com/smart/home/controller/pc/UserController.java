package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.controller.pc.response.user.UserAdminVO;
import com.smart.home.controller.pc.response.user.UserDataAdminVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.service.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/pc/user")
public class UserController {

    @Autowired
    private UserDataService userDataService;

    @ApiOperation("根据用户id或者昵称搜索用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户主键id", required = false),
            @ApiImplicitParam(name = "nickName", value = "用户昵称", required = false)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectByIdOrNickname")
    public APIResponse<UserAdminVO> selectByIdOrNickname(Long userId, String nickName) {
        if(StringUtils.isBlank(nickName)) {
            nickName = null;
        }
        UserData userData = userDataService.selectByIdOrNickname(userId, nickName);
        if (Objects.isNull(userData)) {
            return APIResponse.ERROR("没有找到相关用户");
        }
        UserAdminVO userAdminVO = new UserAdminVO();
        BeanUtils.copyProperties(userData, userAdminVO);
        UserDataAdminVO userDataAdminVO = new UserDataAdminVO();
        BeanUtils.copyProperties(userData, userDataAdminVO);
        userAdminVO.setUserDataAdminVO(userDataAdminVO);
        userAdminVO.setUserCurrentStatus("正常");
        if (YesNoEnum.YES.equals(userData.getSpeakFlag())) {
            userAdminVO.setUserCurrentStatus("禁言");
        }
        if (YesNoEnum.YES.equals(userData.getBlackFlag())) {
            userAdminVO.setUserCurrentStatus("封禁");
        }
        return APIResponse.OK(userAdminVO);
    }

}
