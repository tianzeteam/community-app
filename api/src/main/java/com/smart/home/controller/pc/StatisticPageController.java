package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.other.entity.StatisticPage;
import com.smart.home.modules.other.service.StatisticPageService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "other静态页面接口")
@RestController
@RequestMapping("/api/pc/statisticPage")
public class StatisticPageController {

    @Autowired
    private StatisticPageService statisticPageService;

    @ApiOperation("创建静态页面")
    @PostMapping("/create")
    public APIResponse create(StatisticPage statisticPage) {
        statisticPage.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(statisticPageService.create(statisticPage));
    }

    @ApiOperation("更新静态页面")
    @PostMapping("/update")
    public APIResponse update(StatisticPage statisticPage) {
        statisticPage.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(statisticPageService.update(statisticPage));
    }

    @ApiOperation("删除静态页面")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        statisticPageService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询静态页面")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<StatisticPage>> selectByPage(StatisticPage statisticPage, int pageNum, int pageSize) {
        List<StatisticPage> list = statisticPageService.selectByPage(statisticPage, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询静态页面")
    @GetMapping("/selectById")
    public APIResponse<StatisticPage> selectById(Long id) {
        return APIResponse.OK(statisticPageService.findById(id));
    }

}