package com.smart.home.modules.community.dao;

import com.smart.home.modules.community.dto.CommunityPostDTO;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.entity.CommunityPostExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityPostMapper {
    long countByExample(CommunityPostExample example);

    int deleteByExample(CommunityPostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommunityPost record);

    int insertSelective(CommunityPost record);

    List<CommunityPost> selectByExample(CommunityPostExample example);

    CommunityPost selectByPrimaryKey(Long id);

    List<CommunityPost> selectByIds(@Param("list") List<Long> list);

    List<CommunityPost> selectOrderById(@Param("list") List<Integer> list);

    int updateByExampleSelective(@Param("record") CommunityPost record, @Param("example") CommunityPostExample example);

    int updateByExample(@Param("record") CommunityPost record, @Param("example") CommunityPostExample example);

    int updateByPrimaryKeySelective(CommunityPost record);

    int updateByPrimaryKey(CommunityPost record);

    int increaseLikeCount(@Param("id") Long id);

    int decreaseLikeCount(@Param("id") Long id);

    int increaseStampCount(@Param("id") Long id);

    int decreaseStampCount(@Param("id") Long id);

    int increaseCollectCount(@Param("id") Long id);

    int decreaseCollectCount(@Param("id") Long id);

    long countByCommunityId(@Param("communityId") int communityId);

    int updateState(@Param("id") Long id, @Param("state") int state);

    int increaseReportCount(@Param("id") Long id);

    int updateBoutiqueFlag(@Param("id") Long id,@Param("flag") int flag);

    int updateTopFlag(@Param("id") Long id,@Param("flag") int flag);

    List<CommunityPost> queryViaUserIdByPage(@Param("userId") Long userId, @Param("loginUserId") Long loginUserId);

    int updateAutoAuditFlag(@Param("id") Long id,@Param("autoAuditFlag") int autoAuditFlag, @Param("reason") String reason);

    int updateAutoAuditFlagImage(@Param("id") Long id, @Param("autoAuditFlag") int autoAuditFlag, @Param("reason") String reason);

    int updateAutoAuditFlagAndAuditFlag(@Param("id") Long id,@Param("autoAuditFlag") int autoAuditFlag,@Param("auditFlag") int auditFlag);

    int updateHitSensitiveCount(@Param("id") long id,@Param("count") int count);

    int updateAuditStatusState(@Param("id") Long id,@Param("flag") int flag, @Param("state") int state);

    Long findUserIdById(@Param("id") Long id);

    int increaseReplyCount(@Param("id") Long id);

    List<CommunityPost> queryCollectViaUserIdByPage(@Param("userId") Long userId);

    int increaseVisitCount(Long id);

    List<CommunityPost> getSortRecommend();

    List<CommunityPost> getHotPost(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<CommunityPost> getCommunityDetail(@Param("communityId") Long communityId, @Param("boutiqueFlag") Integer boutiqueFlag);

    CommunityPostDTO selectByIdEffetive(@Param("id") Long id, @Param("userId") Long userId);

    CommunityPostDTO selectById(@Param("id") Long id);

}