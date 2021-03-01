package com.smart.home.controller.pc.request.product;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductPageSearchDTO extends RequestPageBean {

    @ApiModelProperty("产品主键id")
    private Integer id;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("标签数组")
    private List<String> tagList;
    @ApiModelProperty("一级分类id")
    private Integer categoryOneId;
    @ApiModelProperty("二级分类id")
    private Integer categoryTwoId;
    @ApiModelProperty("三级分类id")
    private Integer categoryThreeId;
    @ApiModelProperty("产品状态：0正常1隐藏")
    private Integer onlineFlag;

}
