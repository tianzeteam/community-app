package com.smart.home.config.filter;

import com.smart.home.cache.OnlineUserCache;
import com.smart.home.cache.UserTokenCache;
import com.smart.home.common.contants.SecurityConsts;
import com.smart.home.common.util.JwtUtil;
import com.smart.home.common.util.SpringContextUtil;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.dto.auth.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/17
 **/
public class AccessTokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String accessToken = httpServletRequest.getHeader(SecurityConsts.SECURITY_HEAD_NAME);
        if (StringUtils.isNotBlank(accessToken)) {
            UserAccount user = UserTokenCache.get(accessToken);
            if (Objects.isNull(user)) {
                // 尝试解密，如果解密成功，说明是合法的token，重新取一次用户数据
                try {
                    String userIdString = JwtUtil.getClaim(accessToken, SecurityConsts.ACCOUNT);
                    if (StringUtils.isNotBlank(userIdString)) {
                        UserAccountService userAccountService = SpringContextUtil.getBean(UserAccountService.class);
                        Long userId = Long.valueOf(userIdString);
                        user = userAccountService.findUserByUserId(userId);
                        if (StringUtils.isBlank(user.getAccessToken())) {
                            // 说明用户登出过了
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        }
                        user.setRoleCodeList(userAccountService.findUserRoleCodeList(userId));
                        UserTokenCache.put(user.getAccessToken(), user);
                    } else {
                        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                        return;
                    }
                } catch (Throwable e) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                    httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            }
            // 用户计算日活着
            OnlineUserCache.online(user.getId());
            // controller层或者service层直接用UserContext.getCurrentUser() 就能拿到用户信息了
            new UserContext(user);
        } else {
            UserContext.manuallyClose();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
