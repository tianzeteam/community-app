package com.smart.home.controller.app.response.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/4/11
 **/
@Data
@ToString
public class UnReadMessageVO {

    @ApiModelProperty("消息主键id")
    private Long id;
    @ApiModelProperty("消息内容")
    private String messageContent;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("创建时间")
    private Date createdTime;

}
