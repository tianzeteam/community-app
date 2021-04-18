package com.smart.home.modules.user.dao;

import java.util.List;

import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.entity.UserAccountExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.checkerframework.checker.nullness.qual.PolyRaw;

@Mapper
public interface UserAccountMapper {
    long countByExample(UserAccountExample example);

    int deleteByExample(UserAccountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserAccount record);

    int insertSelective(UserAccount record);

    List<UserAccount> selectByExample(UserAccountExample example);

    UserAccount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    int updateByExample(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    int updateByPrimaryKeySelective(UserAccount record);

    int updateByPrimaryKey(UserAccount record);

    int updateNewToken(@Param("id") Long id,@Param("token") String token);

    int updateHeadUrl(@Param("userId") Long userId,@Param("headUrl") String headUrl);

    String findPermitsById(@Param("id") Long id);

    void savePermits(@Param("id") Long id,@Param("permitJson") String permitJson);

    List<UserAccount> selectByIdAndNicknameAndPermit(@Param("idList") List<Long> idList,@Param("nickName") String nickName,@Param("permitCode") String permitCode);

    String findNicknameByUserId(@Param("userId") Long userId);

    int clearToken(@Param("id") Long id);

}