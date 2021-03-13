package com.smart.home.es.service;

import com.smart.home.common.util.DateUtils;
import com.smart.home.common.util.UUIDUtil;
import com.smart.home.es.bean.SearchKey;
import com.smart.home.es.dao.SearchKeyRepository;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jason
 * @date 2021/3/13
 **/
@Service
public class SearchEsService {

    @Autowired
    private SearchKeyRepository searchKeyRepository;

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

}
