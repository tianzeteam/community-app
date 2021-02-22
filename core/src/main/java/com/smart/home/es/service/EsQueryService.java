package com.smart.home.es.service;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author jason
 * @date 2021/2/18
 *  1 //查询操作
 *  2         MatchQueryBuilder lastUpdateUser = QueryBuilders.matchQuery("personId", userId);
 *  3         MatchQueryBuilder deleteflag = QueryBuilders.matchQuery("deleteFlag", BaseEntity.DEL_FLAG_DELETE);
 *  4         //创建bool多条件查询
 *  5         BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
 *  6         BoolQueryBuilder mustQuery = boolQueryBuilder.must(lastUpdateUser).must(deleteflag);
 *  7         //嵌套索引，需要使用nest查询
 *  8         mustQuery.must(QueryBuilders.nestedQuery("entityNodes", QueryBuilders.termQuery("entityNodes.node_type", recyclePaperDTO.getNodeType()), ScoreMode.None));
 *  9         //可以使用should查询，不是必需条件
 * 10         BoolQueryBuilder nodeQueryBuilder = QueryBuilders.boolQuery();
 * 11         nodeQueryBuilder.should(QueryBuilders.nestedQuery("entityNodes", QueryBuilders.wildcardQuery("entityNodes.parent_ids", "*," + recyclePaperDTO.getNodeId() + "*"), ScoreMode.None));
 * 12         nodeQueryBuilder.should(......);
 * 13         mustQuery.must(nodeQueryBuilder);
 * 14         //查询使用排序
 * 15         SortBuilder order = new FieldSortBuilder("lastUpdateTime").order(SortOrder.DESC);
 * 16         //可以使用高亮显示，就是html标签
 * 17         HighlightBuilder highlightBuilder = new HighlightBuilder();
 * 18         highlightBuilder.preTags("<span class='highlighted'>")
 * 19                 .postTags(</span>)
 * 20                 .field("paperBaseName");//哪个字段高亮
 * 21         //使用分页查询
 * 22         SearchQuery nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
 * 23                 .withQuery(mustQuery).withSort(order).withHighlightBuilder(highlightBuilder)
 * 24                 .withPageable(PageRequest.of(recyclePaperDTO.getPageNum()-1, recyclePaperDTO.getPageSize())).build();
 * 25         //进行查询，entityMapper使用默认的也可，EsPaperBase.class是需要自己映射的查询类
 * 26         elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder, EsPaperBase.class, new HighlightResultMapper(entityMapper));
 **/
@Service
public class EsQueryService {

    @Autowired
    protected ElasticsearchRestTemplate elasticsearchTemplate;

    /**
     * 精确匹配查找标记为keyword的字段的值
     * @param queryParams key是标记为keyword的字段
     * @param T
     * @param <T>
     * @return 此时标记为text的字段返回的都是null
     */
    public <T> List<T> queryForList(Map<String, String> queryParams, Class<T> T) {
        if (Objects.isNull(queryParams) || queryParams.isEmpty()) {
            throw new RuntimeException("queryParams cannot be null or empty");
        }
        Set<String> keySet = queryParams.keySet();
        String[] includes = keySet.toArray(new String[0]);
        // 设置要查询哪些字段
        SourceFilter sourceFilter = new FetchSourceFilterBuilder().withIncludes(includes).build();
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withSourceFilter(sourceFilter);
        for (String key : keySet) {
            if (StringUtils.isBlank(key)) {
                throw new RuntimeException("queryParams.key cannot be null or empty");
            }
            // 添加查询条件
            String[] values = Splitter.on(",").splitToList(queryParams.get(key)).toArray(new String[0]);
            if (Objects.isNull(values) || values.length == 0) {
                throw new RuntimeException("queryParams.value cannot be null or empty");
            }
            nativeSearchQueryBuilder.withQuery(QueryBuilders.termsQuery(key, values));
        }
        SearchQuery searchQuery = nativeSearchQueryBuilder.build();
        return elasticsearchTemplate.queryForList(searchQuery, T);
    }

    /**
     * 创建排序对象
     * @param fieldName 需要排序的字段
     * @param sortType desc || asc
     * @return
     */
    public Sort createSort(String fieldName, String sortType) {
        Sort.Direction direction = getDirection(sortType);
        return Sort.by(direction,fieldName);
    }

    /**
     * 创建多条件排序
     * @param sortParams key是需要排序的字段， value是 desc或者asc
     * @return
     */
    public Sort createSorts(Map<String, String> sortParams) {
        List<Sort.Order> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            Sort.Order order = new Sort.Order(getDirection(entry.getValue()), entry.getKey());
            list.add(order);
        }
        return Sort.by(list);
    }

    /**
     * 创建带排序的分页对象
     * @param pageSize
     * @param sort
     * @return
     */
    public Pageable createPageableWithSort(int pageSize, Sort sort) {
        Pageable pageable = PageRequest.of(0, pageSize, sort);
        return pageable;
    }

    /**
     * 创建不带排序的分页对象
     * @param pageSize
     * @return
     */
    public Pageable createPageable(int pageSize) {
        return PageRequest.of(0, pageSize);
    }

    private Sort.Direction getDirection(String sortType) {
        Sort.Direction direction = null;
        if ("desc".equalsIgnoreCase(sortType)) {
            direction = Sort.Direction.DESC;
        } else if ("asc".equalsIgnoreCase(sortType)) {
            direction = Sort.Direction.ASC;
        }
        return direction;
    }

    /**
     * 创建一个布尔查询
     * @return
     */
    protected BoolQueryBuilder newBoolQueryBuilder() {
        return QueryBuilders.boolQuery();
    }

    /**
     * 默认的常用的 SearchQuery
     * @return
     */
    protected NativeSearchQueryBuilder newNativeSearchQueryBuilder() {
        return new NativeSearchQueryBuilder();
    }

    /**
     * 匹配查询
     * @param fieldName
     * @param fieldValue
     * @return
     */
    protected MatchQueryBuilder equals(String fieldName, String fieldValue) {
        return new MatchQueryBuilder(fieldName,fieldValue);
    }

}
