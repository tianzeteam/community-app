package com.smart.home.controller.app;

import com.smart.home.controller.app.response.UserPrivacySettingVO;
import com.smart.home.controller.app.response.UserPrivacyVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserPrivacy;
import com.smart.home.modules.user.entity.UserPrivacySetting;
import com.smart.home.modules.user.service.UserPrivacySettingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Api(tags = "设置-用户隐私设置")
@RestController
@RequestMapping("/api/app/userPrivacy")
public class AppUserPrivacyController {

    @Autowired
    private UserPrivacySettingService userPrivacySettingService;

    @ApiOperation("查询隐私页面数据")
    @RoleAccess("register")
    @GetMapping("queryPrivacy")
    public APIResponse<List<UserPrivacyVO>> queryPrivacy() {
        UserPrivacyVO vo1 = new UserPrivacyVO();
        vo1.setPrivacyType(0);
        vo1.setTitle("动态隐私设置");
        UserPrivacyVO vo2 = new UserPrivacyVO();
        vo2.setPrivacyType(1);
        vo2.setTitle("个人主页隐私设置");
        return APIResponse.OK(Arrays.asList(vo1, vo2));
    }

    @ApiOperation("按类型查看用户的隐私设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "privacyType", value = "0动态隐私设置1个人主页隐私设置", required = true)
    })
    @RoleAccess("register")
    @GetMapping("/querySettingByPrivacyType")
    public APIResponse<List<UserPrivacySettingVO>> queryByPrivacyType(Integer privacyType) {
        Long userId = UserUtils.getLoginUserId();
        List<UserPrivacy> privacyList = userPrivacySettingService.queryByPrivacyType(userId, privacyType);
        List<UserPrivacySettingVO> list = new ArrayList<>();
        for (UserPrivacy userPrivacy : privacyList) {
            UserPrivacySettingVO vo = new UserPrivacySettingVO();
            vo.setPrivacyId(userPrivacy.getId());
            vo.setPrivacyName(userPrivacy.getTitle());
            UserPrivacySetting userPrivacySetting = userPrivacy.getUserPrivacySetting();
            if (Objects.isNull(userPrivacySetting)) {
                vo.setCheckFlag(0);
            } else {
                vo.setCheckFlag(userPrivacySetting.getCheckFlag());
            }
            list.add(vo);
        }
        return APIResponse.OK(list);
    }

    @ApiOperation("更新用户隐私设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "privacyId", value = "隐私主键ID", required = true),
            @ApiImplicitParam(name = "checkFlag", value = "是否勾选0否1是", required = true)
    })
    @RoleAccess("register")
    @PostMapping("/updatePrivacySetting")
    public APIResponse updatePrivacySetting(Integer privacyId, Integer checkFlag) {
        Long userId = UserUtils.getLoginUserId();
        userPrivacySettingService.updatePrivacySetting(userId, privacyId, checkFlag);
        return APIResponse.OK();
    }

}
