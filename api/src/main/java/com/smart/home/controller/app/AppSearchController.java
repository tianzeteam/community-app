package com.smart.home.controller.app;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.es.dto.NameCountDTO;
import com.smart.home.es.service.SearchEsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/14
 **/
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

}
