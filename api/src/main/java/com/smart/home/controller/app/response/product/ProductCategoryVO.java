package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductCategoryVO {

    @ApiModelProperty("主键ID")
    private Integer id;
    @ApiModelProperty("父主键ID")
    private Integer pid;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("三级分类列表")
    private List<ProductCategoryVO> subCategories;

}
