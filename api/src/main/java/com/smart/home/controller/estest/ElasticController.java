package com.smart.home.controller.estest;

import com.google.common.collect.Maps;
import com.smart.home.dto.APIResponse;
import com.smart.home.es.bean.SearchKey;
import com.smart.home.es.dto.NameCountDTO;
import com.smart.home.es.service.ElasticService;
import com.smart.home.es.service.EsAggregationQueryService;
import com.smart.home.es.service.EsIndexService;
import com.smart.home.es.service.EsQueryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author jason
 * @date 2021/2/17
 * http://localhost:8080/ems/elastic/init
 * http://localhost:8080/ems/elastic/all
 **/
@Log4j2
@RestController
@RequestMapping("/elastic")
public class ElasticController {

    @Autowired
    private ElasticService elasticService;
    @Autowired
    private EsQueryService esQueryService;
    @Autowired
    private EsAggregationQueryService esAggregationQueryService;
    @Autowired
    private EsIndexService esIndexService;

    @GetMapping("/deleteIndex")
    public APIResponse deleteIndex(){
        return APIResponse.OK(esIndexService.deleteIndex(SearchKey.class));
    }

    @GetMapping("/createIndex")
    public APIResponse createIndex(){
        return APIResponse.OK(esIndexService.createIndex(SearchKey.class));
    }

    @GetMapping("/all")
    public Iterator<SearchKey> all(){
        return elasticService.findAll();
    }

    @GetMapping("/saveSearchKeyword")
    public void saveSearchKeyword(@NotBlank String keyword) {
        elasticService.saveKeyword(keyword);
    }

    @GetMapping("/findSearchKeyword")
    public APIResponse<List<SearchKey>> findSearchKeyword(String keyword) {
        Map<String, String> map = Maps.newHashMap();
        map.put("keyword", keyword);
        List<SearchKey> list = esQueryService.queryForList(map, SearchKey.class);
        return APIResponse.OK(list);
    }

    @GetMapping("/findHotKeyword")
    public APIResponse<List<NameCountDTO>> findHotKeyword() {
        List<NameCountDTO> list = esAggregationQueryService.queryForList("keyword", 10, SearchKey.class);
        return APIResponse.OK(list);
    }

}
