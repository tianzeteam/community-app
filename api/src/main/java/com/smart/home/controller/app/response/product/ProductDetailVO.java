package com.smart.home.controller.app.response.product;

import com.smart.home.common.bean.KeyValueBean;
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
    @ApiModelProperty("我有没有收藏过了：0没有1有")
    private Integer collectFlag;
    @ApiModelProperty("产品参数")
    private String params;
    @ApiModelProperty("商城列表")
    private String shops;


}
