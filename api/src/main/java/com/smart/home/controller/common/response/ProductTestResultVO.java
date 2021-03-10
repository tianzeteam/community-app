package com.smart.home.controller.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/10
 **/
@Data
@ToString
public class ProductTestResultVO {

    @ApiModelProperty("产品封面图片")
    private String coverImage;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("评测结论")
    private String testResult;
    @ApiModelProperty("是否推荐：0不推荐1推荐")
    private Integer recommendFlag;

}
