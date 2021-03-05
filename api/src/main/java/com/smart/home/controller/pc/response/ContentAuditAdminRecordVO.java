package com.smart.home.controller.pc.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/5
 **/
@Data
@ToString
public class ContentAuditAdminRecordVO {

    @ApiModelProperty("内容id")
    private Long id;
    @ApiModelProperty("作者名")
    private String nickName;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容类型：0评论1评价2帖子3回帖")
    private Integer contentType;
    @ApiModelProperty("文本内容")
    private String contents;
    @ApiModelProperty("图像内容")
    private List<String> imageList;
    @ApiModelProperty("敏感词数量")
    private Integer hitSensitiveCount;
    @ApiModelProperty("投诉数量")
    private Integer reportCount;
    @ApiModelProperty("投诉分布")
    private String reportCountRate;
    @ApiModelProperty("机审文本:0待审核1审核通过2图片异常3文本异常4文本图片都异常")
    private Integer autoAuditFlag;
    @ApiModelProperty("机审核文本异常原因")
    private String textExceptionReason;
    @ApiModelProperty("机审核图片异常原因")
    private String imageExceptionReason;
    @ApiModelProperty("发布时间")
    private Date createdTime;

}
