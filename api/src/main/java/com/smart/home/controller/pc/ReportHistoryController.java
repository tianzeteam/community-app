package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.other.entity.ReportHistory;
import com.smart.home.modules.other.service.ReportHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "other举报历史接口")
@RestController
@RequestMapping("/api/pc/reportHistory")
public class ReportHistoryController {

    @Autowired
    private ReportHistoryService reportHistoryService;

    @ApiOperation("更新举报历史")
    @PostMapping("/update")
    public APIResponse update(ReportHistory reportHistory) {
        reportHistory.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(reportHistoryService.update(reportHistory));
    }

    @ApiOperation("删除举报历史")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        reportHistoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询举报历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ReportHistory>> selectByPage(ReportHistory reportHistory, int pageNum, int pageSize) {
        List<ReportHistory> list = reportHistoryService.selectByPage(reportHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询举报历史")
    @GetMapping("/selectById")
    public APIResponse<ReportHistory> selectById(Long id) {
        return APIResponse.OK(reportHistoryService.findById(id));
    }

}