package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.system.SysActionLogSearchDTO;
import com.smart.home.controller.pc.response.SysActionLogVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.system.entity.SysActionLog;
import com.smart.home.modules.system.service.SysActionLogService;
import com.smart.home.util.ResponsePageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author jason
**/
@Api(tags = "登陆记录")
@RestController
@RequestMapping("/api/pc/sysActionLog")
public class SysActionLogController{

    @Autowired
    private SysActionLogService sysActionLogService;

    @ApiOperation("分页查询登陆记录")
    @RoleAccess(RoleConsts.SUPER_ADMIN)
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<SysActionLogVO>> selectByPage(@RequestBody SysActionLogSearchDTO sysActionLogSearchDTO) {
        SysActionLog sysActionLog = new SysActionLog();
        BeanUtils.copyProperties(sysActionLogSearchDTO, sysActionLog);
        List<SysActionLog> list = sysActionLogService.selectByPage(sysActionLog, sysActionLogSearchDTO.getPageNum(), sysActionLogSearchDTO.getPageSize());
        List<SysActionLogVO> resultList = BeanCopyUtils.convertListTo(list, SysActionLogVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

}