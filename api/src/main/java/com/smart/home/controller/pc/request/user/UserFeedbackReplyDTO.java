package com.smart.home.controller.pc.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/4/13
 **/
@Data
@ToString
public class UserFeedbackReplyDTO {

    @ApiModelProperty("反馈记录主键id")
    @NotNull(message = "反馈记录主键id不能为空")
    private Long id;
    @ApiModelProperty("回复内容")
    @NotEmpty(message = "回复内容不能为空")
    private String replyContent;

}
