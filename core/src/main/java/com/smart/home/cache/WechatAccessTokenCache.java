package com.smart.home.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.smart.home.modules.user.entity.UserAccount;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author jason
 * @date 2021/3/8
 **/
public class WechatAccessTokenCache {

    private static Cache<String, String> cache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

    public static void add(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            cache.put(key, value);
        }
    }

    public static boolean exists(String key) {
        return StringUtils.isNotBlank(cache.getIfPresent(key));
    }

}
