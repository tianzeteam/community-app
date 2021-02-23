package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserCollectMapper;
import com.smart.home.modules.user.entity.UserCollect;
import com.smart.home.modules.user.entity.UserCollectExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserCollectService {

    @Resource
    UserCollectMapper userCollectMapper;

    public int create(UserCollect userCollect) {
        userCollect.setCreatedTime(new Date());
        return userCollectMapper.insertSelective(userCollect);
    }

    public int update(UserCollect userCollect) {
        userCollect.setUpdatedTime(new Date());
        return userCollectMapper.updateByPrimaryKeySelective(userCollect);
    }

    public int deleteById(Long id) {
        return userCollectMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userCollectMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserCollect> selectByPage(UserCollect userCollect, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserCollectExample example = new UserCollectExample();
        UserCollectExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userCollectMapper.selectByExample(example);
    }

    public UserCollect findById(Long id) {
        UserCollect userCollect = userCollectMapper.selectByPrimaryKey(id);
        return userCollect;
    }

}
