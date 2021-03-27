package com.smart.home.controller.estest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.smart.home.common.util.RandomUtils;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.enums.EsSaveTypeEnum;
import com.smart.home.es.bean.ArticleBean;
import com.smart.home.es.bean.CommunityPostBean;
import com.smart.home.es.bean.ProductBean;
import com.smart.home.es.bean.SearchKey;
import com.smart.home.es.common.EsConstant;
import com.smart.home.es.dto.EsSearchDTO;
import com.smart.home.es.dto.NameCountDTO;
import com.smart.home.es.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;
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
@RequestMapping("/api/elastic")
public class ElasticController {

    @Autowired
    private ElasticService elasticService;
    @Autowired
    private EsQueryService esQueryService;
    @Autowired
    private EsAggregationQueryService esAggregationQueryService;
    @Autowired
    private EsIndexService esIndexService;
    @Autowired
    private EsCommonService esCommonService;


    @GetMapping("/deleteIndex")
    public APIResponse deleteIndex(String indexName){
        return APIResponse.OK(esIndexService.deleteIndexByName(indexName));
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
        List<NameCountDTO> list = null;
        try {
            list = esAggregationQueryService.queryForList("keyword", 10, SearchKey.class);
        } catch (Exception e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK(list);
    }


    @AnonAccess
    @GetMapping("/save")
    public void save(Long id, String title, String content){
        ArticleBean communityPostBean = new ArticleBean();
        communityPostBean.setTitle(title);
        communityPostBean.setDetails(content);
        communityPostBean.setId(id);
        communityPostBean.setSaveType(1);
        communityPostBean.setUserId(1L);
//        communityPostBean.setCreatedTime(new Date());
        communityPostBean.setRemark(content);
        esCommonService.insertOrUpdateOne(EsConstant.articleIndex, EsConstant.article, id, communityPostBean);
    }

    @AnonAccess
    @GetMapping("/del")
    public void del(Long id){
        esCommonService.deleteOne(EsConstant.articleIndex, EsConstant.article, id);

    }
}
