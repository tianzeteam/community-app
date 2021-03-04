package com.smart.home.cloud.qcloud.auditor;

import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilLabelEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilTypeEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/4
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ContentAuditorResult {

    /**
     * 必定有值
     */
    private ContentAuditorEvilEnum contentAuditorEvilEnum;
    /**
     * 必定有值
     */
    private ContentAuditorEvilTypeEnum contentAuditorEvilTypeEnum;
    private ContentAuditorEvilLabelEnum contentAuditorEvilLabelEnum;
    private ContentAuditorSuggestionEnum contentAuditorSuggestionEnum;

    private List<String> keywordsList;

}
