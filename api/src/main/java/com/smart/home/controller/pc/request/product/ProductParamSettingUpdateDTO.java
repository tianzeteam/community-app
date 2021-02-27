package com.smart.home.controller.pc.request.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class ProductParamSettingUpdateDTO extends ProductParamSettingCreateDTO {

    @ApiModelProperty("参数库主键id")
    @NotNull(message = "参数库主键id不能为空")
    private Integer id;

}
