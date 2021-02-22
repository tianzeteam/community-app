package com.smart.home.modules.system.dao;

import com.smart.home.modules.system.entity.SysConfig;
import com.smart.home.modules.system.entity.SysConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysConfigMapper {
    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    long countByExample(SysConfigExample example);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int deleteByExample(SysConfigExample example);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int insert(SysConfig record);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int insertSelective(SysConfig record);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    List<SysConfig> selectByExample(SysConfigExample example);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    SysConfig selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int updateByExampleSelective(@Param("record") SysConfig record, @Param("example") SysConfigExample example);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int updateByExample(@Param("record") SysConfig record, @Param("example") SysConfigExample example);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int updateByPrimaryKeySelective(SysConfig record);

    /**
     *
     * @mbg.generated Thu Feb 18 00:33:46 CST 2021
     */
    int updateByPrimaryKey(SysConfig record);

    String findValueByKey(@Param("key") String key);
}