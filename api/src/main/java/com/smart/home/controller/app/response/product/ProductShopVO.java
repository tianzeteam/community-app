package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductShopVO {

    @ApiModelProperty("商城图标")
    private String shopIcon;
    @ApiModelProperty("商城名称")
    private String shopName;
    @ApiModelProperty("超链接")
    private String url;

}
