package com.smart.home.cloud.qcloud.auditor;

import com.smart.home.cloud.qcloud.QcloudProperties;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilLabelEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilTypeEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.cms.v20190321.CmsClient;
import com.tencentcloudapi.cms.v20190321.models.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;;import java.util.Arrays;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/4
 * 文本内容检测：https://cloud.tencent.com/document/product/1124/51862
 **/
@Log4j2
public class ContentAuditor {

    public static ContentAuditorResult auditorResult(String content) {
        try{
            Credential cred = new Credential(QcloudProperties.secretId, QcloudProperties.secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(QcloudProperties.auditorEndpoint);

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            CmsClient client = new CmsClient(cred, QcloudProperties.region, clientProfile);

            TextModerationRequest req = new TextModerationRequest();
            req.setContent(content);

            TextModerationResponse resp = client.TextModeration(req);

            long evilFlag = resp.getData().getEvilFlag();
            ContentAuditorEvilEnum contentAuditorEvilEnum = ContentAuditorEvilEnum.asOf((int)evilFlag);
            long evilType = resp.getData().getEvilType();
            ContentAuditorEvilTypeEnum contentAuditorEvilTypeEnum = ContentAuditorEvilTypeEnum.asOf((int)evilType);
            ContentAuditorEvilLabelEnum contentAuditorEvilLabelEnum = null;
            String label = resp.getData().getEvilLabel();
            if (StringUtils.isNotBlank(label)) {
                contentAuditorEvilLabelEnum = ContentAuditorEvilLabelEnum.asOf(label);
            }
            ContentAuditorSuggestionEnum contentAuditorSuggestionEnum = null;
            String suggestion = resp.getData().getSuggestion();
            if (StringUtils.isNotBlank(suggestion)) {
                contentAuditorSuggestionEnum = ContentAuditorSuggestionEnum.asOf(suggestion);
            }
            List<String> keywordsList = Arrays.asList(resp.getData().getKeywords());
            ContentAuditorResult contentAuditorResult = new ContentAuditorResult(contentAuditorEvilEnum,contentAuditorEvilTypeEnum,contentAuditorEvilLabelEnum,contentAuditorSuggestionEnum,keywordsList);
            return contentAuditorResult;
        } catch (TencentCloudSDKException e) {
            log.error(e);
        }
        return null;
    }

}
