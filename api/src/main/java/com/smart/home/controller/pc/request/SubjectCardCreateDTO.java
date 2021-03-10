package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class SubjectCardCreateDTO {

    @ApiModelProperty("封面图片")
    @NotBlank(message = "封面图片不能为空")
    private String coverImage;
    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;
    @ApiModelProperty("插入位置")
    @NotNull(message = "插入位置不能为空")
    private Integer locations;
    @ApiModelProperty("插入链接")
    private String url;

}
