package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Data
@ToString
public class UserDataAdminVO {

    @ApiModelProperty("命中敏感词")
    private Integer hitSensitiveCount;
    @ApiModelProperty("被投诉次数")
    private Integer byReportCount;
    @ApiModelProperty("被投诉原因愤怒吧")
    private String reportReasonRate;
    @ApiModelProperty("机审文本异常")
    private Integer textExceptionCount;
    @ApiModelProperty("机审图片异常")
    private Integer imageExceptionCount;
    @ApiModelProperty("人工认定异常")
    private Integer userAuditExceptionCount;
    @ApiModelProperty("被封禁次数")
    private Integer blackCount;

}
