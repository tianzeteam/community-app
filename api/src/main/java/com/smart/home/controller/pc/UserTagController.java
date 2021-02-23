package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserTag;
import com.smart.home.modules.user.service.UserTagService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户标签接口")
@RestController
@RequestMapping("/api/pc/userTag")
public class UserTagController {

    @Autowired
    private UserTagService userTagService;

    @ApiOperation("创建用户标签")
    @PostMapping("/create")
    public APIResponse create(UserTag userTag) {
        return APIResponse.OK(userTagService.create(userTag));
    }

    @ApiOperation("更新用户标签")
    @PostMapping("/update")
    public APIResponse update(UserTag userTag) {
        return APIResponse.OK(userTagService.update(userTag));
    }

    @ApiOperation("删除用户标签")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userTagService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户标签")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserTag>> selectByPage(UserTag userTag, int pageNum, int pageSize) {
        List<UserTag> list = userTagService.selectByPage(userTag, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户标签")
    @GetMapping("/selectById")
    public APIResponse<UserTag> selectById(Long id) {
        return APIResponse.OK(userTagService.findById(id));
    }

}