package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.controller.pc.request.MessageNotifyCreateDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.MessageTypeEnum;
import com.smart.home.modules.message.entity.MessageContent;
import com.smart.home.modules.message.service.MessageContentService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "系统通知")
@RestController
@RequestMapping("/api/pc/messageContent")
public class MessageContentController {

    @Autowired
    private MessageContentService messageContentService;

    @ApiOperation("创建系统通知消息")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/create")
    public APIResponse create(@Valid MessageNotifyCreateDTO messageNotifyCreateDTO, BindingResult bindingResult) {
        MessageContent messageContent = new MessageContent();
        BeanUtils.copyProperties(messageNotifyCreateDTO, messageContent);
        messageContent.setCreatedBy(UserUtils.getLoginUserId());
        messageContent.setSenderId(UserUtils.getLoginUserId());
        return APIResponse.OK(messageContentService.createNotifyMessage(messageContent));
    }

    @ApiOperation("删除系统通知消息")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        messageContentService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询系统通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "", required = true),
            @ApiImplicitParam(name = "pageSize", value = "", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<MessageContent>> selectByPage(int pageNum, int pageSize) {
        MessageContent messageContent = new MessageContent();
        messageContent.setMessageType(MessageTypeEnum.NOTIFY.getType());
        List<MessageContent> list = messageContentService.selectByPage(messageContent, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询系统通知消息")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectById")
    public APIResponse<MessageContent> selectById(Long id) {
        return APIResponse.OK(messageContentService.findById(id));
    }

}