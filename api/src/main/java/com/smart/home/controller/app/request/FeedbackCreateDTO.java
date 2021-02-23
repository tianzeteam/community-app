package com.smart.home.controller.app.request;

import com.smart.home.common.bean.ImageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Data
@ToString
public class FeedbackCreateDTO {

    @NotBlank(message = "反馈内容不能为空")
    @ApiModelProperty("反馈内容")
    private String contents;
    @ApiModelProperty("图片地址数组")
    private List<String> images;

}
