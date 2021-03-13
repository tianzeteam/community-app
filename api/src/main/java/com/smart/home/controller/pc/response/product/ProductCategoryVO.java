package com.smart.home.controller.pc.response.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ProductCategoryVO {

    @ApiModelProperty("上一级主键id，0是顶级")
    private Integer pid;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("名称")
    private String title;
    @ApiModelProperty("排序，越大越靠前")
    private Integer sort;
    @ApiModelProperty("关联参数库数组")
    private List<ProductParamSettingSelectVO> paramList;
    @ApiModelProperty("父类目对象，没有返回null")
    private ProductCategoryVO parent;

}
