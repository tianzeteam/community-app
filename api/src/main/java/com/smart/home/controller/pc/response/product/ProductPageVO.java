package com.smart.home.controller.pc.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductPageVO {

    @ApiModelProperty("产品主键id")
    private Integer id;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品标签")
    private String tag;
    @ApiModelProperty("一级分类名称")
    private String categoryOneName;
    @ApiModelProperty("一级分类名称")
    private String categoryTwoName;
    @ApiModelProperty("二级分类名称")
    private String categoryThreeName;
    @ApiModelProperty("状态：0正常1隐藏")
    private Integer onlineFlag;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("排序，越大越靠前")
    private Integer sort;

}
