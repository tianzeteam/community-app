package com.smart.home.controller.pc;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.FeedbackStatusEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.user.UserFeedbackReplyDTO;
import com.smart.home.controller.pc.response.user.UserFeedbackVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.user.entity.UserFeedback;
import com.smart.home.modules.user.service.UserFeedbackService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation("回复用户的反馈")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/reply")
    public APIResponse reply(@Valid @RequestBody UserFeedbackReplyDTO userFeedbackReplyDTO, BindingResult bindingResult) {
        UserFeedback userFeedback = userFeedbackService.findById(userFeedbackReplyDTO.getId());
        userFeedback.setReplyContent(userFeedbackReplyDTO.getReplyContent());
        userFeedback.setState(FeedbackStatusEnum.REPLIED.getStatus());
        userFeedback.setUpdatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(userFeedbackService.update(userFeedback));
    }

    @ApiOperation("关闭用户反馈")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/close")
    public APIResponse close(@RequestBody IdListBean idListBean) {
        userFeedbackService.close(idListBean.getIdList(), UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

    @ApiOperation("删除用户反馈")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        userFeedbackService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询用户反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<UserFeedbackVO>> selectByPage(UserFeedback userFeedback, int pageNum, int pageSize) {
        List<UserFeedback> list = userFeedbackService.selectByPage(userFeedback, pageNum, pageSize);
        List<UserFeedbackVO> resultList = BeanCopyUtils.convertListTo(list, UserFeedbackVO::new,(s,t)->{
            if (StringUtils.isNotBlank(s.getImages()) && !"[]".equals(s.getImages())) {
                t.setImageList(JSON.parseArray(s.getImages(), String.class));
            }
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("按主键ID查询用户反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectById")
    public APIResponse<UserFeedbackVO> selectById(@RequestParam(required = true) Long id) {
        UserFeedback userFeedback = userFeedbackService.findById(id);
        UserFeedbackVO userFeedbackVO = new UserFeedbackVO();
        BeanUtils.copyProperties(userFeedback, userFeedbackVO);
        if (StringUtils.isNotBlank(userFeedback.getImages()) && !"[]".equals(userFeedback.getImages())) {
            userFeedbackVO.setImageList(JSON.parseArray(userFeedback.getImages(), String.class));
        }
        return APIResponse.OK(userFeedbackVO);
    }

}