package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.service.UserDataService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户数据接口")
@RestController
@RequestMapping("/api/pc/userData")
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    @ApiOperation("创建用户数据")
    @PostMapping("/create")
    public APIResponse create(UserData userData) {
        userData.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userDataService.create(userData));
    }

    @ApiOperation("更新用户数据")
    @PostMapping("/update")
    public APIResponse update(UserData userData) {
        userData.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userDataService.update(userData));
    }

    @ApiOperation("删除用户数据")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userDataService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户数据")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserData>> selectByPage(UserData userData, int pageNum, int pageSize) {
        List<UserData> list = userDataService.selectByPage(userData, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户数据")
    @GetMapping("/selectById")
    public APIResponse<UserData> selectById(Long id) {
        return APIResponse.OK(userDataService.findById(id));
    }

}