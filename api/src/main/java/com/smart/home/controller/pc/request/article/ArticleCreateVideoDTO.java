package com.smart.home.controller.pc.request.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleCreateVideoDTO {

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    @Max(40)
    private String title;

    @ApiModelProperty("视频地址")
    @NotBlank(message = "视频地址不能为空")
    private String remark;

    @ApiModelProperty("视频简介")
    @Max(1000)
    private String details;

    @ApiModelProperty("状态：0保存草稿1投稿")
    @NotNull(message = "状态不能为空")
    private Integer state;

    @ApiModelProperty("下拉选择的频道主键id")
    @NotNull(message = "请选择频道")
    private Long channelId;

    @ApiModelProperty("文章类型：0原创1转载")
    private Integer articleType;

    @ApiModelProperty("转载来源")
    private String originalUrl;

    @ApiModelProperty("转载授权")
    private String originalAuth;
}
