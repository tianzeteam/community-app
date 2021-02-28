package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class ReportCreateDTO {

    @ApiModelProperty("举报内容的主键id")
    @NotNull(message = "举报内容的主键id不能为空")
    private Long id;
    @ApiModelProperty("描述")
    private String contents;
    @ApiModelProperty("截图为证-如果有的话")
    private String images;

}
