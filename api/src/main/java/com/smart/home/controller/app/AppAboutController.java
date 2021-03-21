package com.smart.home.controller.app;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.AboutVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.system.entity.SysAgreement;
import com.smart.home.modules.system.entity.SysDict;
import com.smart.home.modules.system.service.SysAgreementService;
import com.smart.home.modules.system.service.SysDictService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Objects;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/23
 **/
@Api(tags = "设置-关于")
@RestController
@RequestMapping("/api/app/about")
public class AppAboutController {

    @Autowired
    private SysAgreementService sysAgreementService;
    @Autowired
    private SysDictService sysDictService;

    @ApiOperation("查询关于分类记录")
    @AnonAccess
    @GetMapping("/queryAllType")
    public APIResponse<List<AboutVO>> queryAllType() {
        AboutVO vo1 = new AboutVO(0, "关于APP", null);
        AboutVO vo2 = new AboutVO(1, "更新日志", null);
        AboutVO vo3 = new AboutVO(2, "用户协议", null);
        AboutVO vo4 = new AboutVO(3, "隐私政策", null);
        AboutVO vo5 = new AboutVO(4, "证照中心", null);
        AboutVO vo6 = new AboutVO(5, "权限说明", null);
        AboutVO vo7 = new AboutVO(6, "第三方服务", null);
        return APIResponse.OK(Arrays.asList(vo1, vo2, vo3, vo4, vo5, vo6, vo7));
    }

    @ApiOperation("根据类型查询内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型：0关于APP1更新日志2用户协议3隐私政策4证照中心5权限说明6第三方服务", required = true)
    })
    @AnonAccess
    @GetMapping("queryByType")
    public APIResponse<AboutVO> queryByType(Integer type) {
        SysAgreement sysAgreement = sysAgreementService.findByType(type);
        if (Objects.isNull(sysAgreement)) {
            return APIResponse.ERROR("该类型["+type+"]数据尚未配置");
        }
        return APIResponse.OK(BeanCopyUtils.convertTo(sysAgreement, AboutVO::new));
    }

    @ApiOperation("获取app版本号")
    @AnonAccess
    @GetMapping("/queryAppVersion")
    public APIResponse queryAppVersion() {
        SysDict sysDict = sysDictService.queryByDictCode("app.version");
        if (Objects.isNull(sysDict)) {
            return APIResponse.ERROR("app.version的数据字段未配置");
        }
        return APIResponse.OK(sysDict.getDictValue());
    }

}
