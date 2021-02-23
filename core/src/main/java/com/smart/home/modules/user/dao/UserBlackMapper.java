package com.smart.home.modules.user.dao;

import com.smart.home.modules.user.entity.UserBlack;
import com.smart.home.modules.user.entity.UserBlackExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserBlackMapper {
    long countByExample(UserBlackExample example);

    int deleteByExample(UserBlackExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBlack record);

    int insertSelective(UserBlack record);

    List<UserBlack> selectByExample(UserBlackExample example);

    UserBlack selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBlack record, @Param("example") UserBlackExample example);

    int updateByExample(@Param("record") UserBlack record, @Param("example") UserBlackExample example);

    int updateByPrimaryKeySelective(UserBlack record);

    int updateByPrimaryKey(UserBlack record);
}