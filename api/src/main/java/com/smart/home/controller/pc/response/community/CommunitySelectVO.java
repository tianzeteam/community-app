package com.smart.home.controller.pc.response.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class CommunitySelectVO {

    @ApiModelProperty("社区主键id")
    private Integer id;
    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("图片")
    private String coverImage;
    @ApiModelProperty("简介")
    private String remark;

    @ApiModelProperty("粉丝数量")
    private Integer followerCount;

    @ApiModelProperty("是否加入过：0未，1已加入")
    private Integer joinFlag = 0;

}
