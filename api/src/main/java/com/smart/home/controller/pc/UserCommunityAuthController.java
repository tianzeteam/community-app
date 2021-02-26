package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
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
 **/
@Api(tags = "user用户社区权限接口")
@RestController
@RequestMapping("/api/pc/userCommunityAuth")
public class UserCommunityAuthController {

    @Autowired
    private UserCommunityAuthService userCommunityAuthService;

    @ApiOperation("更新用户社区权限")
    @PostMapping("/update")
    public APIResponse update(UserCommunityAuth userCommunityAuth) {
        return APIResponse.OK(userCommunityAuthService.update(userCommunityAuth));
    }

    @ApiOperation("删除用户社区权限")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userCommunityAuthService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户社区权限")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserCommunityAuth>> selectByPage(UserCommunityAuth userCommunityAuth, int pageNum, int pageSize) {
        List<UserCommunityAuth> list = userCommunityAuthService.selectByPage(userCommunityAuth, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户社区权限")
    @GetMapping("/selectById")
    public APIResponse<UserCommunityAuth> selectById(Long id) {
        return APIResponse.OK(userCommunityAuthService.findById(id));
    }

}