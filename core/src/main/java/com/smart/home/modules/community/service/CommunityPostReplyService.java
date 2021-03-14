package com.smart.home.modules.community.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.smart.home.cloud.qcloud.auditor.ContentAuditor;
import com.smart.home.cloud.qcloud.auditor.ContentAuditorResult;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.po.UserIdAndCategoryPO;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.community.dao.CommunityPostReplyMapper;
import com.smart.home.modules.community.dto.CommunityPostReplyDTO;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostReply;
import com.smart.home.modules.community.entity.CommunityPostReplyExample;
import com.smart.home.modules.product.entity.ProductCommentExample;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

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

    public CommunityPostReply getById(Long id){
        CommunityPostReply communityPostReply = communityPostReplyMapper.selectByPrimaryKey(id);
        return communityPostReply;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void create(Long loginUserId, Long postId, String contents) {
        CommunityPost communityPost = communityPostMapper.selectByPrimaryKey(postId);
        if (communityPost == null) {
            return;
        }

        CommunityPostReply communityPostReply = new CommunityPostReply();
        communityPostReply.setUserId(loginUserId);
        communityPostReply.setPostId(postId);
        communityPostReply.setReplyType(0);
        communityPostReply.setContents(contents);
        communityPostReply.setCreatedTime(DateUtil.date());
        if (communityPost.getUserId().equals(loginUserId)) {
            communityPostReply.setAuthorFlag(YesNoEnum.YES.getCode());
        }else {
            communityPostReply.setAuthorFlag(YesNoEnum.NO.getCode());
        }
        communityPostReplyMapper.insertSelective(communityPostReply);
        long id = communityPostReply.getId();
        processAutoAudit(id, postId, contents, loginUserId);
    }

    private void processAutoAudit(long id, Long postId, String contents, Long loginUserId) {
        CompletableFuture.runAsync(()->{
            ContentAuditorResult contentAuditorResult = ContentAuditor.auditorResult(contents);
            if (contentAuditorResult == null) {
                // 机审失败，进入人工审核
                communityPostReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                return;
            }
            if (contentAuditorResult.getContentAuditorEvilEnum() == ContentAuditorEvilEnum.NORMAL) {
                // 机审成功， 直接通过
                communityPostReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                // 增加一次评论数量
                communityPostMapper.increaseReplyCount(postId);
                return;
            }
            // 机器审核不通过，文本异常
            // 先看看建议通过不通过
            ContentAuditorSuggestionEnum contentAuditorSuggestionEnum = contentAuditorResult.getContentAuditorSuggestionEnum();
            if (contentAuditorSuggestionEnum == null
                    || ContentAuditorSuggestionEnum.Block == contentAuditorSuggestionEnum
                    || ContentAuditorSuggestionEnum.Review == contentAuditorSuggestionEnum) {
                communityPostReplyMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), contentAuditorResult.getContentAuditorEvilTypeEnum().getDesc());
                List<String> keywordsList = contentAuditorResult.getKeywordsList();
                if (!CollectionUtils.isEmpty(keywordsList)) {
                    communityPostReplyMapper.updateHitSensitiveCount(id, keywordsList.size());
                    // 增加用户的总敏感词数量
                    userDataService.increaseHitSensitiveCount(loginUserId, keywordsList.size());
                }
                userDataService.increaseTextExceptionCount(loginUserId);
                return;
            }
            if (ContentAuditorSuggestionEnum.Normal == contentAuditorSuggestionEnum) {
                // 正常，视为通过
                communityPostReplyMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                // 增加一次评论数量
                communityPostMapper.increaseReplyCount(postId);
            }
        });
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

    @Transactional(rollbackFor = RuntimeException.class)
    public void manuallyReject(Long id) {
        Long userId = communityPostReplyMapper.findUserIdById(id);
        userDataService.increaseManuallyExceptionCount(userId);
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

    public Long countReplyByDate(Date startDate, Date endDate) {
        CommunityPostReplyExample example = new CommunityPostReplyExample();
        example.createCriteria().andCreatedTimeBetween(startDate, endDate);
        return communityPostReplyMapper.countByExample(example);
    }

    public List<CommunityPostReply> queryViaUserIdByPageWhenLogin(Long userId, int pageNum, int pageSize, Long loginUserId) {
        PageHelper.startPage(pageNum, pageSize);
        return communityPostReplyMapper.queryViaUserIdByPageWhenLogin(userId, loginUserId);
    }

    /**
     * 获取评论 回复
     */
    public List<CommunityPostReply> queryPageById(CommunityPostReplyDTO communityPostReplyDTO, int pageNum, int pageSize, Boolean ifLogin){
        List<CommunityPostReply> communityPostReplies = null;
        if (ifLogin) {
            //登录
            PageHelper.startPage(pageNum, pageSize);
            communityPostReplies = communityPostReplyMapper.queryByIdWhenLogin(communityPostReplyDTO);
        }else {
            //未登录
            PageHelper.startPage(pageNum, pageSize);
            communityPostReplies = communityPostReplyMapper.queryByPageNotLogin(communityPostReplyDTO);
        }
        if (CollUtil.isEmpty(communityPostReplies)) {
            return Collections.EMPTY_LIST;
        }
        if (communityPostReplyDTO.getReplyType() != 0) {
            //一级无回复，不需要查回复用户
            communityPostReplies.stream().forEach(x->{
                if (x.getToUserId() != null) {
                    x.setToUserNickName(userAccountService.findNicknameByUserId(x.getToUserId()));
                }
            });
        }
        return communityPostReplies;
    }

    /**
     * 根据评论id查找二级回复
     */
    public List<CommunityPostReply> queryPageByCommentId(CommunityPostReplyDTO communityPostReplyDTO, int pageNum, int pageSize, Boolean ifLogin){
        List<CommunityPostReply> communityPostReplies = null;
        if (ifLogin) {
            //登录
            PageHelper.startPage(pageNum, pageSize);
            communityPostReplies = communityPostReplyMapper.queryByCommentIdWhenLogin(communityPostReplyDTO);
        }else {
            //未登录
            PageHelper.startPage(pageNum, pageSize);
            communityPostReplies = communityPostReplyMapper.queryByCommentIdNotLogin(communityPostReplyDTO);
        }
        if (CollUtil.isEmpty(communityPostReplies)) {
            return Collections.EMPTY_LIST;
        }
        if (communityPostReplyDTO.getReplyType() != 0) {
            //一级无回复，不需要查回复用户
            communityPostReplies.stream().forEach(x->{
                if (x.getToUserId() != null) {
                    x.setToUserNickName(userAccountService.findNicknameByUserId(x.getToUserId()));
                }
            });
        }
        return communityPostReplies;

    }

}
