package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@ToString
public class UserFeedbackVO implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("用户主键id")
    private Long userId;
    @ApiModelProperty("反馈内容")
    private String contents;
    @ApiModelProperty("截图内容")
    private List<String> imageList;
    private String images;
    @ApiModelProperty("回复内容")
    private String replyContent;
    @ApiModelProperty("状态：0待回复1已回复2已关闭")
    private Integer state;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("更新人主键id")
    private Long updatedBy;
    @ApiModelProperty("更新时间")
    private Date updatedTime;

}