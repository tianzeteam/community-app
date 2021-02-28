package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.modules.article.dao.ArticleCommentMapper;
import com.smart.home.modules.article.dao.ArticleMapper;
import com.smart.home.modules.article.entity.Article;
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
    @Resource
    private ArticleMapper articleMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public void create(Long loginUserId, Long articleId, String contents) {
        ArticleComment articleComment = new ArticleComment();
        articleComment.withArticleId(articleId)
                .withContents(contents)
                .withLikeCount(0)
                .withStampCount(0)
                .withCreatedTime(new Date())
                .withUserId(loginUserId);
        Long authorId = articleMapper.findAuthorById(articleId);
        if (authorId.equals(loginUserId)) {
            articleComment.setAuthorFlag(YesNoEnum.YES.getCode());
        } else {
            articleComment.setAuthorFlag(YesNoEnum.NO.getCode());
        }
        articleCommentMapper.insertSelective(articleComment);
        // 文章增加一次评论数量
        articleMapper.increaseCommentCount(articleId);
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

    public void increaseLikeCount(Long id) {
        articleCommentMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        articleCommentMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        articleCommentMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        articleCommentMapper.decreaseStampCount(id);
    }

    public List<ArticleComment> queryCommentByPageNoLogin(Long articleId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentMapper.queryCommentByPageNoLogin(articleId);
    }

    public List<ArticleComment> queryCommentByPageWhenLogin(Long userId, Long articleId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentMapper.queryCommentByPageWhenLogin(userId, articleId);
    }
}
