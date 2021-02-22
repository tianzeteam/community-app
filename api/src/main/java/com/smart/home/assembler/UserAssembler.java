package com.smart.home.assembler;

import com.smart.home.dto.auth.User;
import com.smart.home.dto.auth.UserRole;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserRoleMapping;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/22
 **/
public class UserAssembler {

    public static User assemblerUser(UserAccount userAccount) {
        User user = new User();
        BeanUtils.copyProperties(userAccount, user);
        return user;
    }

    public static List<UserRole> assemblerUserRoles(List<UserRoleMapping> list) {
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = null;
        for (UserRoleMapping userRoleMapping : list) {
            userRole = new UserRole();
            BeanUtils.copyProperties(userRoleMapping, userRole);
            userRoles.add(userRole);
        }
        return userRoles;
    }

}
