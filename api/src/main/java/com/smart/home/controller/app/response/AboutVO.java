package com.smart.home.controller.app.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AboutVO {

    @ApiModelProperty("分类:0关于APP1更新日志2用户协议3隐私政策4证照中心5权限说明6第三方服务")
    private Integer type;
    @ApiModelProperty("标题")
    private String name;
    @ApiModelProperty("详细")
    private String details;

}
