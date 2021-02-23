package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserBlackMapper;
import com.smart.home.modules.user.entity.UserBlack;
import com.smart.home.modules.user.entity.UserBlackExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserBlackService {

    @Resource
    UserBlackMapper userBlackMapper;

    public int create(UserBlack userBlack) {
        userBlack.setCreatedTime(new Date());
        return userBlackMapper.insertSelective(userBlack);
    }

    public int update(UserBlack userBlack) {
        return userBlackMapper.updateByPrimaryKeySelective(userBlack);
    }

    public int deleteById(Long id) {
        return userBlackMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userBlackMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserBlack> selectByPage(UserBlack userBlack, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserBlackExample example = new UserBlackExample();
        UserBlackExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userBlackMapper.selectByExample(example);
    }

    public UserBlack findById(Long id) {
        UserBlack userBlack = userBlackMapper.selectByPrimaryKey(id);
        return userBlack;
    }

}
