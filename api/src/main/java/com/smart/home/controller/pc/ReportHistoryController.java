package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.response.ReportHistoryVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.other.entity.ReportHistory;
import com.smart.home.modules.other.service.ReportHistoryService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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

    @ApiOperation("删除举报历史")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        reportHistoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询举报历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ReportHistoryVO>> selectByPage(ReportHistory reportHistory, int pageNum, int pageSize) {
        List<ReportHistory> list = reportHistoryService.selectByPage(reportHistory, pageNum, pageSize);
        List<ReportHistoryVO> resultList = BeanCopyUtils.convertListTo(list, ReportHistoryVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("按主键ID查询举报历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectById")
    public APIResponse<ReportHistoryVO> selectById(@RequestParam(required = true) Long id) {
        ReportHistory reportHistory = reportHistoryService.findById(id);
        ReportHistoryVO vo = new ReportHistoryVO();
        BeanUtils.copyProperties(reportHistory, vo);
        return APIResponse.OK(vo);
    }

}