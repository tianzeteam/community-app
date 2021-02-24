package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.user.dao.UserDataMapper;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.entity.UserDataExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;
import java.util.Objects;

/**
 * @author jason
 **/
@Service
public class UserDataService {

    @Resource
    UserDataMapper userDataMapper;

    public int create(UserData userData) {
        userData.setCreatedTime(new Date());
        return userDataMapper.insertSelective(userData);
    }

    public int update(UserData userData) {
        userData.setUpdatedTime(new Date());
        return userDataMapper.updateByPrimaryKeySelective(userData);
    }

    public int deleteById(Long id) {
        return userDataMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userDataMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserData> selectByPage(UserData userData, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserDataExample example = new UserDataExample();
        UserDataExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userDataMapper.selectByExample(example);
    }

    public UserData findById(Long id) {
        UserData userData = userDataMapper.selectByPrimaryKey(id);
        return userData;
    }

    public UserData findByUserId(Long userId) {
        UserDataExample example = new UserDataExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserData> list = userDataMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public UserData initUserData(Long userId) {
        UserData userData = new UserData();
        userData.withUserId(userId)
                .withUserLevel(0)
                .withFocusCount(0)
                .withFollowCount(0)
                .withLikeCount(0)
                .withPostCount(0)
                .withCommentCount(0)
                .withReplyCount(0)
                .withEvaluateCount(0)
                .withContributeCount(0)
                .withRevision(0)
                .withCreatedTime(new Date());
        userDataMapper.insertSelective(userData);
        return userData;
    }

    public void updateSign(Long userId, String sign) {
        userDataMapper.updateSign(userId, sign);
    }

    public void bindWechat(Long userId, String openid) {
        UserData userData = findByUserId(userId);
        if(Objects.isNull(userData)) {
            initUserData(userId);
        }
        if (StringUtils.isNotBlank(userData.getWxOpenid())) {
            throw new ServiceException("已经绑定过微信了");
        }
        userData.setWxOpenid(openid);
        userDataMapper.updateByPrimaryKeySelective(userData);
    }

    public void increaseLikeCount(Long userId) {
        userDataMapper.increaseLikeCount(userId);
    }

    public void decreaseLikeCount(Long userId) {
        userDataMapper.decreaseLikeCount(userId);
    }
}