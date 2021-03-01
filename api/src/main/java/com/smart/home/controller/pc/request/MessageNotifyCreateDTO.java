package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class MessageNotifyCreateDTO {

    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("消息内容")
    private String messageContent;

}
