package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.article.dao.ArticleChannelMapper;
import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.article.entity.ArticleChannelExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class ArticleChannelService {

    @Resource
    ArticleChannelMapper articleChannelMapper;

    public int create(ArticleChannel articleChannel) {
        articleChannel.setCreatedTime(new Date());
        return articleChannelMapper.insertSelective(articleChannel);
    }

    public int update(ArticleChannel articleChannel) {
        articleChannel.setUpdatedTime(new Date());
        return articleChannelMapper.updateByPrimaryKeySelective(articleChannel);
    }

    public int deleteById(Long id) {
        return articleChannelMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            articleChannelMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ArticleChannel> selectByPage(ArticleChannel articleChannel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleChannelExample example = new ArticleChannelExample();
        ArticleChannelExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return articleChannelMapper.selectByExample(example);
    }

    public ArticleChannel findById(Long id) {
        ArticleChannel articleChannel = articleChannelMapper.selectByPrimaryKey(id.intValue());
        return articleChannel;
    }

}
