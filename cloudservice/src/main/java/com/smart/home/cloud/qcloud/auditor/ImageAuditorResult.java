package com.smart.home.cloud.qcloud.auditor;

import com.smart.home.cloud.qcloud.enums.ImageAuditorLabelEnum;
import com.smart.home.cloud.qcloud.enums.ImageAuditorSuggestionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/3/3
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ImageAuditorResult {

    private ImageAuditorSuggestionEnum imageAuditorSuggestionEnum;
    private ImageAuditorLabelEnum imageAuditorLabelEnum;

}
