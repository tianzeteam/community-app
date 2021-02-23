package com.smart.home.controller.app;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.FeedbackStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.FeedbackCreateDTO;
import com.smart.home.controller.app.response.FeedbackVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserFeedback;
import com.smart.home.modules.user.service.UserFeedbackService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Api(tags = "设置-反馈建议")
@RestController
@RequestMapping("/api/app/feedback")
public class AppFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @ApiOperation("分页查询反馈记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryByPage")
    public APIResponse<ResponsePageBean<FeedbackVO>> queryByPage(int pageNum, int pageSize) {
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setUserId(UserUtils.getLoginUserId());
        List<UserFeedback> list = userFeedbackService.selectByPage(userFeedback, pageNum, pageSize);
        List<FeedbackVO> feedbackVOList = BeanCopyUtils.convertListTo(list, FeedbackVO::new);
        return APIResponse.OK(ResponsePageBean.restPage(feedbackVOList));
    }

    @ApiOperation("创建反馈")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/create")
    public APIResponse create(@Valid @RequestBody FeedbackCreateDTO feedbackCreateDTO, BindingResult bindingResult) {
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setUserId(UserUtils.getLoginUserId());
        userFeedback.setState(FeedbackStatusEnum.WAIT_REPLY.getStatus());
        userFeedback.setCreatedBy(UserUtils.getLoginUserId());
        userFeedback.setContents(feedbackCreateDTO.getContents());
        if (feedbackCreateDTO.getImages() != null) {
            userFeedback.setImages(JSON.toJSONString(feedbackCreateDTO.getImages()));
        }
        userFeedback.setReadFlag(YesNoEnum.NO.getCode());
        return APIResponse.OK(userFeedbackService.create(userFeedback));
    }

    @ApiOperation("按主键ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryById")
    public APIResponse<FeedbackVO> queryById(Long id) {
        UserFeedback userFeedback = userFeedbackService.findById(id);
        return APIResponse.OK(BeanCopyUtils.convertTo(userFeedback, FeedbackVO::new));
    }

    @ApiOperation("更新已读")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/updateToRead")
    public APIResponse updateToRead(Long id) {
        userFeedbackService.updateToRead(id, UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

    @ApiOperation("关闭反馈/建议")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键ID", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/updateToClose")
    public APIResponse updateToClose(Long id) {
        userFeedbackService.updateToClose(id, UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

}
