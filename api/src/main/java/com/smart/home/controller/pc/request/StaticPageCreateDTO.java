package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class StaticPageCreateDTO {

    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String categoryName;
    @ApiModelProperty("内容")
    @NotBlank(message = "内容不能为空")
    private String contents;

}
