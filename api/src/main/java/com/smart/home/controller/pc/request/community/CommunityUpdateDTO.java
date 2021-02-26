package com.smart.home.controller.pc.request.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class CommunityUpdateDTO extends CommunityCreateDTO {

    @ApiModelProperty("社区主键id")
    @NotNull(message = "社区主键id不能为空")
    private Integer id;

}
