package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserPrivacyMapper;
import com.smart.home.modules.user.entity.UserPrivacy;
import com.smart.home.modules.user.entity.UserPrivacyExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserPrivacyService {

    @Resource
    UserPrivacyMapper userPrivacyMapper;

    public int create(UserPrivacy userPrivacy) {
        userPrivacy.setCreatedTime(new Date());
        return userPrivacyMapper.insertSelective(userPrivacy);
    }

    public int update(UserPrivacy userPrivacy) {
        userPrivacy.setUpdatedTime(new Date());
        return userPrivacyMapper.updateByPrimaryKeySelective(userPrivacy);
    }

    public int deleteById(Long id) {
        return userPrivacyMapper.deleteByPrimaryKey(id.intValue());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userPrivacyMapper.deleteByPrimaryKey(id.intValue());
        }
    }

    public List<UserPrivacy> selectByPage(UserPrivacy userPrivacy, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserPrivacyExample example = new UserPrivacyExample();
        UserPrivacyExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userPrivacyMapper.selectByExample(example);
    }

    public UserPrivacy findById(Long id) {
        UserPrivacy userPrivacy = userPrivacyMapper.selectByPrimaryKey(id.intValue());
        return userPrivacy;
    }

}
