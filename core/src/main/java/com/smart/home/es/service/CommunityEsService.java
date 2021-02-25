package com.smart.home.es.service;

import com.smart.home.es.bean.CommunityBean;
import com.smart.home.es.common.EsPageResult;
import com.smart.home.es.dto.CommunitySearchDTO;

/**
 * Created by tangchenglong on 2021/2/25.
 */
public interface CommunityEsService
{

    /**
     * 搜索
     * @param communitySearchDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    EsPageResult<CommunityBean> queryPage(CommunitySearchDTO communitySearchDTO, Integer pageNum, Integer pageSize);

}
