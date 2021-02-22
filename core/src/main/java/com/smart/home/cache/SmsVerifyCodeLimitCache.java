package com.smart.home.cache;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author jason
 * @date 2021/2/21
 * 限制一分钟不能重复发送验证码，所以手机号码作为key，缓存失效时间1分钟，如果缓存里有该手机号说明发送频率太高了
 **/
public class SmsVerifyCodeLimitCache {

    private static Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build();

    public static boolean canSend(String phone) {
        boolean canSend = false;
        String result = cache.getIfPresent(phone);
        if (StringUtils.isBlank(result)) {
            canSend = true;
            cache.put(phone, "0");
        }
        return canSend;
    }

}
