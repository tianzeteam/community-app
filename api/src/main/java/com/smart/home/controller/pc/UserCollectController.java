package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserCollect;
import com.smart.home.modules.user.service.UserCollectService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户收藏接口")
@RestController
@RequestMapping("/api/pc/userCollect")
public class UserCollectController {

    @Autowired
    private UserCollectService userCollectService;

    @ApiOperation("创建用户收藏")
    @PostMapping("/create")
    public APIResponse create(UserCollect userCollect) {
        userCollect.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userCollectService.create(userCollect));
    }

    @ApiOperation("更新用户收藏")
    @PostMapping("/update")
    public APIResponse update(UserCollect userCollect) {
        userCollect.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userCollectService.update(userCollect));
    }

    @ApiOperation("删除用户收藏")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userCollectService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户收藏")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserCollect>> selectByPage(UserCollect userCollect, int pageNum, int pageSize) {
        List<UserCollect> list = userCollectService.selectByPage(userCollect, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户收藏")
    @GetMapping("/selectById")
    public APIResponse<UserCollect> selectById(Long id) {
        return APIResponse.OK(userCollectService.findById(id));
    }

}