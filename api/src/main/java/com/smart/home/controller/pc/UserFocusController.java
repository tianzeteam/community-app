package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserFocus;
import com.smart.home.modules.user.service.UserFocusService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户关注接口")
@RestController
@RequestMapping("/api/pc/userFocus")
public class UserFocusController {

    @Autowired
    private UserFocusService userFocusService;

    @ApiOperation("创建用户关注")
    @PostMapping("/create")
    public APIResponse create(UserFocus userFocus) {
        return APIResponse.OK(userFocusService.create(userFocus));
    }

    @ApiOperation("更新用户关注")
    @PostMapping("/update")
    public APIResponse update(UserFocus userFocus) {
        return APIResponse.OK(userFocusService.update(userFocus));
    }

    @ApiOperation("删除用户关注")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userFocusService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户关注")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserFocus>> selectByPage(UserFocus userFocus, int pageNum, int pageSize) {
        List<UserFocus> list = userFocusService.selectByPage(userFocus, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户关注")
    @GetMapping("/selectById")
    public APIResponse<UserFocus> selectById(Long id) {
        return APIResponse.OK(userFocusService.findById(id));
    }

}