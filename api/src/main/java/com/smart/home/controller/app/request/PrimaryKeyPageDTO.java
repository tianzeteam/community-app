package com.smart.home.controller.app.request;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class PrimaryKeyPageDTO extends RequestPageBean {

    @ApiModelProperty("主键id")
    private Long id;

}
