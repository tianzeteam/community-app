package com.smart.home.controller.app.response.community;

import com.smart.home.dto.UserBaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 * @date 2021/3/9
 **/
@Data
@ToString
public class MyCollectPostVO {

    @ApiModelProperty("帖子主键id")
    private Long id;
    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("创建时间")
    private Date createdTime;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("回复数量")
    private Integer replyCount;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("正文")
    private String contents;
    @ApiModelProperty("图片数组")
    private List<String> imageList;
    @ApiModelProperty("我有没有赞过：0否1是")
    private Integer likeFlag;

}
