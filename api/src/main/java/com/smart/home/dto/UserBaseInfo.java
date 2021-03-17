package com.smart.home.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class UserBaseInfo {

    @ApiModelProperty("用户主键id")
    private Long userId;
    @ApiModelProperty("用户头像地址")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;

}
