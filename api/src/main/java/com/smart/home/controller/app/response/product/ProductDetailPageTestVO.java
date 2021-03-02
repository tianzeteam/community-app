package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductDetailPageTestVO {

    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("是否推荐：0否1是")
    private Integer recommendFlag;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String testResult;

}
