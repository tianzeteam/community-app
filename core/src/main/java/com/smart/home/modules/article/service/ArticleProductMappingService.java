package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleProductMappingMapper;
import com.smart.home.modules.article.entity.ArticleProductMapping;
import com.smart.home.modules.article.entity.ArticleProductMappingExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ArticleProductMappingService {

    @Resource
    ArticleProductMappingMapper articleProductMappingMapper;

    public int create(ArticleProductMapping articleProductMapping) {
        return articleProductMappingMapper.insertSelective(articleProductMapping);
    }

    public int update(ArticleProductMapping articleProductMapping) {
        return articleProductMappingMapper.updateByPrimaryKeySelective(articleProductMapping);
    }

    public int deleteById(Long id) {
        return articleProductMappingMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            articleProductMappingMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ArticleProductMapping> selectByPage(ArticleProductMapping articleProductMapping, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleProductMappingExample example = new ArticleProductMappingExample();
        ArticleProductMappingExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleProductMappingMapper.selectByExample(example);
    }

    public ArticleProductMapping findById(Long id) {
        ArticleProductMapping articleProductMapping = articleProductMappingMapper.selectByPrimaryKey(id);
        return articleProductMapping;
    }

}
