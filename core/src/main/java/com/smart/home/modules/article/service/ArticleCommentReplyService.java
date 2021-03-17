package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleCommentMapper;
import com.smart.home.modules.article.dao.ArticleCommentReplyMapper;
import com.smart.home.modules.article.entity.ArticleCommentReply;
import com.smart.home.modules.article.entity.ArticleCommentReplyExample;
import com.smart.home.modules.user.dao.UserAccountMapper;
import com.smart.home.modules.user.entity.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ArticleCommentReplyService {

    @Resource
    ArticleCommentReplyMapper articleCommentReplyMapper;
    @Resource
    ArticleCommentMapper articleCommentMapper;

    public int update(ArticleCommentReply articleCommentReply) {
        return articleCommentReplyMapper.updateByPrimaryKeySelective(articleCommentReply);
    }

    public int deleteById(Long id) {
        return articleCommentReplyMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            articleCommentReplyMapper.deleteByPrimaryKey(id);
        }
    }

    public List<ArticleCommentReply> selectByPage(ArticleCommentReply articleCommentReply, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleCommentReplyExample example = new ArticleCommentReplyExample();
        ArticleCommentReplyExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleCommentReplyMapper.selectByExample(example);
    }

    public ArticleCommentReply findById(Long id) {
        ArticleCommentReply articleCommentReply = articleCommentReplyMapper.selectByPrimaryKey(id);
        return articleCommentReply;
    }

    public void create(Long loginUserId, Long articleId, Long articleCommentId, String contents) {
        ArticleCommentReply articleCommentReply = new ArticleCommentReply();
        articleCommentReply.withArticleCommentId(articleCommentId)
                .withArticleId(articleId)
                .withContents(contents)
                .withCreatedTime(new Date())
                .withPid(0L)
                .withUserId(loginUserId);
        UserAccount userAccount = articleCommentMapper.findUserAccountIdById(articleCommentId);
        articleCommentReply.setToUserId(userAccount.getId());
        articleCommentReply.setToUserName(userAccount.getNickName());
        articleCommentReplyMapper.insertSelective(articleCommentReply);
    }

    public List<ArticleCommentReply> queryCommentReplyByPageNoLogin(Long articleCommentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentReplyMapper.queryCommentReplyByPageNoLogin(articleCommentId);
    }

    public List<ArticleCommentReply> queryCommentReplyByPageWhenLogin(Long userId, Long articleCommentId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return articleCommentReplyMapper.queryCommentReplyByPageNoLogin(articleCommentId);
    }

}
