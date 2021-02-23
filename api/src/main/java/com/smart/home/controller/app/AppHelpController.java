package com.smart.home.controller.app;

import com.smart.home.assembler.HelpAssembler;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.controller.app.response.HelpVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.system.entity.SysHelp;
import com.smart.home.modules.system.service.SysHelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Api(tags = "设置-帮助")
@RestController
@RequestMapping("/api/app/help")
public class AppHelpController {

    @Autowired
    private SysHelpService sysHelpService;

    @ApiOperation("获得顶级目录下的记录")
    @AnonAccess
    @GetMapping("/queryTopList")
    public APIResponse<List<HelpVO>> queryTopList() {
        List<SysHelp> list = sysHelpService.findByPidAndState(0, RecordStatusEnum.NORMAL.getStatus());
        List<HelpVO> helpVOList = HelpAssembler.assemblerHelpVO(list);
        return APIResponse.OK(helpVOList);
    }

    @ApiOperation("根据pid获取子记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "父级主键ID")
    })
    @AnonAccess
    @GetMapping("/queryListByPid")
    public APIResponse<List<HelpVO>> queryListByPid(Integer pid) {
        List<SysHelp> list = sysHelpService.findByPidAndState(pid, RecordStatusEnum.NORMAL.getStatus());
        List<HelpVO> helpVOList = HelpAssembler.assemblerHelpVO(list);
        return APIResponse.OK(helpVOList);
    }

}
