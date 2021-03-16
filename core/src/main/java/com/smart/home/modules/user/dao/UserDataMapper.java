package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.dto.UserDataDTO;
import com.smart.home.modules.user.entity.UserData;
import com.smart.home.modules.user.entity.UserDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDataMapper {
    long countByExample(UserDataExample example);

    int deleteByExample(UserDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserData record);

    int insertSelective(UserData record);

    List<UserData> selectByExample(UserDataExample example);

    UserData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserData record, @Param("example") UserDataExample example);

    int updateByExample(@Param("record") UserData record, @Param("example") UserDataExample example);

    int updateByPrimaryKeySelective(UserData record);

    int updateByPrimaryKey(UserData record);

    int updateSign(@Param("userId") Long userId,@Param("sign") String sign);

    int increaseLikeCount(@Param("userId") Long userId);

    int decreaseLikeCount(@Param("userId") Long userId);

    UserData selectByIdOrNickname(@Param("userId") Long userId,@Param("nickName") String nickName);

    int increaseByReportCount(@Param("userId") Long userId,@Param("reasonRate") String reasonRate);

    String queryReportReasonRate(@Param("userId") Long userId);

    int increaseHitSensitiveCount(@Param("userId") Long userId,@Param("size") int size);

    int increasePostCount(@Param("userId") Long userId);

    int increaseCommentCount(@Param("userId") Long userId);

    int increaseEvaluateCount(@Param("userId") Long userId);

    int increaseReplyCount(@Param("userId") Long userId);

    int increaseTextExceptionCount(@Param("userId") Long userId);

    int increaseImageExceptionCount(@Param("userId") Long userId);

    int increaseManuallyExceptionCount(@Param("userId") Long userId);

    //查询用户信息以及头像
    UserDataDTO getByUserId(Long userId);

    List<UserDataDTO> selectByUserIds(@Param("ids") List<Long> ids);

}