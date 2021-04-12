package com.smart.home.controller.app.response.product;

import com.smart.home.modules.product.entity.ProductParamValue;
import com.smart.home.modules.product.entity.ProductShopMapping;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductDetailVO {

    @ApiModelProperty("产品主键id")
    private Integer id;
    @ApiModelProperty("轮播图数组")
    private List<String> bannerImageList;
    @ApiModelProperty("产品名称")
    private String productName;
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
    @ApiModelProperty("我有没有收藏过了：0没有1有")
    private Integer collectFlag;
    @ApiModelProperty("产品参数数组")
    private List<ProductParamValue> productParamValueList;
    @ApiModelProperty("商城列表数组")
    private List<ProductShopMapping> productShopMappingList;
    @ApiModelProperty("标签数组")
    private List<String> tagList;
    @ApiModelProperty("分享页面")
    private String href;
    @ApiModelProperty("我的评分")
    private BigDecimal myScore;

}
