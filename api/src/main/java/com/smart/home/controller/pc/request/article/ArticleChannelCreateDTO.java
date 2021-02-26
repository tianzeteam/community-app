package com.smart.home.controller.pc.request.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleChannelCreateDTO {

    @NotBlank(message = "频道名称不能为空")
    @ApiModelProperty("频道名称")
    private String title;
    @NotNull(message = "排序不能为空")
    @ApiModelProperty("排序，越大越靠前")
    private Integer sort;
    @NotNull(message = "是否展示在首页不能为空")
    @ApiModelProperty("是否展示在首页：0否1是")
    private Integer indexFlag;

}
