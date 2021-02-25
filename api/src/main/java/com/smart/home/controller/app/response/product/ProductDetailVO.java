package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductDetailVO {

    @ApiModelProperty("轮播图")
    private String bannerImages;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("标签")
    private String tag;
    @ApiModelProperty("厂商")
    private String brandName;
    @ApiModelProperty("简介")
    private String remark;
    @ApiModelProperty("综合评分")
    private BigDecimal averageScore;
    @ApiModelProperty("评价数量")
    private Integer commentCount;
    @ApiModelProperty("评测数量")
    private Integer testCount;


}
