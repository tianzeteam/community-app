package com.smart.home.modules.user.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.AuditCategoryEnum;
import com.smart.home.modules.other.service.AuditHistoryService;
import com.smart.home.modules.user.dao.UserCommunityAuthMapper;
import com.smart.home.modules.user.entity.UserCommunityAuth;
import com.smart.home.modules.user.entity.UserCommunityAuthExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;
import java.util.Objects;

/**
 * @author jason
 **/
@Service
public class UserCommunityAuthService {

    @Resource
    UserCommunityAuthMapper userCommunityAuthMapper;
    @Autowired
    private AuditHistoryService auditHistoryService;

    public int createOrUpdate(UserCommunityAuth userCommunityAuth) {
        Long createdBy = userCommunityAuth.getCreatedBy();
        UserCommunityAuthExample example = new UserCommunityAuthExample();
        example.createCriteria().andUserIdEqualTo(userCommunityAuth.getUserId());
        List<UserCommunityAuth> dbResultList = userCommunityAuthMapper.selectByExample(example);
        UserCommunityAuth dbResult = null;
        if (!CollectionUtils.isEmpty(dbResultList)) {
            dbResult = dbResultList.get(0);
        }
        int affectRow = 0;
        if (Objects.isNull(dbResult)) {
            affectRow = userCommunityAuthMapper.insertSelective(userCommunityAuth);
        } else {
            affectRow = update(dbResult);
        }
        if (affectRow > 0) {
            if (userCommunityAuth.getBlackFlag() != null) {
                if (YesNoEnum.YES.getCode() == userCommunityAuth.getBlackFlag()) {
                    auditHistoryService.create(AuditCategoryEnum.BAN, userCommunityAuth.getUserId(),"封禁社区用户", YesNoEnum.YES, createdBy);
                } else {
                    auditHistoryService.create(AuditCategoryEnum.BAN, userCommunityAuth.getUserId(),"解除封禁社区用户", YesNoEnum.YES, createdBy);
                }
            }
            if (userCommunityAuth.getSpeakFlag() != null) {
                if (YesNoEnum.YES.getCode() == userCommunityAuth.getSpeakFlag()) {
                    auditHistoryService.create(AuditCategoryEnum.NO_SPEECH, userCommunityAuth.getUserId(),"禁言社区用户", YesNoEnum.YES, createdBy);
                } else {
                    auditHistoryService.create(AuditCategoryEnum.NO_SPEECH, userCommunityAuth.getUserId(),"解除禁言社区用户", YesNoEnum.YES, createdBy);
                }
            }
        }
        return affectRow;
    }

    public int update(UserCommunityAuth userCommunityAuth) {
        return userCommunityAuthMapper.updateByPrimaryKeySelective(userCommunityAuth);
    }

    public UserCommunityAuth findById(Long id) {
        UserCommunityAuth userCommunityAuth = userCommunityAuthMapper.selectByPrimaryKey(id);
        return userCommunityAuth;
    }

    public void setAsAdmin(List<Long> idList, Long loginUserId) {
        for (Long userId : idList) {
            UserCommunityAuth userCommunityAuth = findByUserId(userId);
            if (Objects.isNull(userCommunityAuth)) {
                initDataWithAdminFlag(userId, 0, loginUserId);
            } else {
                userCommunityAuthMapper.updateAdminFlag(userId, 1);
            }
        }
    }

    public void cancelAdmin(List<Long> idList) {
        for (Long userId : idList) {
            userCommunityAuthMapper.updateAdminFlag(userId, 0);
        }
    }

    public List<UserCommunityAuth> queryAllAdminUser(Long userId, String nickName) {
        return userCommunityAuthMapper.queryAllAdminUser(userId, nickName);
    }

    private UserCommunityAuth findByUserId(Long userId) {
        UserCommunityAuthExample example = new UserCommunityAuthExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserCommunityAuth> list = this.userCommunityAuthMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    private void initDataWithAdminFlag(Long userId, int adminFlag, Long loginUserId) {
        UserCommunityAuth userCommunityAuth = new UserCommunityAuth();
        userCommunityAuth.setCreatedTime(new Date());
        userCommunityAuth.setCreatedBy(loginUserId);
        userCommunityAuth.withBlackFlag(YesNoEnum.NO.getCode())
                .withSpeakFlag(YesNoEnum.NO.getCode())
                .withUserId(userId)
                .withAdminFlag(adminFlag);
        this.userCommunityAuthMapper.insertSelective(userCommunityAuth);
    }
}
