package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/5
 **/
@Data
@ToString
public class ContentAdminAuditSearchDTO {

    @ApiModelProperty("内容类型：0评论1产品评价2帖子3回帖")
    private List<Integer> contentType;
    @ApiModelProperty("用户主键id")
    private Long userId;
    @ApiModelProperty("内容异常：0全部内容1任意一个模块异常2自定义满足以下任意条件")
    private Integer contentException;
    @ApiModelProperty("敏感词匹配N个以上")
    private Integer hitSensitiveCount;
    @ApiModelProperty("用户投诉N个以上")
    private Integer reportCount;
    @ApiModelProperty("机审文本异常：0未勾选1勾选")
    private Integer textExceptionFlag;
    @ApiModelProperty("机审图片异常：0未勾选1勾选")
    private Integer imageExceptionFlag;
    @ApiModelProperty("排序字段：created_time, like_count, reply_count")
    private String sortField;
    @ApiModelProperty("加载条目数量")
    private Integer pageSize;

}
