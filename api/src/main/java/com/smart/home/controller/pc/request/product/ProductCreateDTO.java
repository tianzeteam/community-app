package com.smart.home.controller.pc.request.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/1
 **/
@Data
@ToString
public class ProductCreateDTO {

    @ApiModelProperty("产品名称")
    @NotBlank(message = "产品名称不能为空")
    @Size(max = 20, message = "产品名称长度不能超过20")
    private String productName;

    @ApiModelProperty("产品简介")
    @Size(max = 200, message = "产品简介长度不能超过200")
    private String remark;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("产品图库列表")
    private List<String> imageList;

    @ApiModelProperty("标签列表")
    private List<String> tagList;

    @ApiModelProperty("购买链接对象列表")
    private List<ProductShopBuyLinkDTO> buyLinkDTOList;

    @ApiModelProperty("参数列表")
    private List<ProductParamValueDTO> paramValueDTOList;

    //////////////////////////////////////////////////////
    @ApiModelProperty("一级类目对象")
    private ProductCategoryDTO categoryOneDTO;
    @ApiModelProperty("二级类目对象")
    private ProductCategoryDTO categoryTwoDTO;
    @ApiModelProperty("三级类目对象")
    private ProductCategoryDTO categoryThreeDTO;
    @ApiModelProperty("品牌对象")
    private ProductBrandDTO productBrandDTO;
    @ApiModelProperty("上线日期")
    private Date onlineDate;
    @ApiModelProperty("规格型号")
    private String specification;
    @ApiModelProperty("支持的平台")
    private String supportPlatform;

}
