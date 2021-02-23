package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleStampHistoryMapper;
import com.smart.home.modules.article.entity.ArticleStampHistory;
import com.smart.home.modules.article.entity.ArticleStampHistoryExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ArticleStampHistoryService {

    @Resource
    ArticleStampHistoryMapper articleStampHistoryMapper;

    public int create(ArticleStampHistory articleStampHistory) {
        articleStampHistory.setCreatedTime(new Date());
        return articleStampHistoryMapper.insertSelective(articleStampHistory);
    }

    public int update(ArticleStampHistory articleStampHistory) {
        return articleStampHistoryMapper.updateByPrimaryKeySelective(articleStampHistory);
    }

    public int deleteById(Long id) {
        return articleStampHistoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            articleStampHistoryMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ArticleStampHistory> selectByPage(ArticleStampHistory articleStampHistory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleStampHistoryExample example = new ArticleStampHistoryExample();
        ArticleStampHistoryExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleStampHistoryMapper.selectByExample(example);
    }

    public ArticleStampHistory findById(Long id) {
        ArticleStampHistory articleStampHistory = articleStampHistoryMapper.selectByPrimaryKey(id);
        return articleStampHistory;
    }

}
