package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.message.entity.MessageContent;
import com.smart.home.modules.message.service.MessageContentService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "message消息接口")
@RestController
@RequestMapping("/api/pc/messageContent")
public class MessageContentController {

    @Autowired
    private MessageContentService messageContentService;

    @ApiOperation("创建消息")
    @PostMapping("/create")
    public APIResponse create(MessageContent messageContent) {
        messageContent.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(messageContentService.create(messageContent));
    }

    @ApiOperation("更新消息")
    @PostMapping("/update")
    public APIResponse update(MessageContent messageContent) {
        messageContent.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(messageContentService.update(messageContent));
    }

    @ApiOperation("删除消息")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        messageContentService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询消息")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<MessageContent>> selectByPage(MessageContent messageContent, int pageNum, int pageSize) {
        List<MessageContent> list = messageContentService.selectByPage(messageContent, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询消息")
    @GetMapping("/selectById")
    public APIResponse<MessageContent> selectById(Long id) {
        return APIResponse.OK(messageContentService.findById(id));
    }

}