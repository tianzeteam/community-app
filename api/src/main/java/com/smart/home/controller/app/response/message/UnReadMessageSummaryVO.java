package com.smart.home.controller.app.response.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/4/21
 **/
@Data
@ToString
public class UnReadMessageSummaryVO {

    @ApiModelProperty("发送人主键id")
    private Long senderId;
    @ApiModelProperty("接收人主键id")
    private Long receiverId;
    @ApiModelProperty("总的未读消息")
    private Long totalCount;
    @ApiModelProperty("发送人昵称")
    private String nickName;
    @ApiModelProperty("发送人头像")
    private String headUrl;
    @ApiModelProperty("最近一条消息内容")
    private String latestMessage;

}
