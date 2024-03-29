package com.smart.home.modules.community.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.smart.home.cloud.qcloud.auditor.ContentAuditor;
import com.smart.home.cloud.qcloud.auditor.ContentAuditorResult;
import com.smart.home.cloud.qcloud.auditor.ImageAuditor;
import com.smart.home.cloud.qcloud.auditor.ImageAuditorResult;
import com.smart.home.cloud.qcloud.enums.ContentAuditorEvilEnum;
import com.smart.home.cloud.qcloud.enums.ContentAuditorSuggestionEnum;
import com.smart.home.cloud.qcloud.enums.ImageAuditorSuggestionEnum;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.enums.RecordStatusEnum;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.enums.AutoAuditFlagEnum;
import com.smart.home.enums.EsSaveTypeEnum;
import com.smart.home.es.bean.CommunityPostBean;
import com.smart.home.es.common.EsConstant;
import com.smart.home.es.dao.CommunityPostRepository;
import com.smart.home.es.service.EsCommonService;
import com.smart.home.modules.community.dao.CommunityMapper;
import com.smart.home.modules.community.dao.CommunityPostReplyMapper;
import com.smart.home.modules.community.dao.CommunityUserMappingMapper;
import com.smart.home.modules.community.dto.CommunityPostDTO;
import com.smart.home.modules.community.dao.CommunityPostMapper;
import com.smart.home.modules.community.entity.*;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.modules.system.service.SysFileService;
import com.smart.home.modules.user.dao.UserCollectMapper;
import com.smart.home.modules.user.dao.UserCommunityAuthMapper;
import com.smart.home.modules.user.dao.UserDataMapper;
import com.smart.home.modules.user.dto.UserDataDTO;
import com.smart.home.modules.user.entity.UserCommunityAuth;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import com.smart.home.modules.user.service.UserFocusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author jason
 **/
@Slf4j
@Service
public class CommunityPostService {

    @Resource
    CommunityPostMapper communityPostMapper;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserDataService userDataService;
    @Resource
    private UserDataMapper userDataMapper;
    @Resource
    private CommunityMapper communityMapper;
    @Autowired
    private SysFileService sysFileService;
    @Resource
    private UserCommunityAuthMapper userCommunityAuthMapper;
    @Autowired
    private EsCommonService esCommonService;
    @Resource
    private CommunityPostReplyMapper communityPostReplyMapper;
    @Resource
    private UserCollectMapper userCollectMapper;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private UserFocusService userFocusService;
    @Resource
    private CommunityUserMappingMapper communityUserMappingMapper;
    @Autowired
    private CommunityPostRepository communityPostRepository;


