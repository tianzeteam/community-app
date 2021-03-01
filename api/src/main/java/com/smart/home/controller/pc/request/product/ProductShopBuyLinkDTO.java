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
public class ProductShopBuyLinkDTO {

    @ApiModelProperty("主键id,查询的vo带下来的")
    private Integer id;
    @ApiModelProperty("图标,查询的vo带下来的")
    private String coverImage;
    @ApiModelProperty("名称,查询的vo带下来的")
    private String shopName;
    @ApiModelProperty("标题，需要填写的")
    private String title;
    @ApiModelProperty("购买链接，需要填写的")
    @NotBlank(message = "购买链接不能为空")
    private String url;

}
