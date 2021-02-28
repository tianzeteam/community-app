package com.smart.home.controller.pc;

import com.google.common.html.HtmlEscapers;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.StaticPageCreateDTO;
import com.smart.home.controller.pc.request.StaticPageUpdateDTO;
import com.smart.home.controller.pc.response.StaticPageVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.other.entity.StatisticPage;
import com.smart.home.modules.other.service.StatisticPageService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "固定页管理")
@RestController
@RequestMapping("/api/pc/statisticPage")
public class StatisticPageController {

    @Autowired
    private StatisticPageService statisticPageService;

    @ApiOperation("创建静态页面")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.OPERATOR})
    @PostMapping("/create")
    public APIResponse create(@Valid StaticPageCreateDTO staticPageCreateDTO, BindingResult bindingResult) {
        StatisticPage statisticPage = new StatisticPage();
        BeanUtils.copyProperties(staticPageCreateDTO, statisticPage);
        statisticPage.setCreatedBy(UserUtils.getLoginUserId());
        statisticPage.setContents(HtmlUtils.htmlEscape(statisticPage.getContents()));
        return APIResponse.OK(statisticPageService.create(statisticPage));
    }

    @ApiOperation("更新静态页面")
    @PostMapping("/update")
    public APIResponse update(@Valid StaticPageUpdateDTO staticPageUpdateDTO, BindingResult bindingResult) {
        StatisticPage statisticPage = new StatisticPage();
        BeanUtils.copyProperties(staticPageUpdateDTO, statisticPage);
        statisticPage.setCreatedBy(UserUtils.getLoginUserId());
        statisticPage.setContents(HtmlUtils.htmlEscape(statisticPage.getContents()));
        return APIResponse.OK(statisticPageService.update(statisticPage));
    }

    @ApiOperation("删除静态页面")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        statisticPageService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询静态页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<StaticPageVO>> selectByPage(String name, int pageNum, int pageSize) {
        List<StatisticPage> list = statisticPageService.selectSummaryByPage(name, pageNum, pageSize);
        List<StaticPageVO> resultList = BeanCopyUtils.convertListTo(list, StaticPageVO::new);
        return APIResponse.OK(ResponsePageBean.restPage(resultList));
    }

    @ApiOperation("按主键ID查询静态页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @GetMapping("/selectById")
    public APIResponse<StatisticPage> selectById(Integer id) {
        StatisticPage statisticPage = statisticPageService.findById(id);
        statisticPage.setContents(HtmlUtils.htmlUnescape(statisticPage.getContents()));
        return APIResponse.OK(statisticPage);
    }

}