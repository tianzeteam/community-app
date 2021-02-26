package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.other.entity.AuditHistory;
import com.smart.home.modules.other.service.AuditHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "操作记录")
@RestController
@RequestMapping("/api/pc/auditHistory")
public class AuditHistoryController {

    @Autowired
    private AuditHistoryService auditHistoryService;

    @ApiOperation("分页查询审核历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType", value = "审核类型：9文章人工审核", required = true),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<AuditHistory>> selectByPage(Integer queryType, int pageNum, int pageSize) {
        AuditHistory auditHistory = new AuditHistory();
        auditHistory.setCategory(queryType);
        List<AuditHistory> list = auditHistoryService.selectByPage(auditHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询审核历史")
    @GetMapping("/selectById")
    public APIResponse<AuditHistory> selectById(Long id) {
        return APIResponse.OK(auditHistoryService.findById(id));
    }

}