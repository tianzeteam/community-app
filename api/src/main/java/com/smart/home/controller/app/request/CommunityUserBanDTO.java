package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/26
 **/
@ApiModel("封禁社区用户入参")
@Data
@ToString
public class CommunityUserBanDTO {

    @NotNull(message = "userId 不能为空")
    @ApiModelProperty("需要封禁的用户主键id")
    private Long userId;
    @ApiModelProperty("开始日期")
    private Date effectiveStartDate;
    @ApiModelProperty("结束日期")
    private Date effectiveEndDate;
    @ApiModelProperty("封禁理由-数组")
    private List<String> reasonList;

}
