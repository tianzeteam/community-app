package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.modules.user.dao.UserFocusMapper;
import com.smart.home.modules.user.dao.UserFollowerMapper;
import com.smart.home.modules.user.entity.UserFocus;
import com.smart.home.modules.user.entity.UserFocusExample;
import com.smart.home.modules.user.entity.UserFollower;
import com.smart.home.modules.user.entity.UserFollowerExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 **/
@Service
public class UserFocusService {

    @Resource
    UserFocusMapper userFocusMapper;
    @Resource
    private UserFollowerMapper userFollowerMapper;

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

    /**
     * 关注用户
     * @param focusUserId 被关注用户主键ID
     * @param loginUserId 操作用户主键ID
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void focusUser(Long focusUserId, Long loginUserId) throws ServiceException {
        // 判断是否关注过了
        UserFocusExample example = new UserFocusExample();
        example.createCriteria().andUserIdEqualTo(loginUserId).andFocusUserIdEqualTo(focusUserId);
        if (userFocusMapper.countByExample(example) > 0) {
            throw new ServiceException("已经关注该用户了");
        }
        UserFocus userFocus = new UserFocus();
        userFocus.setUserId(loginUserId);
        userFocus.setFocusUserId(focusUserId);
        userFocus.setCreatedTime(new Date());
        userFocus.setFocusRate(0);
        userFocusMapper.insertSelective(userFocus);
        // 放关注一个用户后，对方会产生一个粉丝记录
        UserFollower userFollower = new UserFollower();
        userFollower.setCreatedTime(new Date());
        userFollower.setFollowerUserId(loginUserId);
        userFollower.setUserId(focusUserId);
        // 判断下是否是互相关注
        example = new UserFocusExample();
        example.createCriteria().andUserIdEqualTo(focusUserId).andFocusUserIdEqualTo(loginUserId);
        if (userFocusMapper.countByExample(example) > 0) {
            userFollower.setFollowEach(YesNoEnum.YES.getCode());
        } else {
            userFollower.setFollowEach(YesNoEnum.NO.getCode());
        }
        userFollowerMapper.insertSelective(userFollower);
    }

    public void cancelFocusUser(Long focusUserId, Long loginUserId) {
        // 从关注列表里删掉这个人
        UserFocusExample example = new UserFocusExample();
        example.createCriteria().andUserIdEqualTo(loginUserId).andFocusUserIdEqualTo(focusUserId);
        int affectRow = userFocusMapper.deleteByExample(example);
        if (affectRow > 0) {
            // 删除我在对方的粉丝列表里
            UserFollowerExample userFollowerExample = new UserFollowerExample();
            userFollowerExample.createCriteria().andUserIdEqualTo(focusUserId).andFollowerUserIdEqualTo(loginUserId);
            userFollowerMapper.deleteByExample(userFollowerExample);
            // 如果被关注的人也是我的粉丝，所以在我的粉丝列表里要取消互相关注标记
            userFollowerExample = new UserFollowerExample();
            userFollowerExample.createCriteria().andUserIdEqualTo(loginUserId).andFollowerUserIdEqualTo(focusUserId);
            List<UserFollower> userFollowerList = userFollowerMapper.selectByExample(userFollowerExample);
            if (!CollectionUtils.isEmpty(userFollowerList)) {
                UserFollower userFollower = userFollowerList.get(0);
                userFollower.setFollowEach(YesNoEnum.NO.getCode());
                userFollowerMapper.updateByPrimaryKeySelective(userFollower);
            }
        }
    }

    public long countByFocusUserId(Long userId, Long focusUserId) {
        UserFocusExample example = new UserFocusExample();
        example.createCriteria().andUserIdEqualTo(userId).andFocusUserIdEqualTo(focusUserId);
        return userFocusMapper.countByExample(example);
    }
}
