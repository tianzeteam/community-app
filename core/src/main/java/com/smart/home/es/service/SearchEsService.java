package com.smart.home.es.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.DateUtils;
import com.smart.home.common.util.UUIDUtil;
import com.smart.home.enums.EsSaveTypeEnum;
import com.smart.home.es.bean.*;
import com.smart.home.es.common.EsConstant;
import com.smart.home.es.dao.SearchKeyRepository;
import com.smart.home.es.dto.EsSearchDTO;
import com.smart.home.es.dto.NameCountDTO;
import com.smart.home.modules.user.dao.UserDataMapper;
import com.smart.home.modules.user.dao.UserFocusMapper;
import com.smart.home.modules.user.dto.UserDataDTO;
import com.smart.home.modules.user.entity.UserFocus;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

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
    @Autowired
    private EsCommonService esCommonService;
    @Resource
    private UserDataMapper userDataMapper;
    @Resource
    private UserFocusMapper userFocusMapper;


    /**
     * 存下用户搜索的关键字
     *
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
     *
     * @param size
     */
    public List<NameCountDTO> findHotKeyword(int size) throws ServiceException {
        try {
            List<NameCountDTO> list = esAggregationQueryService.queryForList("keyword", size, SearchKey.class);
            return list;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 综合搜索
     * 多线程搜索
     * 分类聚合
     */
    public Map<String, Object> multiple(String contents) {
        Long startTime = System.currentTimeMillis();
        Callable<List<CommunityPostBean>> communityPostCallable = new Callable<List<CommunityPostBean>>() {
            @Override
            public List<CommunityPostBean> call() {
                EsSearchDTO esSearchDTO = new EsSearchDTO();
                esSearchDTO.setFrom(0);
                esSearchDTO.setSize(50);
                esSearchDTO.setContents(contents);
                List<CommunityPostBean> search = esCommonService.search(EsConstant.communityPostIndex, esSearchDTO, CommunityPostBean.class);
                return search;
            }
        };
        Callable<List<ArticleBean>> articleCallable = new Callable<List<ArticleBean>>() {
            @Override
            public List<ArticleBean> call() {
                EsSearchDTO esSearchDTO = new EsSearchDTO();
                esSearchDTO.setFrom(0);
                esSearchDTO.setSize(2);
                esSearchDTO.setContents(contents);
                List<ArticleBean> search = esCommonService.search(EsConstant.articleIndex, esSearchDTO, ArticleBean.class);
                return search;
            }
        };
        Callable<List<ProductBean>> productCallable = new Callable<List<ProductBean>>() {
            @Override
            public List<ProductBean> call() {
                EsSearchDTO esSearchDTO = new EsSearchDTO();
                esSearchDTO.setFrom(0);
                esSearchDTO.setSize(2);
                esSearchDTO.setContents(contents);
                List<ProductBean> search = esCommonService.search(EsConstant.productIndex, esSearchDTO, ProductBean.class);
                return search;
            }
        };
        Callable<List<ProductCommentBean>> productCommentCallable = new Callable<List<ProductCommentBean>>() {
            @Override
            public List<ProductCommentBean> call() {
                EsSearchDTO esSearchDTO = new EsSearchDTO();
                esSearchDTO.setFrom(0);
                esSearchDTO.setSize(2);
                esSearchDTO.setContents(contents);
                List<ProductCommentBean> search = esCommonService.search(EsConstant.productCommentIndex, esSearchDTO, ProductCommentBean.class);
                return search;
            }
        };

        FutureTask<List<CommunityPostBean>> cpFutureTask = new FutureTask<>(communityPostCallable);
        FutureTask<List<ArticleBean>> arFutureTask = new FutureTask<>(articleCallable);
        FutureTask<List<ProductBean>> productFutureTask = new FutureTask<>(productCallable);
        FutureTask<List<ProductCommentBean>> productCommentFutureTask = new FutureTask<>(productCommentCallable);
        new Thread(cpFutureTask).start();
        new Thread(arFutureTask).start();
        new Thread(productFutureTask).start();
        new Thread(productCommentFutureTask).start();
        Map<String, Object> map = new HashMap<>();

        try {
            List<CommunityPostBean> communityPostBeans = cpFutureTask.get();
            List<ArticleBean> articleBeans = arFutureTask.get();
            List<ProductBean> productBeans = productFutureTask.get();
            List<ProductCommentBean> productCommentBeans = productCommentFutureTask.get();
            communityPostUserMsg(communityPostBeans);
            articleUserMsg(articleBeans);
            productCommentUserMsg(productCommentBeans);
            map.put("communityPosts", communityPostBeans);
            map.put("articles", articleBeans);
            map.put("products", productBeans);
            map.put("productComments", productCommentBeans);
        } catch (InterruptedException e) {
            log.error("get 异常：{}", e);
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.error("get error:{}", e);
            e.printStackTrace();
        }
        //结束时间
        Long endTime = System.currentTimeMillis();
        Long diffTime = endTime - startTime;
        System.out.println("代码执行时间:" + diffTime);
        return map;
    }

    private void communityPostUserMsg(List<CommunityPostBean> list) {
        List<Long> collect = list.parallelStream().map(CommunityPostBean::getUserId).collect(Collectors.toList());
        List<UserDataDTO> userDataDTOS = userDataMapper.selectByUserIds(collect);
        if (CollUtil.isEmpty(userDataDTOS)) {
            return;
        }
        list.stream().forEach(x -> {
            userDataDTOS.stream().forEach(y -> {
                if (x.getUserId().equals(y.getUserId())) {
                    x.setNickname(y.getNickname());
                    x.setHeadImg(y.getHeadUrl());
                    x.setLevel(y.getUserLevel());
                    x.setUserRemark(y.getRemark());
                }
            });
        });
    }

    private void articleUserMsg(List<ArticleBean> list) {
        List<Long> collect = list.parallelStream().map(ArticleBean::getUserId).collect(Collectors.toList());
        List<UserDataDTO> userDataDTOS = userDataMapper.selectByUserIds(collect);
        if (CollUtil.isEmpty(userDataDTOS)) {
            return;
        }
        list.stream().forEach(x -> {
            userDataDTOS.stream().forEach(y -> {
                if (x.getUserId().equals(y.getUserId())) {
                    x.setNickname(y.getNickname());
                    x.setHeadImg(y.getHeadUrl());
                    x.setLevel(y.getUserLevel());
                    x.setUserRemark(y.getRemark());
                }
            });
        });
    }

    private void productCommentUserMsg(List<ProductCommentBean> list) {
        List<Long> collect = list.parallelStream().map(ProductCommentBean::getUserId).collect(Collectors.toList());
        List<UserDataDTO> userDataDTOS = userDataMapper.selectByUserIds(collect);
        if (CollUtil.isEmpty(userDataDTOS)) {
            return;
        }
        list.stream().forEach(x -> {
            userDataDTOS.stream().forEach(y -> {
                if (x.getUserId().equals(y.getUserId())) {
                    x.setNickname(y.getNickname());
                    x.setHeadImg(y.getHeadUrl());
                    x.setLevel(y.getUserLevel());
                    x.setUserRemark(y.getRemark());
                }
            });
        });
    }


    /**
     * 社区-关注
     * 查询关注的人
     * 查询es userid多value，id倒序，分页
     * 组装用户信息
     */
    public List<Object> focusNewsSearch(Long userId, Integer pageNum, Integer pageSize) {
        List<UserFocus> userFocusList = userFocusMapper.selectByUserId(userId);
        if (CollUtil.isEmpty(userFocusList)) {
            return Collections.EMPTY_LIST;
        }
        List<Long> collect = userFocusList.parallelStream().map(UserFocus::getId).collect(Collectors.toList());
        EsSearchDTO esSearchDTO = new EsSearchDTO();
        esSearchDTO.setLongList(collect);
        esSearchDTO.setFrom(pageNum);
        esSearchDTO.setSize(pageSize);
        List<String> list = esCommonService.searchMultiple(new String[]{EsConstant.communityPostIndex, EsConstant.productCommentIndex, EsConstant.articleIndex},
                esSearchDTO);
        if (CollUtil.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        List<Long> userIds = new ArrayList<>();
        for (String str : list) {
            JSONObject jsonObject = JSONObject.parseObject(str);
            Long focusUserid = jsonObject.getLong("userId");
            userIds.add(focusUserid);
        }
        List<Object> objects = new ArrayList<>();
        List<UserDataDTO> userDataDTOS = userDataMapper.selectByUserIds(userIds);
        if (CollUtil.isNotEmpty(userDataDTOS)) {
            list.stream().forEach(x -> {
                userDataDTOS.stream().forEach(y -> {
                    JSONObject jsonObject = JSONObject.parseObject(x);
                    Long esUserId = jsonObject.getLong("userId");
                    if (esUserId != null && esUserId.equals(y.getUserId())) {
                        Integer saveType = jsonObject.getInteger("saveType");
                        if (saveType == null) {
                            return;
                        }
                        EsSaveTypeEnum esSaveTypeEnum = EsSaveTypeEnum.saveTypeEnumByType(saveType);
                        switch (esSaveTypeEnum){
                            case ARTICLE:
                                ArticleBean articleBean = JSONObject.parseObject(x, ArticleBean.class);
                                UserDataDTO userDataDTO = userDataMapper.getByUserId(articleBean.getUserId());
                                if (userDataDTO != null) {
                                    articleBean.setNickname(y.getNickname());
                                    articleBean.setHeadImg(y.getHeadUrl());
                                    articleBean.setLevel(y.getUserLevel());
                                    articleBean.setUserRemark(y.getRemark());
                                }
                                objects.add(articleBean);
                                break;
                            case COMMUNITY_POST:
                                CommunityPostBean communityPostBean = JSONObject.parseObject(x, CommunityPostBean.class);
                                UserDataDTO dataDTO = userDataMapper.getByUserId(communityPostBean.getUserId());
                                if (dataDTO != null) {
                                    communityPostBean.setNickname(y.getNickname());
                                    communityPostBean.setHeadImg(y.getHeadUrl());
                                    communityPostBean.setLevel(y.getUserLevel());
                                    communityPostBean.setUserRemark(y.getRemark());
                                }
                                objects.add(communityPostBean);
                                break;
                            case PRODUCT_COMMENT:
                                ProductCommentBean productCommentBean = JSONObject.parseObject(x, ProductCommentBean.class);
                                UserDataDTO user = userDataMapper.getByUserId(productCommentBean.getUserId());
                                if (user != null) {
                                    productCommentBean.setNickname(y.getNickname());
                                    productCommentBean.setHeadImg(y.getHeadUrl());
                                    productCommentBean.setLevel(y.getUserLevel());
                                    productCommentBean.setUserRemark(y.getRemark());
                                }
                                objects.add(productCommentBean);
                                break;
                        }
                    }
                });
            });
        }

        return objects;
    }


}
