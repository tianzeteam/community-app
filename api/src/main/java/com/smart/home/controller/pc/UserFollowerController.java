package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserFollower;
import com.smart.home.modules.user.service.UserFollowerService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户粉丝接口")
@RestController
@RequestMapping("/api/pc/userFollower")
public class UserFollowerController {

    @Autowired
    private UserFollowerService userFollowerService;

    @ApiOperation("创建用户粉丝")
    @PostMapping("/create")
    public APIResponse create(UserFollower userFollower) {
        return APIResponse.OK(userFollowerService.create(userFollower));
    }

    @ApiOperation("更新用户粉丝")
    @PostMapping("/update")
    public APIResponse update(UserFollower userFollower) {
        return APIResponse.OK(userFollowerService.update(userFollower));
    }

    @ApiOperation("删除用户粉丝")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userFollowerService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户粉丝")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserFollower>> selectByPage(UserFollower userFollower, int pageNum, int pageSize) {
        List<UserFollower> list = userFollowerService.selectByPage(userFollower, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户粉丝")
    @GetMapping("/selectById")
    public APIResponse<UserFollower> selectById(Long id) {
        return APIResponse.OK(userFollowerService.findById(id));
    }

}