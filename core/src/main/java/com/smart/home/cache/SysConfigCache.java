package com.smart.home.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

public class SysConfigCache {

    private static Cache<String, String> cache = CacheBuilder.newBuilder().build();

    public static void put(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            cache.put(key, value);
        }
    }

    public static String get(String key) {
        return cache.getIfPresent(key);
    }

    public static void remove(String key) {
        cache.invalidate(key);
    }

}
