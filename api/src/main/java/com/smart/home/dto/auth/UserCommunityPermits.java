package com.smart.home.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Data
@ToString
public class UserCommunityPermits {

    @ApiModelProperty("是否禁言：0否1是")
    private Integer speakFlag;
    @ApiModelProperty("是否封禁：0否1是")
    private Integer blackFlag;
    @ApiModelProperty("是否是版主：0否1是")
    private Integer adminFlag;
    @ApiModelProperty("封禁开始日期")
    private Date effectiveStartDate;
    @ApiModelProperty("封禁结束日期")
    private Date effectiveEndDate;

}
