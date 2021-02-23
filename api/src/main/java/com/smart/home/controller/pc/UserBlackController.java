package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserBlack;
import com.smart.home.modules.user.service.UserBlackService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户黑名单接口")
@RestController
@RequestMapping("/api/pc/userBlack")
public class UserBlackController {

    @Autowired
    private UserBlackService userBlackService;

    @ApiOperation("创建用户黑名单")
    @PostMapping("/create")
    public APIResponse create(UserBlack userBlack) {
        return APIResponse.OK(userBlackService.create(userBlack));
    }

    @ApiOperation("更新用户黑名单")
    @PostMapping("/update")
    public APIResponse update(UserBlack userBlack) {
        return APIResponse.OK(userBlackService.update(userBlack));
    }

    @ApiOperation("删除用户黑名单")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userBlackService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户黑名单")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserBlack>> selectByPage(UserBlack userBlack, int pageNum, int pageSize) {
        List<UserBlack> list = userBlackService.selectByPage(userBlack, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户黑名单")
    @GetMapping("/selectById")
    public APIResponse<UserBlack> selectById(Long id) {
        return APIResponse.OK(userBlackService.findById(id));
    }

}