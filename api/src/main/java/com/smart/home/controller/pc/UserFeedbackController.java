package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.user.entity.UserFeedback;
import com.smart.home.modules.user.service.UserFeedbackService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "user用户反馈接口")
@RestController
@RequestMapping("/api/pc/userFeedback")
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @ApiOperation("创建用户反馈")
    @PostMapping("/create")
    public APIResponse create(UserFeedback userFeedback) {
        userFeedback.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userFeedbackService.create(userFeedback));
    }

    @ApiOperation("更新用户反馈")
    @PostMapping("/update")
    public APIResponse update(UserFeedback userFeedback) {
        userFeedback.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userFeedbackService.update(userFeedback));
    }

    @ApiOperation("删除用户反馈")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userFeedbackService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户反馈")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserFeedback>> selectByPage(UserFeedback userFeedback, int pageNum, int pageSize) {
        List<UserFeedback> list = userFeedbackService.selectByPage(userFeedback, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询用户反馈")
    @GetMapping("/selectById")
    public APIResponse<UserFeedback> selectById(Long id) {
        return APIResponse.OK(userFeedbackService.findById(id));
    }

}