package com.smart.home.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.smart.home.es.bean.CommunityBean;
import com.smart.home.es.common.EsPageResult;
import com.smart.home.es.dto.CommunitySearchDTO;
import com.smart.home.es.service.CommunityEsService;
import com.smart.home.es.service.EsQueryService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tangchenglong on 2021/2/25.
 */
@Service
public class CommunityEsServiceImpl extends EsQueryService implements CommunityEsService
{
    /**
     * 搜索
     *
     * @param searchDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public EsPageResult<CommunityBean> queryPage(CommunitySearchDTO searchDTO, Integer pageNum, Integer pageSize)
    {
        if (pageNum <= 0)
        {
            pageNum = 1;
        }

        if (pageSize <= 0)
        {
            pageNum = 20;
        }

        NativeSearchQueryBuilder sourceBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        if (StringUtils.isBlank(searchDTO.getKeyword())
                && Objects.isNull(searchDTO.getCategoryId()))
        {
            queryBuilder.must(QueryBuilders.matchAllQuery());
        }

        if (StringUtils.isNotBlank(searchDTO.getKeyword()))
        {
            queryBuilder.must(QueryBuilders.matchQuery(searchDTO.getKeyword().trim(), "title"));
        }
        if (Objects.nonNull(searchDTO.getCategoryId()))
        {
            queryBuilder.filter(QueryBuilders.termQuery("categoryId", searchDTO.getCategoryId()));
        }

        Map<String, String> sorts = searchDTO.getSorts();

        if (Objects.isNull(sorts) || sorts.isEmpty())
        {
            sorts.put("followerCount", "desc");
        }

        // 设置排序
        List<FieldSortBuilder> fieldSortBuilderList = Lists.newArrayListWithCapacity(sorts.size());

        for (Map.Entry<String, String> entry : sorts.entrySet())
        {
            String field = entry.getKey();

            SortOrder sortOrder = entry.getKey().equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC;

            fieldSortBuilderList.add(SortBuilders.fieldSort(field).order(sortOrder));
        }

        HighlightBuilder highlightBuilder = getHighlightBuilder(null, null, "title");

        sourceBuilder
                .withIndices("community")
                .withTypes("_doc")
                .withQuery(queryBuilder)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(pageRequest);

        if (CollectionUtils.isEmpty(fieldSortBuilderList))
        {
            fieldSortBuilderList.stream().forEach(fieldSortBuilder -> sourceBuilder.withSort(fieldSortBuilder));
        }

        // 设置高亮
        AggregatedPage<CommunityBean> page = elasticsearchTemplate.queryForPage(sourceBuilder.build(), CommunityBean.class, new SearchResultMapper()
        {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable)
            {
                long totalHits = response.getHits().getTotalHits();

                List<T> list = new ArrayList<>();

                SearchHits hits = response.getHits();

                if (totalHits <= 0)
                {
                    return null;
                }

                for (SearchHit searchHit : hits)
                {
                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();

                    T item = JSON.parseObject(searchHit.getSourceAsString(), clazz);

                    Field[] fields = clazz.getDeclaredFields();

                    for (Field field : fields)
                    {
                        field.setAccessible(true);

                        if (highlightFields.containsKey(field.getName()))
                        {
                            try
                            {
                                field.set(item, highlightFields.get(field.getName()).fragments()[0].toString());
                            }
                            catch (IllegalAccessException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    list.add(item);
                }
                return new AggregatedPageImpl<>(list, pageable, totalHits);

            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> type)
            {
                return null;
            }
        });

        if (Objects.isNull(page) || CollectionUtils.isEmpty(page.getContent()))
        {
            return null;
        }

        EsPageResult<CommunityBean> pageResult = new EsPageResult<>(pageNum, pageSize, page.getTotalElements(), page.getContent());

        return pageResult;
    }


}
