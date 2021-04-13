package com.smart.home.controller.pc.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class ReportHistoryVO {

    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("分类：0文章1帖子")
    private Integer category;
    @ApiModelProperty("分类名称")
    private String categoryName;
    @ApiModelProperty("关联举报涉及的文章或者帖子的主键id")
    private Long sourceId;
    @ApiModelProperty("举报内容")
    private String contents;
    @ApiModelProperty("举报人主键id")
    private Long createdBy;
    @ApiModelProperty("举报时间")
    private Date createdTime;

}