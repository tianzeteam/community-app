package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserPrivacy;
import com.smart.home.modules.user.service.UserPrivacyService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户隐私接口")
@RestController
@RequestMapping("/api/pc/userPrivacy")
public class UserPrivacyController {

    @Autowired
    private UserPrivacyService userPrivacyService;

    @ApiOperation("创建用户隐私")
    @PostMapping("/create")
    public APIResponse create(UserPrivacy userPrivacy) {
        userPrivacy.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userPrivacyService.create(userPrivacy));
    }

    @ApiOperation("更新用户隐私")
    @PostMapping("/update")
    public APIResponse update(UserPrivacy userPrivacy) {
        userPrivacy.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userPrivacyService.update(userPrivacy));
    }

    @ApiOperation("删除用户隐私")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userPrivacyService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户隐私")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserPrivacy>> selectByPage(UserPrivacy userPrivacy, int pageNum, int pageSize) {
        List<UserPrivacy> list = userPrivacyService.selectByPage(userPrivacy, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户隐私")
    @GetMapping("/selectById")
    public APIResponse<UserPrivacy> selectById(Long id) {
        return APIResponse.OK(userPrivacyService.findById(id));
    }

}