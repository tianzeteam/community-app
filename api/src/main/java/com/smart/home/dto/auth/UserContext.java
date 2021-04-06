package com.smart.home.dto.auth;

import com.smart.home.modules.user.entity.UserAccount;

/**
 * @author jason
 * @date 2020/11/3
 **/
public class UserContext implements AutoCloseable {

    static final ThreadLocal<UserAccount> current = new ThreadLocal<>();

    public UserContext(UserAccount user) {
        current.set(user);
    }

    public static UserAccount getCurrentUser() {
        return current.get();
    }

    @Override
    public void close() throws Exception {
        current.remove();
    }

    public static void manuallyClose() {
        current.remove();
    }
}
