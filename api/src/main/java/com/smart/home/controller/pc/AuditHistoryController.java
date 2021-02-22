package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.other.entity.AuditHistory;
import com.smart.home.modules.other.service.AuditHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "other审核历史接口")
@RestController
@RequestMapping("/api/pc/auditHistory")
public class AuditHistoryController {

    @Autowired
    private AuditHistoryService auditHistoryService;

    @ApiOperation("创建审核历史")
    @PostMapping("/create")
    public APIResponse create(AuditHistory auditHistory) {
        auditHistory.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(auditHistoryService.create(auditHistory));
    }

    @ApiOperation("更新审核历史")
    @PostMapping("/update")
    public APIResponse update(AuditHistory auditHistory) {
        auditHistory.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(auditHistoryService.update(auditHistory));
    }

    @ApiOperation("删除审核历史")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        auditHistoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询审核历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<AuditHistory>> selectByPage(AuditHistory auditHistory, int pageNum, int pageSize) {
        List<AuditHistory> list = auditHistoryService.selectByPage(auditHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询审核历史")
    @GetMapping("/selectById")
    public APIResponse<AuditHistory> selectById(Long id) {
        return APIResponse.OK(auditHistoryService.findById(id));
    }

}