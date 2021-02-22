package com.smart.home.util;

import com.alibaba.fastjson.JSON;
import com.smart.home.dto.APIResponse;
import com.smart.home.common.contants.SecurityConsts;
import com.smart.home.common.enums.APIResponseCodeEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class HttpServletUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpServletUtil.class);

    /**
     * 获取ServletRequestAttributes对象
     */
    public static ServletRequestAttributes getServletRequest() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取HttpServletRequest对象
     */
    public static HttpServletRequest getRequest() {
        return getServletRequest().getRequest();
    }

    /**
     * 获取HttpServletResponse对象
     */
    public static HttpServletResponse getResponse() {
        return getServletRequest().getResponse();
    }

    /**
     * 获取请求参数
     */
    public static String getParameter(String param) {
        return getRequest().getParameter(param);
    }

    /**
     * 获取请求参数，带默认值
     */
    public static String getParameter(String param, String defaultValue) {
        String parameter = getRequest().getParameter(param);
        return StringUtils.isEmpty(parameter) ? defaultValue : parameter;
    }

    /**
     * 获取请求参数转换为int类型
     */
    public static Integer getParameterInt(String param) {
        return Integer.valueOf(getRequest().getParameter(param));
    }

    /**
     * 获取请求参数转换为int类型，带默认值
     */
    public static Integer getParameterInt(String param, Integer defaultValue) {
        return Integer.valueOf(getParameter(param, String.valueOf(defaultValue)));
    }

    public static String getSevletPath() {
        return getRequest().getServletPath();
    }

    /**
     * 得到去除pah variable的请求地址
     *
     * @return
     */
    public static String getServletPathWithOutPathVariable() {
        HttpServletRequest request = getRequest();
        Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (Objects.isNull(pathVariables)) {
            return request.getServletPath();
        }
        if (pathVariables.isEmpty()) {
            return request.getServletPath();
        }
        // 一般地址就是最后一位是参数，所以取第一个替换掉就行了
        String variableName = (String) pathVariables.keySet().iterator().next();
        String requestPath = request.getServletPath();
        requestPath = requestPath.replace(variableName, "");
        return requestPath;
    }

    /**
     * 返回JSON格式数据
     *
     * @param data
     */
    public static void writeResponseJSON(String data) {
        HttpServletResponse response = getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(200);
        try {
            response.getWriter().write(data);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void response401() {
        HttpServletResponse response = getResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(APIResponse.ERROR(APIResponseCodeEnum.NO_AUTH.getCode(), null)));
        } catch (IOException e) {
            logger.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static String getHeaderJwtToken() {
        return getRequest().getHeader(SecurityConsts.SECURITY_HEAD_NAME);
    }

    /**
     * 一个简单的签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
    private boolean validateSign(HttpServletRequest request, String secret) {
        String requestSign = request.getParameter("sign");
        if (StringUtils.isBlank(requestSign)) {
            return false;
        }
        List<String> keys = new ArrayList<String>(request.getParameterMap().keySet());
        keys.remove("sign");
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append("=").append(request.getParameter(key)).append("&");
        }
        String linkString = sb.toString();
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);

        String sign = DigestUtils.md5Hex(linkString + secret);

        return StringUtils.equals(sign, requestSign);
    }
}
