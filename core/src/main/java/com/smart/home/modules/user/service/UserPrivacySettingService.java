package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserPrivacySettingMapper;
import com.smart.home.modules.user.entity.UserPrivacySetting;
import com.smart.home.modules.user.entity.UserPrivacySettingExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserPrivacySettingService {

    @Resource
    UserPrivacySettingMapper userPrivacySettingMapper;

    public int create(UserPrivacySetting userPrivacySetting) {
        return userPrivacySettingMapper.insertSelective(userPrivacySetting);
    }

    public int update(UserPrivacySetting userPrivacySetting) {
        return userPrivacySettingMapper.updateByPrimaryKeySelective(userPrivacySetting);
    }

    public int deleteById(Long id) {
        return userPrivacySettingMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userPrivacySettingMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserPrivacySetting> selectByPage(UserPrivacySetting userPrivacySetting, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserPrivacySettingExample example = new UserPrivacySettingExample();
        UserPrivacySettingExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userPrivacySettingMapper.selectByExample(example);
    }

    public UserPrivacySetting findById(Long id) {
        UserPrivacySetting userPrivacySetting = userPrivacySettingMapper.selectByPrimaryKey(id);
        return userPrivacySetting;
    }

}
