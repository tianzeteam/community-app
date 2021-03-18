package com.smart.home.controller.pc.request.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductParamValueDTO {

    @ApiModelProperty("主键id,之前查询带下来的, 如是手动新增的话传null")
    private Integer id;
    @ApiModelProperty("名称,之前查询带下来的")
    private String paramName;
    @ApiModelProperty("默认值,之前查询带下来的")
    private String defaultValue;
    @ApiModelProperty("简介,之前查询带下来的")
    private String remark;
    @ApiModelProperty("枚举值,之前查询带下来的")
    private String enumValues;
    @ApiModelProperty("参数值，需要填写的")
    @NotBlank(message = "参数值不能为空")
    private String paramValue;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("是否是自定义：3是，其他值或null值为否")
    private Integer enableAll;

}
