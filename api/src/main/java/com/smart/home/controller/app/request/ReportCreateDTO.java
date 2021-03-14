package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/28
 **/
@Data
@ToString
public class ReportCreateDTO {

    @ApiModelProperty("举报内容的主键id")
    @NotNull(message = "举报内容的主键id不能为空")
    private Long id;
    @ApiModelProperty("描述")
    private String contents;
    @ApiModelProperty("截图为证-如果有的话")
    private List<String> imageList;
    @ApiModelProperty("作者用户主键id")
    @NotNull(message = "作者用户主键id不能为空")
    private Long authorUserId;
    @ApiModelProperty("举报原因：这个应该是枚举，从report.reason字典取")
    @NotBlank(message = "举报原因不能为空")
    private String reason;
}
