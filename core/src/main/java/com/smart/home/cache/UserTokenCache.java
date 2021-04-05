package com.smart.home.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.smart.home.modules.user.entity.UserAccount;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jason
 * @date 2021/2/17
 **/
public class UserTokenCache {

    private static Cache<String, UserAccount> cache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS)
            .build();

    public static void put(String key, UserAccount value) {
        if (!Objects.isNull(value)) {
            cache.put(key, value);
        }
    }

    public static UserAccount get(String key) {
        return cache.getIfPresent(key);
    }

    public static boolean remove(String key) {
        if (StringUtils.isNotBlank(key)) {
            cache.invalidate(key);
            return true;
        }
        return false;
    }

    public static long size() {
        return cache.size();
    }

}
