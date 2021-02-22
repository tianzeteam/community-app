package com.smart.home.cache;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @author jason
 * @date 2021/2/21
 * 存放验证码的地方，5分钟内有效
 **/
public class SmsVerifyCodeCache {

    private static Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    public static void put(String phone, String code) {
        cache.put(phone, code);
    }

    public static boolean isValid(String phone,String code) {
        boolean valid = code.equals(cache.getIfPresent(phone));
        cache.invalidate(phone);
        return valid;
    }

}
