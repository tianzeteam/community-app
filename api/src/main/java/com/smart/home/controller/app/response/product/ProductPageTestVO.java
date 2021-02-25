package com.smart.home.controller.app.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/25
 **/
@ApiModel("产品页面的评测列表")
@Data
@ToString
public class ProductPageTestVO {

    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("投稿标题")
    private String title;
    @ApiModelProperty("详细内容")
    private String details;
    @ApiModelProperty("是否推荐：0否1是")
    private Integer recommendFlag;

}
