package com.smart.home.controller.pc.request.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/21
 **/
@Data
@ToString
public class SysDictUpdateDTO extends SysDictDTO {

    @ApiModelProperty("主键id")
    private Integer id;

}
