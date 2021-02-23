package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleLikeHistoryMapper;
import com.smart.home.modules.article.entity.ArticleLikeHistory;
import com.smart.home.modules.article.entity.ArticleLikeHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ArticleLikeHistoryService {

    @Resource
    ArticleLikeHistoryMapper articleLikeHistoryMapper;

    public int create(ArticleLikeHistory articleLikeHistory) {
        articleLikeHistory.setCreatedTime(new Date());
        return articleLikeHistoryMapper.insertSelective(articleLikeHistory);
    }

    public int update(ArticleLikeHistory articleLikeHistory) {
        return articleLikeHistoryMapper.updateByPrimaryKeySelective(articleLikeHistory);
    }

    public int deleteById(Long id) {
        return articleLikeHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            articleLikeHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ArticleLikeHistory> selectByPage(ArticleLikeHistory articleLikeHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleLikeHistoryExample example = new ArticleLikeHistoryExample();
        ArticleLikeHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleLikeHistoryMapper.selectByExample(example);
    }

    public ArticleLikeHistory findById(Long id) {
        ArticleLikeHistory articleLikeHistory = articleLikeHistoryMapper.selectByPrimaryKey(id);
        return articleLikeHistory;
    }

}
