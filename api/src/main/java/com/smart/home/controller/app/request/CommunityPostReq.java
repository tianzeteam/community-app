package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class CommunityPostReq {

    @ApiModelProperty("帖子id")
    private Long id;

    @ApiModelProperty("数组list，可作为多原因勾选，或图片多选")
    private List<String> list;

    @ApiModelProperty("文本")
    private String contents;

    @ApiModelProperty("社区主键id")
    private Long community;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("是否置顶，0否1是")
    private Integer topFlag;

    @ApiModelProperty("是否加精")
    private Integer boutiqueFlag;

    @ApiModelProperty("是否能评论")
    private Integer commentFlag;

    @ApiModelProperty("审核状态，0待审核，1审核通过，2审核失败")
    private Integer auditStatus;
}
