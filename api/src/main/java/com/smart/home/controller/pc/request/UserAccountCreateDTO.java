package com.smart.home.controller.pc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/20
 **/
@Data
@ToString
public class UserAccountCreateDTO {

    @ApiModelProperty(value = "头像地址", required = false)
    private String headUrl;
    @ApiModelProperty(value = "用户名，非必填，没有自动取mobile的值", required = false)
    private String username;
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "姓名", required = false)
    private String name;
    @ApiModelProperty(value = "昵称", required = false)
    private String nickName;

    @ApiModelProperty("同时授予角色的主键ID列表")
    private List<Integer> roleIdList;

}
