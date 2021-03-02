package com.smart.home.modules.article.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.article.dao.ArticleChannelMapper;
import com.smart.home.modules.article.dao.ArticleMapper;
import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.article.entity.ArticleChannelExample;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    ArticleMapper articleMapper;

    public int create(ArticleChannel articleChannel) {
        // 检查同名频道
        ArticleChannelExample example = new ArticleChannelExample();
        example.createCriteria().andTitleEqualTo(articleChannel.getTitle());
        if (articleChannelMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该频道已经存在");
        }
        articleChannel.setCreatedTime(new Date());
        articleChannel.setRevision(0);
        articleChannel.setState(RecordStatusEnum.NORMAL.getStatus());
        return articleChannelMapper.insertSelective(articleChannel);
    }

    public int update(ArticleChannel articleChannel) {
        // 查看变更后的名称有没有冲突
        ArticleChannelExample example = new ArticleChannelExample();
        example.createCriteria().andTitleEqualTo(articleChannel.getTitle()).andIdNotEqualTo(articleChannel.getId());
        if (articleChannelMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("该频道已经存在");
        }
        articleChannel.setUpdatedTime(new Date());
        return articleChannelMapper.updateByPrimaryKeySelective(articleChannel);
    }

    public int deleteById(Long id) {
        return articleChannelMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 检查有没有关联的文章了，有的话不能删除
            if (articleMapper.countArticleByChannelId(id) > 0) {
                throw new ServiceException("该频道有关联的文章了，不能删除");
            }
            articleChannelMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<ArticleChannel> selectByPage(ArticleChannel articleChannel, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        ArticleChannelExample example = new ArticleChannelExample();
        ArticleChannelExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(articleChannel.getTitle())) {
            criteria.andTitleEqualTo(articleChannel.getTitle());
        }
        return articleChannelMapper.selectByExample(example);
    }

    public ArticleChannel findById(Long id) {
        ArticleChannel articleChannel = articleChannelMapper.selectByPrimaryKey(id.intValue());
        return articleChannel;
    }

    public List<ArticleChannel> selectAllValid() {
        ArticleChannelExample example = new ArticleChannelExample();
        example.createCriteria().andStateEqualTo(RecordStatusEnum.NORMAL.getStatus());
        example.setOrderByClause("sort desc");
        return articleChannelMapper.selectByExample(example);
    }

    public List<ArticleChannel> selectAllIndexValid() {
        ArticleChannelExample example = new ArticleChannelExample();
        example.createCriteria().andStateEqualTo(RecordStatusEnum.NORMAL.getStatus())
                    .andIndexFlagEqualTo(YesNoEnum.YES.getCode());
        example.setOrderByClause("sort desc");
        return articleChannelMapper.selectByExample(example);
    }
}
