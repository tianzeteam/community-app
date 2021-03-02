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
public class ProductSearchResultVO {

    @ApiModelProperty("产品主键id")
    private Integer id;
    @ApiModelProperty("封面图片")
    private String coverImage;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品简介")
    private String remark;
    @ApiModelProperty("产品标签")
    private String tag;
    @ApiModelProperty("评论数量")
    private Integer commentCount;
    @ApiModelProperty("综合评分")
    private BigDecimal averageScore;

}
