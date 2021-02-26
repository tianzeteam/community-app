package com.smart.home.controller.app.response.message;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class MessageVO {

    @ApiModelProperty("消息大类：0回复我的1赞2系统通知")
    private Integer category;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("副标题")
    private String subTitle;

}
