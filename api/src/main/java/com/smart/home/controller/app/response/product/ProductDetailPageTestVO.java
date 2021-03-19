package com.smart.home.controller.app.response.product;

import com.smart.home.dto.UserBaseInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductDetailPageTestVO extends UserBaseInfo {

    @ApiModelProperty("是否推荐：0否1是")
    private Integer recommendFlag;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String testResult;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("图片数组")
    private List<String> imageList;

}
