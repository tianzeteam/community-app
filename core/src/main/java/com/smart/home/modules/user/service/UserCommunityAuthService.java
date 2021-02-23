package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserCommunityAuthMapper;
import com.smart.home.modules.user.entity.UserCommunityAuth;
import com.smart.home.modules.user.entity.UserCommunityAuthExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserCommunityAuthService {

    @Resource
    UserCommunityAuthMapper userCommunityAuthMapper;

    public int create(UserCommunityAuth userCommunityAuth) {
        return userCommunityAuthMapper.insertSelective(userCommunityAuth);
    }

    public int update(UserCommunityAuth userCommunityAuth) {
        return userCommunityAuthMapper.updateByPrimaryKeySelective(userCommunityAuth);
    }

    public int deleteById(Long id) {
        return userCommunityAuthMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userCommunityAuthMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserCommunityAuth> selectByPage(UserCommunityAuth userCommunityAuth, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserCommunityAuthExample example = new UserCommunityAuthExample();
        UserCommunityAuthExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userCommunityAuthMapper.selectByExample(example);
    }

    public UserCommunityAuth findById(Long id) {
        UserCommunityAuth userCommunityAuth = userCommunityAuthMapper.selectByPrimaryKey(id);
        return userCommunityAuth;
    }

}
