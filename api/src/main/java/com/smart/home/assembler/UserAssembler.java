package com.smart.home.assembler;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.dto.auth.User;
import com.smart.home.dto.auth.UserCommunityPermits;
import com.smart.home.dto.auth.UserRole;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserRoleMapping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jason
 * @date 2021/2/22
 **/
public class UserAssembler {

    public static User assemblerUser(UserAccount userAccount) {
        User user = new User();
        BeanUtils.copyProperties(userAccount, user);
        String permits = userAccount.getPermits();
        if (StringUtils.isNotBlank(permits)) {
            user.setPermissions(JSON.parseObject(permits, Map.class));
        }
        if (userAccount.getRoleCodeList() != null) {
            user.setRoleCodeList(userAccount.getRoleCodeList());
        }
        UserCommunityPermits userCommunityPermits = new UserCommunityPermits();
        if (userAccount.getUserCommunityAuth() != null) {
            BeanUtils.copyProperties(userAccount.getUserCommunityAuth(), userCommunityPermits);
        } else {
            userCommunityPermits.setAdminFlag(YesNoEnum.NO.getCode());
            userCommunityPermits.setBlackFlag(YesNoEnum.NO.getCode());
            userCommunityPermits.setEffectiveEndDate(null);
            userCommunityPermits.setEffectiveStartDate(null);
            userCommunityPermits.setSpeakFlag(YesNoEnum.NO.getCode());
        }
        user.setUserCommunityPermits(userCommunityPermits);
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
