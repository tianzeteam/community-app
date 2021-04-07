package com.smart.home.controller.pc.request.system;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/4/8
 **/
@Data
@ToString
public class SysActionLogSearchDTO extends RequestPageBean {

    @ApiModelProperty("操作人主键id")
    private Long userId;
    @ApiModelProperty("开始时间")
    private Date startDate;
    @ApiModelProperty("结束时间")
    private Date endDate;

}
