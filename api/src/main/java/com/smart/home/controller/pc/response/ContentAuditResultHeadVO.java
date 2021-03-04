package com.smart.home.controller.pc.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/5
 **/
@Data
@ToString
public class ContentAuditResultHeadVO {

    @ApiModelProperty("待审数量")
    private Long waitAuditCount;
    @ApiModelProperty("机审文本异常")
    private Long autoTextExceptionCount;
    @ApiModelProperty("机审图片异常")
    private Long autoImageExceptionCount;
    @ApiModelProperty("有投诉")
    private Long hasReportCount;
    @ApiModelProperty("命中敏感词")
    private Long hitSensitiveCount;
    @ApiModelProperty("全部正常")
    private Long totalNormalCount;

}
