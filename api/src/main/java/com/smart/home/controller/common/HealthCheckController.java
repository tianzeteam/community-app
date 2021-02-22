package com.smart.home.controller.common;

import com.smart.home.dto.APIResponse;
import com.smart.home.modules.system.entity.SysTraceLog;
import com.smart.home.modules.system.service.SysTraceLogService;
import com.smart.home.dto.auth.annotation.AnonAccess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/2/17
 **/
@Api(tags = "开发辅助接口，无需对接")
@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @Autowired
    private SysTraceLogService sysTraceLogService;

    @ApiOperation("健康检查")
    @AnonAccess
    @GetMapping("/check")
    public String check() {
        return "ok";
    }

    @ApiOperation("查询追踪日志")
    @AnonAccess
    @GetMapping("/sysTraceLog/queryByTraceId")
    public APIResponse<SysTraceLog> queryByTraceId(@RequestParam String traceId) {
        return APIResponse.OK(sysTraceLogService.queryByTraceId(traceId));
    }


}
