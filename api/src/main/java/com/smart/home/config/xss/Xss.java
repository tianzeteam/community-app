package com.smart.home.config.xss;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author jason
 * @date 2021/3/16
 **/
@Component
@Data
@ToString
public class Xss {
    /** xss防护开关 */
    private boolean enabled = true;

    /** 拦截规则，可通过“,”隔开多个 */
    private String urlPatterns = "/*";

    /** 默认忽略规则（无需修改） */
    private String defaultExcludes = "/favicon.ico,/img/*,/js/*,/css/*,/lib/*";

    /** 忽略规则，可通过“,”隔开多个 */
    private String excludes = "/api/pc/statisticPage/update,/api/pc/statisticPage/create";

    /**
     * 拼接忽略规则
     */
    public String getExcludes() {
        if (!StringUtils.isEmpty(excludes.trim())) {
            return defaultExcludes + "," + excludes;
        }
        return defaultExcludes;
    }
}
