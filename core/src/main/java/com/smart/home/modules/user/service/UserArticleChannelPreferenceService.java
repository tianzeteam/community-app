package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.user.dao.UserArticleChannelPreferenceMapper;
import com.smart.home.modules.user.entity.UserArticleChannelPreference;
import com.smart.home.modules.user.entity.UserArticleChannelPreferenceExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserArticleChannelPreferenceService {

    @Resource
    UserArticleChannelPreferenceMapper userArticleChannelPreferenceMapper;

    public void create(Long userId, Long channelId, Integer sort) throws ServiceException {
        // 检查重复记录
        UserArticleChannelPreferenceExample example = new UserArticleChannelPreferenceExample();
        example.createCriteria().andUserIdEqualTo(userId).andChannelIdEqualTo(channelId);
        if (userArticleChannelPreferenceMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("已经添加过该频道了");
        }
        UserArticleChannelPreference userArticleChannelPreference = new UserArticleChannelPreference();
        userArticleChannelPreference.withChannelId(channelId)
                .withSort(sort)
                .withUserId(userId);
        userArticleChannelPreferenceMapper.insertSelective(userArticleChannelPreference);
    }

    public void delete(Long userId, Long channelId) {
        UserArticleChannelPreferenceExample example = new UserArticleChannelPreferenceExample();
        example.createCriteria().andUserIdEqualTo(userId).andChannelIdEqualTo(channelId);
        userArticleChannelPreferenceMapper.deleteByExample(example);
    }

    public List<ArticleChannel> queryMyChannel(Long loginUserId) {
        return userArticleChannelPreferenceMapper.queryMyChannel(loginUserId);
    }
}
