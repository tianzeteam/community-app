package com.smart.home.cloud.qcloud.sms;

import com.smart.home.cloud.qcloud.QcloudProperties;
import com.smart.home.common.exception.ServiceException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.log4j.Log4j2;

/**
 * @author jason
 * @date 2021/3/12
 **/
@Log4j2
public class SmsSendUtil {

    private static SmsClient client = null;

    public synchronized static void initClient() {
        if (client == null) {
            Credential cred = new Credential(QcloudProperties.secretId, QcloudProperties.secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(QcloudProperties.smsEndpoint);
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            client = new SmsClient(cred, "", clientProfile);
        }
    }

    public static boolean sendBindPhoneCode(String sign, String tempId, String code, String phone) throws ServiceException {
        initClient();
        try {
            SendSmsRequest req = new SendSmsRequest();
            req.setSign(sign);
            String[] phoneNumberSet1 = {"+86" + phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            // 短信模板id
            req.setTemplateID(tempId);
            // 验证码和有效分钟数
            String[] templateParamSet1 = {code};
            req.setTemplateParamSet(templateParamSet1);
            // 设置应用id
            req.setSmsSdkAppid("1400491724");
            SendSmsResponse resp = client.SendSms(req);
            if ("Ok".equals(resp.getSendStatusSet()[0].getCode())) {
                return true;
            }
            throw new ServiceException(resp.getSendStatusSet()[0].getMessage());
        } catch (TencentCloudSDKException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static boolean sendVerifyCode(String sign, String tempId, String code, String phone, String minutes) throws ServiceException {
        initClient();
        try {
            SendSmsRequest req = new SendSmsRequest();
            req.setSign(sign);
            String[] phoneNumberSet1 = {"+86" + phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            // 短信模板id
            req.setTemplateID(tempId);
            // 验证码和有效分钟数
            String[] templateParamSet1 = {code, minutes};
            req.setTemplateParamSet(templateParamSet1);
            // 设置应用id
            req.setSmsSdkAppid("1400491724");
            SendSmsResponse resp = client.SendSms(req);
            if ("Ok".equals(resp.getSendStatusSet()[0].getCode())) {
                return true;
            }
            throw new ServiceException(resp.getSendStatusSet()[0].getMessage());
        } catch (TencentCloudSDKException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
