package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleCommentMapper;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.entity.ArticleCommentExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ArticleCommentService {

    @Resource
    ArticleCommentMapper articleCommentMapper;

    public int create(ArticleComment articleComment) {
        articleComment.setCreatedTime(new Date());
        return articleCommentMapper.insertSelective(articleComment);
    }

    public int update(ArticleComment articleComment) {
        return articleCommentMapper.updateByPrimaryKeySelective(articleComment);
    }

    public int deleteById(Long id) {
        return articleCommentMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            articleCommentMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ArticleComment> selectByPage(ArticleComment articleComment, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleCommentExample example = new ArticleCommentExample();
        ArticleCommentExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleCommentMapper.selectByExample(example);
    }

    public ArticleComment findById(Long id) {
        ArticleComment articleComment = articleCommentMapper.selectByPrimaryKey(id);
        return articleComment;
    }

}
