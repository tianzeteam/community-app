package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.exception.DuplicateDataException;
import com.smart.home.modules.user.dao.UserCollectMapper;
import com.smart.home.modules.user.entity.UserCollect;
import com.smart.home.modules.user.entity.UserCollectExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class UserCollectService {

    @Resource
    UserCollectMapper userCollectMapper;

    public int create(UserCollect userCollect) {
        // 检查有没有收藏过了，如果收藏过了就报错
        UserCollectExample example = new UserCollectExample();
        example.createCriteria().andUserIdEqualTo(userCollect.getUserId())
                .andCollectTypeEqualTo(userCollect.getCollectType())
                .andPrimaryKeyEqualTo(userCollect.getPrimaryKey());
        if (userCollectMapper.countByExample(example) > 0) {
            throw new DuplicateDataException("已经收藏过了");
        }
        userCollect.setCreatedTime(new Date());
        return userCollectMapper.insertSelective(userCollect);
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

    public int delete(Integer collectType, Long userId, Long primaryKey) {
        UserCollectExample example = new UserCollectExample();
        example.createCriteria().andUserIdEqualTo(userId).andCollectTypeEqualTo(collectType).andPrimaryKeyEqualTo(primaryKey);
        return userCollectMapper.deleteByExample(example);
    }

    public List<Long> countCollect(int type, Long userId, List<Long> primaryKeyList) {
        return userCollectMapper.countCollect(type, userId, primaryKeyList);
    }
}
