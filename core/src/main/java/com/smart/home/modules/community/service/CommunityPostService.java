package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostExample;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author jason
 **/
@Service
public class CommunityPostService {

    @Resource
    CommunityPostMapper communityPostMapper;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;

    public int create(CommunityPost communityPost) {
        communityPost.setTopFlag(YesNoEnum.NO.getCode());
        communityPost.setBoutiqueFlag(YesNoEnum.NO.getCode());
        communityPost.setLikeCount(0);
        communityPost.setReportCount(0);
        communityPost.setAuditStatus(AuditStatusEnum.WAIT_AUDIT.getCode());
        communityPost.setRevision(0);
        communityPost.setStampCount(0);
        communityPost.setCollectCount(0);
        communityPost.setCreatedTime(new Date());
        return communityPostMapper.insertSelective(communityPost);
    }

    public int update(CommunityPost communityPost) {
        communityPost.setUpdatedTime(new Date());
        return communityPostMapper.updateByPrimaryKeySelective(communityPost);
    }

    public int deleteById(Long id) {
        return communityPostMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            // 软删除
            communityPostMapper.updateState(id, RecordStatusEnum.DELETE.getStatus());
        }
    }

    public List<CommunityPost> selectByPage(CommunityPost communityPost, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityPostExample example = new CommunityPostExample();
        CommunityPostExample.Criteria criteria = example.createCriteria();
        if (communityPost.getUserId() != null) {
            criteria.andUserIdEqualTo(communityPost.getUserId());
        }
        example.setOrderByClause("created_time desc");
        List<CommunityPost> list = communityPostMapper.selectByExample(example);
        for (CommunityPost post : list) {
            post.setNickName(userAccountService.findNicknameByUserId(post.getUserId()));
        }
        return list;
    }

    public CommunityPost findById(Long id) {
        CommunityPost communityPost = communityPostMapper.selectByPrimaryKey(id);
        return communityPost;
    }

    public void increaseLikeCount(Long id) {
        communityPostMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        communityPostMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        communityPostMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        communityPostMapper.decreaseStampCount(id);
    }

    public void increaseCollectCount(Long id) {
        communityPostMapper.increaseCollectCount(id);
    }

    public void decreaseCollectCount(Long id) {
        communityPostMapper.decreaseCollectCount(id);
    }

    public void updateBoutiqueFlag(Long postId, int flag) {
        communityPostMapper.updateBoutiqueFlag(postId, flag);
    }

    public void updateTopFlag(Long postId, int flag) {
        communityPostMapper.updateTopFlag(postId, flag);
    }

    public List<CommunityPost> queryViaUserIdByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return communityPostMapper.queryViaUserIdByPage(userId);
    }

    public Long countWaitAudit() {
        CommunityPostExample example = new CommunityPostExample();
        example.createCriteria().andAuditStatusEqualTo(AuditStatusEnum.WAIT_AUDIT.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.WAIT_AUDIT.getCode());
        return communityPostMapper.countByExample(example);
    }

    public Long countTextException() {
        CommunityPostExample example = new CommunityPostExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return communityPostMapper.countByExample(example);
    }

    public Long countImageException() {
        CommunityPostExample example = new CommunityPostExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return communityPostMapper.countByExample(example);
    }

    public Long countHasReport() {
        CommunityPostExample example = new CommunityPostExample();
        example.createCriteria().andReportCountGreaterThan(0);
        return communityPostMapper.countByExample(example);
    }

    public Long countHitSensitive() {
        CommunityPostExample example = new CommunityPostExample();
        example.createCriteria().andHitSensitiveCountGreaterThan(0);
        return communityPostMapper.countByExample(example);
    }

    public Long countTotalNormal() {
        CommunityPostExample example = new CommunityPostExample();
        example.createCriteria().andAuditStatusEqualTo(AuditStatusEnum.APPROVED.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.APPROVE.getCode());
        return communityPostMapper.countByExample(example);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void manuallyReject(Long id) {
        Long userId = communityPostMapper.findUserIdById(id);
        userDataService.increaseManuallyExceptionCount(userId);
        communityPostMapper.updateAuditStatus(id, AuditStatusEnum.REJECT.getCode());
    }

    public void manuallyApprove(Long id) {
        int affectRow = communityPostMapper.updateAuditStatus(id, AuditStatusEnum.APPROVED.getCode());
        if (affectRow > 0) {
            // 增加用户的发帖数量
            Long userId = communityPostMapper.findUserIdById(id);
            userDataService.increasePostCount(userId);
        }
    }

    public Long countPostByDate(Date startDate, Date endDate) {
        CommunityPostExample example = new CommunityPostExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate);
        return communityPostMapper.countByExample(example);
    }

    public List<CommunityPost> queryCollectViaUserIdByPage(Long userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return communityPostMapper.queryCollectViaUserIdByPage(userId);
    }

    public void syncClick(Long id){
        CompletableFuture.runAsync(()->{
            communityPostMapper.increaseVisitCount(id);
        });
    }

    /**
     * 推荐帖子列表
     * 排序规则：帖子浏览人数，帖子已发布时间，帖子回帖数，点赞数，收藏数，分享数
     */
    public List<CommunityPost> queryRecommendPostList(int pageNum, int pageSize) {

        return null;
    }
}
