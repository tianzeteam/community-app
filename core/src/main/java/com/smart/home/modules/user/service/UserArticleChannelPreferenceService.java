package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
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

    public int create(UserArticleChannelPreference userArticleChannelPreference) {
        return userArticleChannelPreferenceMapper.insertSelective(userArticleChannelPreference);
    }

    public int update(UserArticleChannelPreference userArticleChannelPreference) {
        return userArticleChannelPreferenceMapper.updateByPrimaryKeySelective(userArticleChannelPreference);
    }

    public int deleteById(Long id) {
        return userArticleChannelPreferenceMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userArticleChannelPreferenceMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserArticleChannelPreference> selectByPage(UserArticleChannelPreference userArticleChannelPreference, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserArticleChannelPreferenceExample example = new UserArticleChannelPreferenceExample();
        UserArticleChannelPreferenceExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userArticleChannelPreferenceMapper.selectByExample(example);
    }

    public UserArticleChannelPreference findById(Long id) {
        UserArticleChannelPreference userArticleChannelPreference = userArticleChannelPreferenceMapper.selectByPrimaryKey(id);
        return userArticleChannelPreference;
    }

}
