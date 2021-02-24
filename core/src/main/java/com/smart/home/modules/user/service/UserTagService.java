package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.modules.user.dao.UserTagMapper;
import com.smart.home.modules.user.entity.UserTag;
import com.smart.home.modules.user.entity.UserTagExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class UserTagService {

    @Resource
    UserTagMapper userTagMapper;

    public int create(UserTag userTag) {
        return userTagMapper.insertSelective(userTag);
    }

    public int update(UserTag userTag) {
        return userTagMapper.updateByPrimaryKeySelective(userTag);
    }

    public int deleteById(Long id) {
        return userTagMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            userTagMapper.deleteByPrimaryKey(id);
        }
    }

    public List<UserTag> selectByPage(UserTag userTag, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserTagExample example = new UserTagExample();
        UserTagExample.Criteria criteria = example.createCriteria();
        // TODO 按需根据字段查询
        return userTagMapper.selectByExample(example);
    }

    public UserTag findById(Long id) {
        UserTag userTag = userTagMapper.selectByPrimaryKey(id);
        return userTag;
    }

    public UserTag findByUserId(Long userId) {
        UserTagExample example = new UserTagExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserTag> list = userTagMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
