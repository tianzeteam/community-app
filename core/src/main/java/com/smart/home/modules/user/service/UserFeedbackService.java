package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
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
        return userFeedbackMapper.insertSelective(userFeedback);
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
        // TODO 按需根据字段查询
        return userFeedbackMapper.selectByExample(example);
    }

    public UserFeedback findById(Long id) {
        UserFeedback userFeedback = userFeedbackMapper.selectByPrimaryKey(id);
        return userFeedback;
    }

}
