package com.smart.home.es.service;

import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.DateUtils;
import com.smart.home.common.util.UUIDUtil;
import com.smart.home.es.bean.SearchKey;
import com.smart.home.es.dao.SearchKeyRepository;
import com.smart.home.es.dto.NameCountDTO;
import lombok.extern.log4j.Log4j2;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jason
 * @date 2021/3/13
 **/
@Log4j2
@Service
public class SearchEsService {

    @Autowired
    private SearchKeyRepository searchKeyRepository;
    @Autowired
    private EsAggregationQueryService esAggregationQueryService;

    /**
     * 存下用户搜索的关键字
     * @param keyword 关键字
     */
    public void saveKeyword(String keyword) {
        Asserts.notBlank(keyword, "keyword");
        String id = UUIDUtil.uuid();
        SearchKey searchKey = new SearchKey(id, keyword, DateUtils.getCurrentDateTime());
        searchKeyRepository.save(searchKey);
    }

    /**
     * 取排名前size位的的热词
     * @param size
     */
    public List<NameCountDTO> findHotKeyword(int size) throws ServiceException {
        try {
            List<NameCountDTO>  list = esAggregationQueryService.queryForList("keyword", size, SearchKey.class);
            return list;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

}
