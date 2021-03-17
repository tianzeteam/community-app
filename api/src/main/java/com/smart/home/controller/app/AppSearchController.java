package com.smart.home.controller.app;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.es.common.EsConstant;
import com.smart.home.es.dto.NameCountDTO;
import com.smart.home.es.service.EsCommonService;
import com.smart.home.es.service.SearchEsService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
//    @AnonAccess
    @GetMapping("/focus/news")
    public APIResponse focusNews() {
        Long loginUserId = UserUtils.getLoginUserId();
        searchEsService.focusNewsSearch(loginUserId);
        return null;
    }


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    //验证 多索引查询以及返回形式
    @AnonAccess
    @GetMapping("/test")
    public Object test(Long userId, Long userId1) {
        SearchRequest searchRequest = new SearchRequest(EsConstant.communityPostIndex, EsConstant.articleIndex);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("userId", userId.toString(), userId1.toString());
        boolQueryBuilder.must(queryBuilder);
        sourceBuilder.query(boolQueryBuilder);//多条件查询
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.sort("id", SortOrder.DESC);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString()));
            }
            return res;
        } catch (IOException e) {
            log.error("es 查询异常：{}", e.toString());
            log.error("" + e);
            return null;
        }
    }

}
