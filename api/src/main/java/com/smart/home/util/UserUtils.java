package com.smart.home.util;

import com.smart.home.dto.auth.User;
import com.smart.home.dto.auth.UserContext;
import com.smart.home.modules.user.entity.UserAccount;

import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/20
 **/
public class UserUtils {

    /**
     * 获取登陆用户主键ID
     * @return
     */
    public static Long getLoginUserId() {
        UserAccount user = UserContext.getCurrentUser();
        if (!Objects.isNull(user)) {
            return user.getId();
        }
        return 0L;
    }

    /**
     * 获取登陆用户
     * @return
     */
    public static UserAccount getLoginUser() {
        return UserContext.getCurrentUser();
    }

}
