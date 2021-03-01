package com.smart.home.controller.pc.request.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductShopUpdateDTO {

    @ApiModelProperty("主键id")
    @NotNull(message = "主键id不能为空")
    private Integer id;

}
