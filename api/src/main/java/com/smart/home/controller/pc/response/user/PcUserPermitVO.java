package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class PcUserPermitVO {

    @ApiModelProperty("权限编码")
    private String permitCode;
    @ApiModelProperty("是否勾选：0否1是")
    private Integer checkFlag;

}
