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
@ApiModel("产品库-产品评测列表返回对象")
@Data
@ToString
public class ProductPageTestVO {

    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("是否推荐：0否1是")
    private Integer recommendFlag;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String testResult;
    @ApiModelProperty("晒图")
    private String bannerImages;
    @ApiModelProperty("发布时间")
    private Date createdTime;

}
