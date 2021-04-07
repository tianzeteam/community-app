package com.smart.home.controller.pc.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/4/8
 **/
@Data
@ToString
public class SysActionLogVO {

    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("日志名称")
    private String name;
    @ApiModelProperty("操作ip")
    private String ip;
    @ApiModelProperty("日志消息")
    private String message;
    @ApiModelProperty("操作人")
    private Long createdBy;
    @ApiModelProperty("操作时间")
    private Date createdTime;

}
