package com.smart.home.controller.common;

import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.other.entity.StatisticPage;
import com.smart.home.modules.other.service.StatisticPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/28
 **/
@Api(tags = "访问静态页面")
@Log4j2
@Controller
@RequestMapping("/api/static")
public class StaticPageController {

    @Autowired
    private StatisticPageService statisticPageService;

    @ApiOperation("根据页面id访问")
    @AnonAccess
    @GetMapping("/page/{id}")
    public void accessStaticPage(@PathVariable("id") Integer id, HttpServletResponse response) {
        StatisticPage statisticPage = statisticPageService.findById(id);
        String contents = "<h3>404 not found!页面走丢了</h3>";
        if (!Objects.isNull(statisticPage)) {
            contents = HtmlUtils.htmlUnescape(statisticPage.getContents());
        }
        response.setContentType("text/html; charset=UTF-8");
        try {
            response.getWriter().println(contents);
        } catch (IOException e) {
            log.error(e);
        }
    }

}
