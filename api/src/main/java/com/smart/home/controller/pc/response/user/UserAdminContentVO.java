package com.smart.home.controller.pc.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/2
 **/
@Data
@ToString
public class UserAdminContentVO {

    @ApiModelProperty("内容id")
    private Long id;
    @ApiModelProperty("作者")
    private String nickName;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("文本内容，截图前N个多少字符")
    private String contents;
    @ApiModelProperty("图像地址列表")
    private List<String> imageList;
    @ApiModelProperty("创建时间")
    private Date createdTime;

}
