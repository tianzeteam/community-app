package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.message.entity.MessageReadHistory;
import com.smart.home.modules.message.service.MessageReadHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "message消息已读历史接口")
@RestController
@RequestMapping("/api/pc/messageReadHistory")
public class MessageReadHistoryController {

    @Autowired
    private MessageReadHistoryService messageReadHistoryService;

    @ApiOperation("更新消息已读历史")
    @PostMapping("/update")
    public APIResponse update(MessageReadHistory messageReadHistory) {
        return APIResponse.OK(messageReadHistoryService.update(messageReadHistory));
    }

    @ApiOperation("删除消息已读历史")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        messageReadHistoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询消息已读历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<MessageReadHistory>> selectByPage(MessageReadHistory messageReadHistory, int pageNum, int pageSize) {
        List<MessageReadHistory> list = messageReadHistoryService.selectByPage(messageReadHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询消息已读历史")
    @GetMapping("/selectById")
    public APIResponse<MessageReadHistory> selectById(Long id) {
        return APIResponse.OK(messageReadHistoryService.findById(id));
    }

}