package com.smart.home.controller.pc.request.product;

import com.smart.home.controller.pc.response.product.ProductParamSettingSelectVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductCategoryDTO {

    @ApiModelProperty("主键id,之前查询带出来的")
    private Integer id;
    @ApiModelProperty("名称,之前查询带出来的")
    private String title;
    @ApiModelProperty("分类下的参数id数组")
    private List<ProductParamSettingSelectVO> paramList;

}
