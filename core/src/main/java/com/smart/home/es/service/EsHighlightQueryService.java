package com.smart.home.es.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Service
public class EsHighlightQueryService extends EsQueryService {

    public <T> AggregatedPage test(String searchFiledName, String searchFieldValue, String highlightFieldName, Class<T> T) {
        //google的色值
        String preTag = "<font color='#dd4b39'>";
        String postTag = "</font>";
        BoolQueryBuilder boolQueryBuilder = newBoolQueryBuilder()
                .must(equals(searchFiledName, searchFieldValue));

        SearchQuery searchQuery = newNativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightFields(new HighlightBuilder.Field(highlightFieldName).preTags(preTag).postTags(postTag))
                .withHighlightFields(new HighlightBuilder.Field(searchFiledName).preTags(preTag).postTags(postTag))
                .build();
        AggregatedPage<T> aggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, T, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<T> list = new ArrayList<>();
                //获取遍历查询结果
                for (SearchHit hit : searchResponse.getHits()) {
                    if (searchResponse.getHits().getHits().length <= 0) {
                        return null;
                    }
                    // 转换自己需要得bean TODO
                    Map map = hit.getSourceAsMap();
                    System.out.println(map);
                    HighlightField highlightField = hit.getHighlightFields().get(highlightFieldName);
                    if (!Objects.isNull(highlightField)) {
                        // 得到高亮结果
                        String result = highlightField.fragments()[0].toString();
                        System.out.println(result);
                    }
                    HighlightField searchHighlightField = hit.getHighlightFields().get(searchFiledName);
                    if (!Objects.isNull(searchHighlightField)) {
                        String result = searchHighlightField.fragments()[0].toString();
                        System.out.println(result);
                    }
                }
                return new AggregatedPageImpl<>((List<T>) list);
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });

        aggregatedPage.get().forEach(model -> {
            System.out.println(model);
        });

        return aggregatedPage;
    }

}
