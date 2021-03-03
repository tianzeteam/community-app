package com.smart.home.cloud.qcloud.auditor;

import com.smart.home.cloud.qcloud.enums.ImageAuditorLabelEnum;
import com.smart.home.cloud.qcloud.enums.ImageAuditorSuggestionEnum;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ims.v20200713.ImsClient;
import com.tencentcloudapi.ims.v20200713.models.ImageModerationRequest;
import com.tencentcloudapi.ims.v20200713.models.ImageModerationResponse;
import lombok.extern.log4j.Log4j2;

/**
 * @author jason
 * @date 2021/3/3
 * https://cloud.tencent.com/document/api/1125/53273
 **/
@Log4j2
public class ImageAuditor {

    public static ImageAuditorResult auditorResult(String imageUrl) {
        String secretId = "AKIDwKTFnCEYWHrEOKuIZbO1TfdhZDk7g5g2";
        String secretKey = "84qNf5GApJ8j31mxtYziCPM7XicBW7TZ";
        String endpoint = "ims.tencentcloudapi.com";
        String region = "ap-shanghai";
        try {
            Credential cred = new Credential(secretId, secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(endpoint);

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            ImsClient client = new ImsClient(cred, region, clientProfile);

            ImageModerationRequest req = new ImageModerationRequest();
            req.setFileUrl(imageUrl);

            ImageModerationResponse resp = client.ImageModeration(req);
            String suggestion = resp.getSuggestion();
            String label = resp.getLabel();
            ImageAuditorSuggestionEnum imageAuditorSuggestionEnum = ImageAuditorSuggestionEnum.asOf(suggestion);
            ImageAuditorLabelEnum imageAuditorLabelEnum = ImageAuditorLabelEnum.asOf(label);
            return new ImageAuditorResult(imageAuditorSuggestionEnum, imageAuditorLabelEnum);
        } catch (TencentCloudSDKException e) {
            log.error(e);
        }
        return null;
    }

}
