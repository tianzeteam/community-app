package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.system.entity.SysActionLog;
import com.smart.home.modules.system.service.SysActionLogService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author jason
**/
@Api(tags = "com.smart.home.modules.system.entity.SysActionLogExample接口")
@RestController
@RequestMapping("/api/pc/sysActionLog")
public class SysActionLogController{

    @Autowired
    private SysActionLogService sysActionLogService;

    @ApiOperation("创建com.smart.home.modules.system.entity.SysActionLogExample")
    @PostMapping("/create")
    public APIResponse create(SysActionLog sysActionLog) {
                                                                                                                                        sysActionLog.setCreatedBy(UserUtils.getLoginUserId());
                                                                                            return APIResponse.OK(sysActionLogService.create(sysActionLog));
    }

    @ApiOperation("更新com.smart.home.modules.system.entity.SysActionLogExample")
    @PostMapping("/update")
    public APIResponse update(SysActionLog sysActionLog) {
                                                                                                                                        sysActionLog.setCreatedBy(UserUtils.getLoginUserId());
                                                                                            return APIResponse.OK(sysActionLogService.update(sysActionLog));
    }

    @ApiOperation("删除com.smart.home.modules.system.entity.SysActionLogExample")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        sysActionLogService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询com.smart.home.modules.system.entity.SysActionLogExample")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<SysActionLog>> selectByPage(SysActionLog sysActionLog, int pageNum, int pageSize) {
        List<SysActionLog> list = sysActionLogService.selectByPage(sysActionLog, pageNum, pageSize);
        return APIResponse.OK(ResponsePageUtil.restPage(list, list));
    }

    @ApiOperation("按主键ID查询com.smart.home.modules.system.entity.SysActionLogExample")
    @GetMapping("/selectById")
    public APIResponse<SysActionLog> selectById(Long id) {
        return APIResponse.OK(sysActionLogService.findById(id));
    }

}