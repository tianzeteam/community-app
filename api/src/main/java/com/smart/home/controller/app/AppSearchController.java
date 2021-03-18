package com.smart.home.controller.app;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.es.dto.NameCountDTO;
import com.smart.home.es.service.SearchEsService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jason
 * @date 2021/3/14
 **/
@Slf4j
@Api(tags = "搜索")
@RestController
@RequestMapping("/api/app/search")
public class AppSearchController {

    @Autowired
    private SearchEsService searchEsService;


    @ApiOperation("搜索前N位的热词")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "搜索多少位？", required = true)
    })
    @AnonAccess
    @GetMapping("/hotKeys")
    public APIResponse hotKeys(@RequestParam(required = true) Integer size) {
        List<NameCountDTO> list = searchEsService.findHotKeyword(size);
        List<String> resultList = new ArrayList<>();
        for (NameCountDTO nameCountDTO : list) {
            resultList.add(nameCountDTO.getName());
        }
        return APIResponse.OK(resultList);
    }

    @ApiOperation("综合搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contents", value = "文案", required = true)
    })
    @AnonAccess
    @GetMapping("/multiple")
    public APIResponse multiple(String contents) {
        log.info("multiple综合搜索:{}", contents);
        Map<String, Object> multiple = searchEsService.multiple(contents);
        return APIResponse.OK(multiple);
    }

    @ApiOperation("关注人已发布搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @GetMapping("/focus/news")
    public APIResponse focusNews(Integer pageNum, Integer pageSize) {
        Long loginUserId = UserUtils.getLoginUserId();
        List<Object> objects = searchEsService.focusNewsSearch(loginUserId, pageNum, pageSize);
        return APIResponse.OK(objects);
    }


}
