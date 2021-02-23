package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserArticleChannelPreference;
import com.smart.home.modules.user.service.UserArticleChannelPreferenceService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户频道偏好设置接口")
@RestController
@RequestMapping("/api/pc/userArticleChannelPreference")
public class UserArticleChannelPreferenceController {

    @Autowired
    private UserArticleChannelPreferenceService userArticleChannelPreferenceService;

    @ApiOperation("创建用户频道偏好设置")
    @PostMapping("/create")
    public APIResponse create(UserArticleChannelPreference userArticleChannelPreference) {
        return APIResponse.OK(userArticleChannelPreferenceService.create(userArticleChannelPreference));
    }

    @ApiOperation("更新用户频道偏好设置")
    @PostMapping("/update")
    public APIResponse update(UserArticleChannelPreference userArticleChannelPreference) {
        return APIResponse.OK(userArticleChannelPreferenceService.update(userArticleChannelPreference));
    }

    @ApiOperation("删除用户频道偏好设置")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userArticleChannelPreferenceService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户频道偏好设置")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserArticleChannelPreference>> selectByPage(UserArticleChannelPreference userArticleChannelPreference, int pageNum, int pageSize) {
        List<UserArticleChannelPreference> list = userArticleChannelPreferenceService.selectByPage(userArticleChannelPreference, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户频道偏好设置")
    @GetMapping("/selectById")
    public APIResponse<UserArticleChannelPreference> selectById(Long id) {
        return APIResponse.OK(userArticleChannelPreferenceService.findById(id));
    }

}