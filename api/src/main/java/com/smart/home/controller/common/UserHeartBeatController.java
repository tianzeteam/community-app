package com.smart.home.controller.common;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Api(tags = "客户端心跳")
@RestController
@RequestMapping("/api/common/heartBeat")
public class UserHeartBeatController {

    public static AtomicLong atomicLong = new AtomicLong(0);
    private static Set<Long> tokenSet = new HashSet<>();

    @ApiOperation("发送心跳数据-一小时发一次？")
    @AnonAccess
    @GetMapping("/online")
    public APIResponse online() {
        if (tokenSet.contains(UserUtils.getLoginUserId())) {
            // do nothing
        } else {
            atomicLong.incrementAndGet();
            tokenSet.add(UserUtils.getLoginUserId());
        }
        return APIResponse.OK();
    }

    @ApiOperation("用户关闭客户端")
    @AnonAccess
    @GetMapping("/offline")
    public APIResponse offline() {
        if (tokenSet.contains(UserUtils.getLoginUserId())) {
            tokenSet.remove(UserUtils.getLoginUserId());
            atomicLong.decrementAndGet();
        }
        return APIResponse.OK();
    }

    @ApiOperation("获取在线人数")
    @AnonAccess
    @GetMapping("/onlineCount")
    public APIResponse onlineCount() {
        return APIResponse.OK(atomicLong.get());
    }

}
