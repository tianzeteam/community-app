package com.smart.home.controller.app.response.community;

import com.smart.home.dto.UserBaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/3
 **/
@Data
@ToString
public class MyRootProfilePostVO extends UserBaseInfo {

    @ApiModelProperty("主键id")
    private Long id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String contents;
    @ApiModelProperty("图片数组")
    private List<String> imageList;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("回复数量")
    private Integer replyCount;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("是否评论过:0否1是")
    private Integer replyFlag;
    @ApiModelProperty("是否点赞过：0否1是")
    private Integer likeFlag;

}
