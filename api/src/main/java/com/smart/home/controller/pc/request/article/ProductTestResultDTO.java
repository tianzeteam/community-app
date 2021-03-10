package com.smart.home.controller.pc.request.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/10
 **/
@Data
@ToString
public class ProductTestResultDTO {

    @ApiModelProperty("评测结论")
    private String testResult;
    @ApiModelProperty("推荐标记：0不推荐1推荐")
    private Integer recommendFlag;

}
