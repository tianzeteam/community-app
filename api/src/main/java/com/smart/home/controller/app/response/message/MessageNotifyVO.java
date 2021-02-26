package com.smart.home.controller.app.response.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/26
 * 系统通知消息
 **/
@Data
@ToString
public class MessageNotifyVO {

    @ApiModelProperty("消息主键id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("通知内容")
    private String messageContent;
    @ApiModelProperty("通知时间")
    private Date createdTime;

}
