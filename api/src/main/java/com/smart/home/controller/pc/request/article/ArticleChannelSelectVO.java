package com.smart.home.controller.pc.request.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class ArticleChannelSelectVO {

    @ApiModelProperty("频道主键id")
    private Integer id;
    @ApiModelProperty("频道名称")
    private String title;

}
