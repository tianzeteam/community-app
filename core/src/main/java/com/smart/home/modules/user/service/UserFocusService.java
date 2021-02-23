package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserFocusMapper;
import com.smart.home.modules.user.entity.UserFocus;
import com.smart.home.modules.user.entity.UserFocusExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserFocusService {

    @Resource
    UserFocusMapper userFocusMapper;

    public int create(UserFocus userFocus) {
        userFocus.setCreatedTime(new Date());
        return userFocusMapper.insertSelective(userFocus);
    }

    public int update(UserFocus userFocus) {
        return userFocusMapper.updateByPrimaryKeySelective(userFocus);
    }

    public int deleteById(Long id) {
        return userFocusMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userFocusMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserFocus> selectByPage(UserFocus userFocus, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserFocusExample example = new UserFocusExample();
        UserFocusExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userFocusMapper.selectByExample(example);
    }

    public UserFocus findById(Long id) {
        UserFocus userFocus = userFocusMapper.selectByPrimaryKey(id);
        return userFocus;
    }

}
