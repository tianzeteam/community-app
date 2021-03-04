package com.smart.home.modules.system.dao;

import com.smart.home.modules.system.entity.SysFile;
import com.smart.home.modules.system.entity.SysFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysFileMapper {
    long countByExample(SysFileExample example);

    int deleteByExample(SysFileExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysFile record);

    int insertSelective(SysFile record);

    List<SysFile> selectByExample(SysFileExample example);

    SysFile selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysFile record, @Param("example") SysFileExample example);

    int updateByExample(@Param("record") SysFile record, @Param("example") SysFileExample example);

    int updateByPrimaryKeySelective(SysFile record);

    int updateByPrimaryKey(SysFile record);

    int updateSyncFlag(@Param("fileName") String fileName,@Param("flag") int flag);

    int updateSyncFlagList(@Param("fileNameList") List<String> fileNameList ,@Param("flag") int flag);
}