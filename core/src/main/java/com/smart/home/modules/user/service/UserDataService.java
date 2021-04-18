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
        UserDataExample example = new UserDataExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserData> list = userDataMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
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

    public void bindWechat(Long userId, String openid) throws ServiceException {
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

    public UserData selectByIdOrNickname(Long userId, String nickName) {
        return userDataMapper.selectByIdOrNickname(userId, nickName);
    }

    /**
     * 累积用户的命中敏感词数量
     * @param loginUserId
     * @param size
     * @return
     */
    public int increaseHitSensitiveCount(Long loginUserId, int size) {
        return userDataMapper.increaseHitSensitiveCount(loginUserId, size);
    }

    /**
     * 累计文本异常数量
     * @param userId
     * @return
     */
    public int increaseTextExceptionCount(Long userId) {
        return userDataMapper.increaseTextExceptionCount(userId);
    }
    /**
     * 累计图片异常数量 TODO 对接
     * @param userId
     * @return
     */
    public int increaseImageExceptionCount(Long userId) {
        return userDataMapper.increaseImageExceptionCount(userId);
    }

    /**
     * 累计人工认定异常数量
     * @param userId
     * @return
     */
    public int increaseManuallyExceptionCount(Long userId) {
        return userDataMapper.increaseManuallyExceptionCount(userId);
    }

    public void increasePostCount(Long userId) {
        userDataMapper.increasePostCount(userId);
    }

    public void increaseCommentCount(Long userId) {
        userDataMapper.increaseCommentCount(userId);
    }

    public void increaseEvaluateCount(Long userId) {
        userDataMapper.increaseEvaluateCount(userId);
    }

    public void increaseReplyCount(Long userId) {
        userDataMapper.increaseReplyCount(userId);
    }

    public UserData findByWxOpenid(String wx) {
        UserDataExample example = new UserDataExample();
        example.createCriteria().andWxOpenidEqualTo(wx);
        List<UserData> list = this.userDataMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public void updateOpenid(Long userId, String openid) {
        this.userDataMapper.updateOpendid(userId, openid);
    }
}
