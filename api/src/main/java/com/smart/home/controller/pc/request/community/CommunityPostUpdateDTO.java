package com.smart.home.controller.pc.request.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class CommunityPostUpdateDTO {

    @ApiModelProperty("帖子主键id")
    @NotNull(message = "帖子主键id不能为空")
    private Long id;

}
