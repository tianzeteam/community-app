package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.FeedbackStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.modules.user.dao.UserFeedbackMapper;
import com.smart.home.modules.user.entity.UserFeedback;
import com.smart.home.modules.user.entity.UserFeedbackExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserFeedbackService {

    @Resource
    UserFeedbackMapper userFeedbackMapper;

    public int create(UserFeedback userFeedback) {
        userFeedback.setCreatedTime(new Date());
        userFeedback.setRevision(0);
        return userFeedbackMapper.insertSelective(userFeedback);
    }

    /**
     * 更新成已读
     * @param id
     * @param userId
     */
    public void updateToRead(Long id, Long userId) {
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setId(id);
        userFeedback.setReadFlag(YesNoEnum.YES.getCode());
        userFeedback.setUpdatedBy(userId);
        update(userFeedback);
    }

    /**
     * 更细成已关闭
     * @param id
     * @param userId
     */
    public void updateToClose(Long id, Long userId) {
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setId(id);
        userFeedback.setUpdatedBy(userId);
        userFeedback.setState(FeedbackStatusEnum.CLOSED.getStatus());
        update(userFeedback);
    }

    public int update(UserFeedback userFeedback) {
        userFeedback.setUpdatedTime(new Date());
        return userFeedbackMapper.updateByPrimaryKeySelective(userFeedback);
    }

    public int deleteById(Long id) {
        return userFeedbackMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userFeedbackMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserFeedback> selectByPage(UserFeedback userFeedback, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserFeedbackExample example = new UserFeedbackExample();
        UserFeedbackExample.Criteria criteria = example.createCriteria();
        if (userFeedback.getUserId() != null) {
           criteria.andUserIdEqualTo(userFeedback.getUserId());
        }
        return userFeedbackMapper.selectByExample(example);
    }

    public UserFeedback findById(Long id) {
        UserFeedback userFeedback = userFeedbackMapper.selectByPrimaryKey(id);
        return userFeedback;
    }

}
