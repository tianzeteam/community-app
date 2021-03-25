package com.smart.home.controller.common;

import com.google.common.base.Splitter;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.system.entity.SysDict;
import com.smart.home.modules.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author jason
 * @date 2021/3/2
 **/
@Api(tags = "数据字典")
@RestController
@RequestMapping("/api/common/dict")
public class CommonDictController {

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation("获取投诉原因")
    @AnonAccess
    @GetMapping("/reportReasonItems")
    public APIResponse reportReasonItems() {
        SysDict sysDict = sysDictService.queryByDictCode("report.reason");
        if (Objects.isNull(sysDict)) {
            return APIResponse.ERROR("report.reason数据字典还未配置");
        }
        return APIResponse.OK(Splitter.on(",").splitToList(sysDict.getDictValue()));
    }

    @ApiOperation("获取安卓下载地址")
    @AnonAccess
    @GetMapping("/androidDownloadUrl")
    public APIResponse androidDownloadUrl() {
        SysDict sysDict = sysDictService.queryByDictCode("android.download.url");
        if (Objects.isNull(sysDict)) {
            return APIResponse.ERROR("android.download.url数据字典还未配置");
        }
        return APIResponse.OK(sysDict.getDictValue());
    }

    @ApiOperation("获取苹果市场地址")
    @AnonAccess
    @GetMapping("/appleStoreUrl")
    public APIResponse appleStoreUrl() {
        SysDict sysDict = sysDictService.queryByDictCode("apple.appstore.url");
        if (Objects.isNull(sysDict)) {
            return APIResponse.ERROR("apple.appstore.url数据字典还未配置");
        }
        return APIResponse.OK(sysDict.getDictValue());
    }

}
