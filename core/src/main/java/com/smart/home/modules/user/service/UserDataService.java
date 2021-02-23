package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserDataMapper;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.entity.UserDataExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

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

}
