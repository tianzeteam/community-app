package com.smart.home.controller.app.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Data
@ToString
public class FeedbackVO {

    @ApiModelProperty("主键ID")
    private Long id;
    @ApiModelProperty("反馈内容")
    private String contents;
    @ApiModelProperty("反馈日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdTime;
    @ApiModelProperty("状态：0等待回复1已有回复2已关闭")
    private Integer state;
    @ApiModelProperty("回复内容，按主键ID查询才有返回")
    private String replyContent;

}
