package com.smart.home.controller.app;

import com.smart.home.controller.app.response.UserPrivacySettingVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserPrivacy;
import com.smart.home.modules.user.entity.UserPrivacySetting;
import com.smart.home.modules.user.service.UserPrivacySettingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Api(tags = "user用户隐私设置")
@RestController
@RequestMapping("/api/app/userPrivacy")
public class AppUserPrivacyController {

    @Autowired
    private UserPrivacySettingService userPrivacySettingService;

    @RoleAccess("register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "privacyType", value = "0动态隐私设置1个人主页隐私设置", required = true)
    })
    @GetMapping("queryByPrivacyType")
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

}