    public void create(CommunityPost communityPost) {
        CommunityPostDTO communityPostDTO = new CommunityPostDTO();
        communityPost.setTopFlag(YesNoEnum.NO.getCode());
        communityPost.setBoutiqueFlag(YesNoEnum.NO.getCode());
        communityPost.setLikeCount(0);
        communityPost.setReportCount(0);
        communityPost.setAuditStatus(AuditStatusEnum.WAIT_AUDIT.getCode());
        communityPost.setRevision(0);
        communityPost.setStampCount(0);
        communityPost.setCollectCount(0);
        communityPost.setCreatedTime(new Date());
        BeanUtils.copyProperties(communityPost, communityPostDTO);
        executeSavePost(communityPostDTO, 0);
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
        List<CommunityPost> communityPosts = communityPostMapper.selectByIds(idList);
        if (CollUtil.isEmpty(communityPosts)) {
            return;
        }
        for (CommunityPost communityPost : communityPosts) {
            // 软删除
            communityPostMapper.updateState(communityPost.getId(), RecordStatusEnum.DELETE.getStatus());
            if (communityPost.getState() == 1) {
                esCommonService.deleteOne(EsConstant.communityPostIndex, EsConstant.communityPost, communityPost.getId());
            }
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

    @Transactional(rollbackFor = RuntimeException.class)
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

    public List<CommunityPost> queryViaUserIdByPage(Long userId, int pageNum, int pageSize, Long loginUserId) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommunityPost> list = communityPostMapper.queryViaUserIdByPage(userId, loginUserId);
        for (CommunityPost communityPost : list) {
            long replyCount = communityPostReplyMapper.countByUserId(loginUserId, communityPost.getId());
            if (replyCount > 0) {
                communityPost.setReplyFlag(YesNoEnum.YES.getCode());
            } else {
                communityPost.setReplyFlag(YesNoEnum.NO.getCode());
            }
        }
        return list;
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
        communityPostMapper.updateAuditStatusState(id, AuditStatusEnum.REJECT.getCode(), 0);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void manuallyApprove(Long id) {
        int affectRow = communityPostMapper.updateAuditStatusState(id, AuditStatusEnum.APPROVED.getCode(), 1);
        CommunityPost communityPost = communityPostMapper.selectByPrimaryKey(id);
        CommunityPostBean communityPostBean = new CommunityPostBean();
        communityPostBean.setSaveType(EsSaveTypeEnum.COMMUNITY_POST.getType());
        BeanUtils.copyProperties(communityPost, communityPostBean);
        communityPostBean.setCreatedTime(new Date());
        if (communityPost.getCommunity() != null) {
            Community community = communityMapper.selectByPrimaryKey(communityPost.getCommunity());
            communityPostBean.setCommunityTitle(community.getTitle());
        }
//        CommunityPostBean bean = communityPostRepository.save(communityPostBean);
        esCommonService.insertOrUpdateOne(EsConstant.communityPostIndex, EsConstant.communityPost, communityPost.getId(), communityPostBean);
        log.info("es insertOrUpdateOne 完毕：{}", communityPost.getId());
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
     * 排序规则：帖子最后更新时间 =
     * 有回复：最新回复时间
     * 没回复：按发帖时间
     * 如果用户没有加入任何社区或未登录：展示所有社区的帖子，如果有至少加入一个社区，只展示加入社区的帖子
     */
    public List<CommunityPostDTO> queryRecommendPostList(Long userId, int pageNum, int pageSize) {
        List<CommunityPost> communityPosts = queryReplySortPost(userId, pageNum, pageSize);
        List<CommunityPostDTO> communityPostDTOS = transCommunityPostDTO(communityPosts);
        return toPageList((Page) communityPosts, communityPostDTOS);
    }

    private List<CommunityPost> queryReplySortPost(Long userId, int pageNum, int pageSize){
        List<CommunityPost> sortPostList = null;
        List<Integer> communityIds = null;
        if (userId > 0) {
            //已登陆 查询已加入社区，提取社区作为条件
            List<CommunityUserMapping> communityUserMappings = communityUserMappingMapper.selectByUserId(userId);
            if (CollUtil.isNotEmpty(communityUserMappings)) {
                communityIds = communityUserMappings.parallelStream().map(CommunityUserMapping::getCommunityId).collect(Collectors.toList());
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        //评论要聚合，否则查帖子时数据会忽多忽少
        List<Long> postIds = communityPostReplyMapper.selectOrderById(communityIds);
        if (CollUtil.isEmpty(postIds)) {
            //没回复
            PageHelper.startPage(pageNum, pageSize);
            sortPostList = communityPostMapper.selectOrderById(communityIds);
        }else {
            //有回复，不满足10条也不用给增加，下一页自然会走无回复的逻辑，不论是否满足都需要查询帖子数量，和总数，只是条件上取自回复中的帖子id
            PageHelper.startPage(pageNum, pageSize);
            sortPostList = communityPostMapper.selectByIds(postIds);
        }
        return sortPostList;
    }

    private <T> Page<T> toPageList(Page pager, List<T> list) {
        Page<T> page = new Page();
        page.addAll(list);
        BeanCopyUtils.copyProperties(pager, page);
        return page;
    }

    /**
     * 热议
     * 排序规则：发布时间在7天内的帖子，跟帖数量倒序
     */
    public List<CommunityPostDTO> queryHotPostList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<CommunityPost> communityPosts = communityPostMapper.getHotPost(DateUtil.offsetDay(DateUtil.date(), -7), DateUtil.date());
        List<CommunityPostDTO> communityPostDTOS = transCommunityPostDTO(communityPosts);
        return toPageList((Page) communityPosts, communityPostDTOS);
    }

    /**
     * 社区详情-帖子列表
     * 搜索规则：
     * 帖子最后更新时间 =
     * 有回复：最新回复时间
     * 没回复：按发帖时间
     * 注意帖子置顶优先
     */
    public List<CommunityPostDTO> queryCommunityDetailPostList(Long communityId, Integer boutiqueFlag, int pageNum, int pageSize){
        List<CommunityPost> sortPostList = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Long> postIds = communityPostReplyMapper.selectOrderById(null);
        if (CollUtil.isEmpty(postIds)) {
            //没回复
            PageHelper.startPage(pageNum, pageSize);
            sortPostList = communityPostMapper.selectOrderById(null);
        }else {
            //有回复，不满足10条也不用给增加，下一页自然会走无回复的逻辑，不论是否满足都需要查询帖子数量，和总数，只是条件上取自回复中的帖子id
            PageHelper.startPage(pageNum, pageSize);
            sortPostList = communityPostMapper.selectByIds(postIds);
        }
        List<CommunityPostDTO> communityPostDTOS = transCommunityPostDTO(sortPostList);
        return toPageList((Page) sortPostList, communityPostDTOS);
    }

    /**
     * 帖子详情
     * 登录用户
     */
    public CommunityPostDTO queryDetailWithLogin(Long id, Long userId){
        CommunityPostDTO communityPostDTO = communityPostMapper.selectByIdEffetive(id, userId);
        if (communityPostDTO == null) {
            return null;
        }
        if (communityPostDTO.getState() == 1) {
            //增加一次浏览
            syncClick(id);
        }
        //处理图片
        if (StrUtil.isNotEmpty(communityPostDTO.getImages())) {
            communityPostDTO.setImagesList(JSON.parseArray(communityPostDTO.getImages(), String.class));
        }
        return communityPostDTO;
    }

    public CommunityPostDTO queryDetailNotLogin(Long id){
        CommunityPostDTO communityPostDTO = communityPostMapper.selectById(id);
        if (communityPostDTO == null) {
            return null;
        }
        if (communityPostDTO.getState() == 1) {
            syncClick(id);
        }
        //处理图片
        if (StrUtil.isNotEmpty(communityPostDTO.getImages())) {
            communityPostDTO.setImagesList(JSON.parseArray(communityPostDTO.getImages(), String.class));
        }
        return communityPostDTO;
    }

    /**
     * 保存
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Long executeSavePost(CommunityPostDTO communityPostDTO, int ifSend){
        UserCommunityAuth userCommunityAuth = userCommunityAuthMapper.selectByUserId(communityPostDTO.getUserId());
        if (userCommunityAuth == null) {
            throw new ServiceException("您无社区权限");
        }
        if (userCommunityAuth.getBlackFlag() == 1) {
            throw new ServiceException("您已被封禁");
        }
        if (userCommunityAuth.getSpeakFlag() == 1) {
            throw new ServiceException("您已被禁言");
        }

        CommunityPost communityPost = new CommunityPost();
        communityPost.setUserId(communityPostDTO.getUserId());
        communityPost.setCommunity(communityPostDTO.getCommunity());
        communityPost.setTitle(communityPostDTO.getTitle());
        communityPost.setContents(communityPostDTO.getContents());
        communityPost.setImages(communityPostDTO.getImages());
        communityPost.setTopFlag(communityPostDTO.getTopFlag());
        communityPost.setBoutiqueFlag(communityPostDTO.getBoutiqueFlag());
        communityPost.setCommentFlag(communityPostDTO.getCommentFlag());
        communityPost.setLikeCount(0);
        communityPost.setRevision(1);
        communityPost.setCreatedTime(DateUtil.date());
        communityPost.setStampCount(0);
        communityPost.setCollectCount(0);
        communityPost.setState(0);
        if (StrUtil.isNotEmpty(communityPostDTO.getRemark()) && communityPostDTO.getRemark().length() > 100) {
            communityPost.setRemark(StrUtil.sub(communityPostDTO.getRemark(), 0, 100) + "……");
        }else {
            communityPost.setRemark(communityPostDTO.getRemark());
        }
        if (communityPostDTO.getId() != null && communityPostDTO.getId() != 0) {
            communityPost.setId(communityPostDTO.getId());
            communityPostMapper.updateByPrimaryKeySelective(communityPost);
            return communityPostDTO.getId();
        }else {
            communityPostMapper.insertSelective(communityPost);
            if (ifSend == 1) {
                //直接发布
                release(communityPost.getId());
            }
            return communityPost.getId();
        }
    }

    /**
     * 发布帖子
     */
    public void release(Long id){
        CommunityPost communityPost = communityPostMapper.selectByPrimaryKey(id);
        if (communityPost == null) {
            throw new ServiceException("没有此帖子");
        }
        if (communityPost.getState() != 0) {
            throw new ServiceException("该帖子非草稿状态，请刷新重试");
        }
        int i = communityPostMapper.updateState(id, 1);
        if (i > 0) {
            // 同步图片
            List<String> images = JSON.parseArray(communityPost.getImages(), String.class);
            sysFileService.syncImageFileList(images);
            processAutoAudit(communityPost.getUserId(), id, communityPost.getContents(), images);
        }
    }

    /**
     * 我的分页帖子
     */
    public List<CommunityPost> page(Long userId, Integer state, int pageNum, int pageSize){
        CommunityPostExample example = new CommunityPostExample();
        CommunityPostExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (state != null) {
            criteria.andStateEqualTo(state);
        }
        example.orderBy("id desc");
        PageHelper.startPage(pageNum, pageSize);
        List<CommunityPost> communityPosts = communityPostMapper.selectByExample(example);
        return communityPosts;
    }


    private void processAutoAudit(Long loginUserId, long id, String details, List<String> imageList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean contentPass = false;
                ContentAuditorResult contentAuditorResult = ContentAuditor.auditorResult(details);
                if (contentAuditorResult == null) {
                    // 机审文本失败，进入人工审核
                    communityPostMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                    return;
                }
                if (contentAuditorResult.getContentAuditorEvilEnum() == ContentAuditorEvilEnum.NORMAL) {
                    // 机审成功， 标记为成功， 还要等图片审核结果
                    contentPass = true;
                }
                // 图片审核，如果有的话
                boolean hasImage = false;
                ImageAuditorResult imageAuditorResult = null;
                if (!CollectionUtils.isEmpty(imageList)) {
                    hasImage = true;
                    for (String image : imageList) {
                        imageAuditorResult = ImageAuditor.auditorResult(image);
                        if (imageAuditorResult == null) {
                            break;
                        }
                        if (imageAuditorResult.getImageAuditorSuggestionEnum() != ImageAuditorSuggestionEnum.Pass) {
                            break;
                        }
                    }
                    if (Objects.isNull(imageAuditorResult)) {
                        // 机审图片失败，进入人工审核
                        communityPostMapper.updateAutoAuditFlagImage(id, AutoAuditFlagEnum.ERROR.getCode(), "请求云服务失败");
                        return;
                    }
                    if (imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Pass) {
                        // 图片机审成功 加上 文本审核成功
                        if (contentPass) {
                            if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                                communityPostMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                                return;
                            } else {
                                // 还是要进入人工审核的
                                communityPostMapper.updateAutoAuditFlagImage(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步人工审核");
                                return;
                            }
                        }
                    }
                } else {
                    // 没有图片的话判断机审结果，成功的话直接更新成成功
                    if (contentPass) {
                        if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                            communityPostMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                            return;
                        } else {
                            // 还是要进入人工审核的
                            communityPostMapper.updateAutoAuditFlagImage(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步人工审核");
                            return;
                        }
                    }
                }

                // 机器审核不通过，文本异常
                // 先看看建议通过不通过
                ContentAuditorSuggestionEnum contentAuditorSuggestionEnum = contentAuditorResult.getContentAuditorSuggestionEnum();
                if (contentAuditorSuggestionEnum == null
                        || ContentAuditorSuggestionEnum.Block == contentAuditorSuggestionEnum
                        || ContentAuditorSuggestionEnum.Review == contentAuditorSuggestionEnum) {
                    communityPostMapper.updateAutoAuditFlag(id, AutoAuditFlagEnum.CONTENT_EXCEPTION.getCode(), contentAuditorResult.getContentAuditorEvilTypeEnum().getDesc());
                    List<String> keywordsList = contentAuditorResult.getKeywordsList();
                    if (!CollectionUtils.isEmpty(keywordsList)) {
                        communityPostMapper.updateHitSensitiveCount(id, keywordsList.size());
                        // 增加用户的总敏感词数量
                        userDataService.increaseHitSensitiveCount(loginUserId, keywordsList.size());
                    }
                    userDataService.increaseTextExceptionCount(loginUserId);
                } else if (ContentAuditorSuggestionEnum.Normal == contentAuditorSuggestionEnum) {
                    // 正常，视为通过
                    contentPass = true;
                }
                if (hasImage) {
                    // 机器审核不通过，图片异常
                    if (imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Block
                            || imageAuditorResult.getImageAuditorSuggestionEnum() == ImageAuditorSuggestionEnum.Review) {
                        String reason = imageAuditorResult.getImageAuditorLabelEnum().getDesc();
                        if (contentPass) {
                            communityPostMapper.updateAutoAuditFlagImage(id, AutoAuditFlagEnum.IMAGE_EXCEPTION.getCode(), reason);
                        } else {
                            communityPostMapper.updateAutoAuditFlagImage(id, AutoAuditFlagEnum.IMAGE_AND_CONTENT_EXCEPTION.getCode(), reason);
                        }
                        userDataService.increaseImageExceptionCount(loginUserId);
                    }
                } else if (contentPass) {
                    if ("1".equals(sysDictService.queryValueByDictCode("switch.content.audit.autoPass"))) {
                        communityPostMapper.updateAutoAuditFlagAndAuditFlag(id, AutoAuditFlagEnum.APPROVE.getCode(), AuditStatusEnum.APPROVED.getCode());
                        return;
                    } else {
                        // 还是要进入人工审核的
                        communityPostMapper.updateAutoAuditFlagImage(id, AutoAuditFlagEnum.ERROR.getCode(), "需要进一步人工审核");
                        return;
                    }
                }
            }
        }).start();
    }



    private List<CommunityPostDTO> transCommunityPostDTO(List<CommunityPost> list){
        if (CollUtil.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        List<CommunityPostDTO> communityPostDTOS = Lists.newArrayListWithCapacity(list.size());
        list.stream().forEach(x->{
            CommunityPostDTO communityPostDTO = new CommunityPostDTO();
            BeanUtils.copyProperties(x, communityPostDTO);
            if (StrUtil.isNotEmpty(x.getImages())) {
                List<String> strings = JSON.parseArray(x.getImages(), String.class);
                communityPostDTO.setImagesList(strings);
            }
            //查用户
            UserDataDTO userDataDTO = userDataMapper.getByUserId(x.getUserId());
            if (userDataDTO != null) {
                communityPostDTO.setNickname(userDataDTO.getNickname());
                communityPostDTO.setHeadUrl(userDataDTO.getHeadUrl());
                communityPostDTO.setUserLevel(userDataDTO.getUserLevel());
                communityPostDTO.setUserRemark(userDataDTO.getRemark());
                communityPostDTO.setUserId(x.getUserId());
            }
            //查社区
            Community community = communityMapper.selectByPrimaryKey(x.getCommunity());
            if (community != null) {
                communityPostDTO.setCommunityTitle(community.getTitle());
            }
            communityPostDTOS.add(communityPostDTO);
        });
        return communityPostDTOS;
    }

    public boolean hasFocusThisAuthor(Long longUserId, Long authorId) {
        return userFocusService.queryFocusFlag(longUserId, authorId) == YesNoEnum.YES.getCode();
    }
}
