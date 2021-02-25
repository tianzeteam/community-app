package com.smart.home.es.service;

import com.smart.home.es.bean.CommunityBean;
import com.smart.home.es.common.EsPageResult;
import com.smart.home.es.dto.CommunitySearchDTO;

import java.util.List;

/**
 * Created by tangchenglong on 2021/2/25.
 */
public interface CommunityEsService
{

    /**
     * 搜索
     *
     * @param communitySearchDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    EsPageResult<CommunityBean> queryPage(CommunitySearchDTO communitySearchDTO, Integer pageNum, Integer pageSize);

    /**
     * 创建
     *
     * @param communityBean
     * @return
     */
    void saveCommunityBean(CommunityBean communityBean);


    /**
     * 批量创建
     *
     * @param communityBeanList
     */
    void batchSaveCommunityBean(List<CommunityBean> communityBeanList);

    /**
     * 根据Id获取
     *
     * @param id
     * @return
     */
    CommunityBean findCommunityBeanById(Integer id);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteCommunityBeanById(Integer id);

    /**
     * 更新
     *
     * @param communityBean
     */
    void updateCommunityBean(CommunityBean communityBean);


}
