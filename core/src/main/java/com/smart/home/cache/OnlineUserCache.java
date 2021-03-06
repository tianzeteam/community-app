package com.smart.home.cache;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jason
 * @date 2021/3/6
 **/
public class OnlineUserCache {

    private static Set<Long> setUser = new HashSet<>(1024);

    public static void online(Long userId) {
        setUser.add(userId);
    }

    public static int countOnline() {
        return setUser.size();
    }

    /**
     * 每日凌晨清理掉
     */
    public static void removeAll() {
        setUser.clear();
    }

}
