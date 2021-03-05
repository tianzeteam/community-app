package com.smart.home.controller.pc.request.system;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Data
@ToString
public class SysMenuSearchDTO extends RequestPageBean {

    @ApiModelProperty(value = "菜单名称", required = false)
    private String name;
    @ApiModelProperty(value = "菜单简介", required = false)
    private String remark;
    @ApiModelProperty(value = "权限标识", required = false)
    private String permit;
    @ApiModelProperty(value = "菜单类型0顶级菜单1子级菜单3不是菜单", required = false)
    private Integer type;
    @ApiModelProperty(value = "父级菜单主键ID", required = false)
    private Integer pid;
    @ApiModelProperty(value = "状态0正常1暂停", required = false)
    private Integer state;

}
