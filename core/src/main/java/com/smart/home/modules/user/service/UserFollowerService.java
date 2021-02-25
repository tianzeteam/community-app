package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserFollowerMapper;
import com.smart.home.modules.user.dto.MyFocusDTO;
import com.smart.home.modules.user.dto.MyFollowerDTO;
import com.smart.home.modules.user.entity.UserFollower;
import com.smart.home.modules.user.entity.UserFollowerExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserFollowerService {

    @Resource
    UserFollowerMapper userFollowerMapper;

    public int create(UserFollower userFollower) {
        userFollower.setCreatedTime(new Date());
        return userFollowerMapper.insertSelective(userFollower);
    }

    public int update(UserFollower userFollower) {
        return userFollowerMapper.updateByPrimaryKeySelective(userFollower);
    }

    public int deleteById(Long id) {
        return userFollowerMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userFollowerMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserFollower> selectByPage(UserFollower userFollower, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserFollowerExample example = new UserFollowerExample();
        UserFollowerExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userFollowerMapper.selectByExample(example);
    }

    public UserFollower findById(Long id) {
        UserFollower userFollower = userFollowerMapper.selectByPrimaryKey(id);
        return userFollower;
    }

    public List<MyFollowerDTO> myFollowerByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userFollowerMapper.myFollowerByPage(userId);
    }

    public List<MyFocusDTO> myFocusByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userFollowerMapper.myFocusByPage(userId);
    }
}
