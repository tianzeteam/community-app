package com.smart.home.controller.pc.response.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class CommunityCategorySelectVO {

    @ApiModelProperty("社区类目主键id")
    private Integer id;
    @ApiModelProperty("名称")
    private String title;

}
