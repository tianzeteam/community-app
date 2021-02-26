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
public class ArticleCreateDTO {

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    @Max(40)
    private String title;

    @ApiModelProperty("正文")
    @NotBlank(message = "正文不能为空")
    private String details;

    @ApiModelProperty("状态：0保存草稿1投稿")
    @NotNull(message = "状态不能为空")
    private Integer state;

    @ApiModelProperty("封面图片")
    private String coverImage;

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
