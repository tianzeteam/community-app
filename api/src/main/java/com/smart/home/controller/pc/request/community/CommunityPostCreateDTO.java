package com.smart.home.controller.pc.request.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class CommunityPostCreateDTO {

    @ApiModelProperty("标题")
    @Size(max = 40)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("正文")
    @NotBlank(message = "正文不能为空")
    private String contents;

    @ApiModelProperty("状态：0草稿1发布")
    @NotNull(message = "状态不能为空")
    private Integer state;

}
