package com.smart.home.cloud.wechat.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * @author jason
 * @date 2021/3/8
 **/
@Log4j2
public class WechatLoginUtil {

    public static String[] getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WechatProperties.APPID + "&secret=" + WechatProperties.SECRET + "&code=" + code + "&grant_type=authorization_code";
        URI uri = URI.create(url);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();

                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }
                String responseMessage = sb.toString().trim();
                log.info("微信登陆getAccessToken返回消息：{}", responseMessage);
                JSONObject object = JSON.parseObject(responseMessage);
                String accessToken = object.getString("access_token");
                String openID = object.getString("openid");
                //String refreshToken = object.getString("refresh_token");
                //long expires_in = object.getLong("expires_in");
                return new String[]{accessToken, openID};
            }
        } catch (ClientProtocolException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        } catch (IllegalStateException e) {
            log.error(e);
        } catch (JSONException e) {
            log.error(e);
        }
        return null;
    }

    private void getUserInfo(String accessToken,String openID, long expires_in) {
        if (isAccessTokenIsInvalid(accessToken, openID) && System.currentTimeMillis() < expires_in) {
            String uri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openID;
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(URI.create(uri));
            try {
                HttpResponse response = client.execute(get);
                if (response.getStatusLine().getStatusCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                        builder.append(temp);
                    }
                    JSONObject object = JSON.parseObject(builder.toString().trim());
                    String nikeName = object.getString("nickname");
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private static boolean isAccessTokenIsInvalid(String accessToken, String openID) {
        String url = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openID;
        URI uri = URI.create(url);
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(uri);
        HttpResponse response;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
                StringBuilder sb = new StringBuilder();

                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    sb.append(temp);
                }
                JSONObject object = JSON.parseObject(sb.toString().trim());
                int errorCode = object.getIntValue("errcode");
                if (errorCode == 0) {
                    return true;
                }
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private static void refreshAccessToken(String refreshToken) {
        String uri = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + WechatProperties.APPID + "&grant_type=refresh_token&refresh_token="
                + refreshToken;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(URI.create(uri));
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                    builder.append(temp);
                }
                JSONObject object = JSON.parseObject(builder.toString().trim());
                String accessToken = object.getString("access_token");
                refreshToken = object.getString("refresh_token");
                String openID = object.getString("openid");
                long expires_in = object.getLong("expires_in");
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
