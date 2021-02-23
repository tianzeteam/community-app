package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserPrivacySetting;
import com.smart.home.modules.user.service.UserPrivacySettingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户隐私设置接口")
@RestController
@RequestMapping("/api/pc/userPrivacySetting")
public class UserPrivacySettingController {

    @Autowired
    private UserPrivacySettingService userPrivacySettingService;

    @ApiOperation("创建用户隐私设置")
    @PostMapping("/create")
    public APIResponse create(UserPrivacySetting userPrivacySetting) {
        return APIResponse.OK(userPrivacySettingService.create(userPrivacySetting));
    }

    @ApiOperation("更新用户隐私设置")
    @PostMapping("/update")
    public APIResponse update(UserPrivacySetting userPrivacySetting) {
        return APIResponse.OK(userPrivacySettingService.update(userPrivacySetting));
    }

    @ApiOperation("删除用户隐私设置")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userPrivacySettingService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户隐私设置")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserPrivacySetting>> selectByPage(UserPrivacySetting userPrivacySetting, int pageNum, int pageSize) {
        List<UserPrivacySetting> list = userPrivacySettingService.selectByPage(userPrivacySetting, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户隐私设置")
    @GetMapping("/selectById")
    public APIResponse<UserPrivacySetting> selectById(Long id) {
        return APIResponse.OK(userPrivacySettingService.findById(id));
    }

}