package com.smart.home.controller.pc.request.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class ProductParamSettingCreateDTO {

    @ApiModelProperty("参数名称")
    @Size(max = 32)
    @NotBlank(message = "参数名称不能为空")
    private String paramName;

    @ApiModelProperty("默认值")
    @Size(max = 32)
    private String defaultValue;

    @ApiModelProperty("参数简介")
    @Size(max = 64)
    private String remark;

    @ApiModelProperty("枚举值列表")
    private List<String> enumValueList;

    @ApiModelProperty("应用给所有的产品:0否1是")
    private Integer enableAll;

}
