package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/5
 **/
@Data
@ToString
public class ContentAdminAuditApproveDTO {

    @ApiModelProperty("内容id")
    private Long id;
    @ApiModelProperty("内容类型：0评论1产品评价2帖子3回帖")
    private Integer contentType;

}
