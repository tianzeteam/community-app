package com.smart.home.controller.pc.request;

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
public class UserAccountSearchDTO extends RequestPageBean {

    @ApiModelProperty(value = "用户名", required = false)
    private String username;
    @ApiModelProperty(value = "手机号", required = false)
    private String mobile;
    @ApiModelProperty(value = "姓名", required = false)
    private String name;
    @ApiModelProperty(value = "昵称", required = false)
    private String nickName;

}
