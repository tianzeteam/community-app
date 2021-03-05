package com.smart.home.modules.community.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.community.dao.CommunityPostReplyMapper;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.entity.CommunityPostReplyExample;
import com.smart.home.modules.product.entity.ProductCommentExample;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Date;

/**
 * @author jason
 **/
@Service
public class CommunityPostReplyService {

    @Resource
    CommunityPostReplyMapper communityPostReplyMapper;
    @Resource
    CommunityPostMapper communityPostMapper;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;

    public int create(CommunityPostReply communityPostReply) {
        communityPostReply.setCreatedTime(new Date());
        return communityPostReplyMapper.insertSelective(communityPostReply);
    }

    public int update(CommunityPostReply communityPostReply) {
        return communityPostReplyMapper.updateByPrimaryKeySelective(communityPostReply);
    }

    public int deleteById(Long id) {
        return communityPostReplyMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(List<Long> idList) {
        for (Long id : idList) {
            communityPostReplyMapper.deleteByPrimaryKey(id);
        }
    }

    public List<CommunityPostReply> selectByPage(CommunityPostReply communityPostReply, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        CommunityPostReplyExample.Criteria criteria = example.createCriteria();
        if (communityPostReply.getUserId() != null) {
            criteria.andUserIdEqualTo(communityPostReply.getUserId());
        }
        example.setOrderByClause("created_time desc");
        List<CommunityPostReply> list = communityPostReplyMapper.selectByExample(example);
        for (CommunityPostReply postReply : list) {
            postReply.setNickName(userAccountService.findNicknameByUserId(postReply.getUserId()));
        }
        return list;
    }

    public CommunityPostReply findById(Long id) {
        CommunityPostReply communityPostReply = communityPostReplyMapper.selectByPrimaryKey(id);
        return communityPostReply;
    }

    public void increaseLikeCount(Long id) {
        communityPostReplyMapper.increaseLikeCount(id);
    }

    public void decreaseLikeCount(Long id) {
        communityPostReplyMapper.decreaseLikeCount(id);
    }

    public void increaseStampCount(Long id) {
        communityPostReplyMapper.increaseStampCount(id);
    }

    public void decreaseStampCount(Long id) {
        communityPostReplyMapper.decreaseStampCount(id);
    }

    public Long countWaitAudit() {
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        example.createCriteria().andAuditFlagEqualTo(AuditStatusEnum.WAIT_AUDIT.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.WAIT_AUDIT.getCode());
        return communityPostReplyMapper.countByExample(example);
    }

    public Long countTextException() {
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return communityPostReplyMapper.countByExample(example);
    }

    public Long countImageException() {
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        example.createCriteria()
                .andAutoAuditFlagIn(Arrays.asList(AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode()));
        return communityPostReplyMapper.countByExample(example);
    }

    public Long countHasReport() {
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        example.createCriteria().andReportCountGreaterThan(0);
        return communityPostReplyMapper.countByExample(example);
    }

    public Long countHitSensitive() {
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        example.createCriteria().andHitSensitiveCountGreaterThan(0);
        return communityPostReplyMapper.countByExample(example);
    }

    public Long countTotalNormal() {
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        example.createCriteria().andAuditFlagEqualTo(AuditStatusEnum.APPROVED.getCode())
                .andAutoAuditFlagEqualTo(AutoAuditFlagEnum.APPROVE.getCode());
        return communityPostReplyMapper.countByExample(example);
    }

    public void manuallyReject(Long id) {
        communityPostReplyMapper.manuallyReject(id, AuditStatusEnum.REJECT.getCode());
    }

    public void manuallyApprove(Long id) {
        int affectRow = communityPostReplyMapper.manuallyReject(id, AuditStatusEnum.APPROVED.getCode());
        if (affectRow > 0) {
            CommunityPostReply communityPostReply = findById(id);
            communityPostMapper.increaseReplyCount(communityPostReply.getPostId());
            userDataService.increaseReplyCount(communityPostReply.getUserId());
        }
    }
}
