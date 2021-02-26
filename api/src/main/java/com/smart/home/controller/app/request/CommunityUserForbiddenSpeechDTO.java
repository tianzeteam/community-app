package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/26
 **/
@ApiModel("禁言社区用户入参")
@Data
@ToString
public class CommunityUserForbiddenSpeechDTO {

    @NotNull(message = "userId 不能为空")
    @ApiModelProperty("需要封禁的用户主键id")
    private Long userId;
    @ApiModelProperty("禁言理由-数组")
    private List<String> reasonList;

}
