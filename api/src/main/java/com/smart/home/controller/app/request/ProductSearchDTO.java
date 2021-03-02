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
public class ProductSearchDTO extends RequestPageBean {

    @ApiModelProperty("查询类型：0综合1新品排序2口碑排序")
    private Integer queryType;
    @ApiModelProperty("排序方式：desc，asc")
    private String sortType;
    @ApiModelProperty("筛选条件-一级类目主键ID")
    private Integer categoryOneId;
    @ApiModelProperty("筛选条件-二级类目主键ID")
    private Integer categoryTwoId;
    @ApiModelProperty("筛选条件-三级类目主键ID")
    private Integer categoryThreeId;
    @ApiModelProperty("筛选条件-平台")
    private String supportPlatform;

}
