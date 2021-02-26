package com.smart.home.controller.pc.request.product;

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
public class ProductCategoryCreateDTO {

    @ApiModelProperty("上一级主键id，如果是顶级则传0")
    @NotNull(message = "上一级主键id不能为空")
    private Integer pid;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String title;
    @ApiModelProperty("排序，越大越靠前")
    @NotNull(message = "排序不能为空")
    private Integer sort;

}
