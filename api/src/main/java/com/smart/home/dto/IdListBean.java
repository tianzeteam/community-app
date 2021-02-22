package com.smart.home.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/19
 **/
@Data
@ToString
public class IdListBean {

    @ApiModelProperty("主键ID数组")
    private List<Long> idList;

}
