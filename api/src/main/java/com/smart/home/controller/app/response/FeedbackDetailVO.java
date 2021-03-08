package com.smart.home.controller.app.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Data
@ToString
public class FeedbackDetailVO extends FeedbackVO {

    @ApiModelProperty("图片数组")
    private List<String> imageList;

}
