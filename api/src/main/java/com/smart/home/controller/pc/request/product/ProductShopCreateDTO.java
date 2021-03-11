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
public class ProductShopCreateDTO {

    @ApiModelProperty("图标")
    private String coverImage;
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String shopName;

}
