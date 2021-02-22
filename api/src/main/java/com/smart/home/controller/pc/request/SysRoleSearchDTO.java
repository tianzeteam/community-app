package com.smart.home.controller.pc.request;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/19
 **/
@Data
@ToString
public class SysRoleSearchDTO extends RequestPageBean {

    @ApiModelProperty(value = "角色名称", required = false)
    private String name;
    @ApiModelProperty(value = "角色备注", required = false)
    private String remark;

}
