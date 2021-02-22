package com.smart.home.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.cache.SysConfigCache;
import com.smart.home.common.enums.APIResponseCodeEnum;
import com.smart.home.modules.system.dao.SysConfigMapper;
import com.smart.home.modules.system.entity.SysConfig;
import com.smart.home.modules.system.entity.SysConfigExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Service
public class SysConfigService {

    @Resource
    private SysConfigMapper sysConfigMapper;

    public int insert(SysConfig sysConfig) {
        // key唯一的，检查重复性
        SysConfigExample sysConfigExample = new SysConfigExample();
        sysConfigExample.createCriteria().andParamKeyEqualTo(sysConfig.getParamKey());
        if (sysConfigMapper.countByExample(sysConfigExample) > 0) {
            throw new RuntimeException(APIResponseCodeEnum.ERROR_DUPLICATE_DATA.getMessage());
        }
        sysConfig.setCreatedTime(new Date());
        return sysConfigMapper.insertSelective(sysConfig);
    }

    public int update(SysConfig sysConfig) {
        String paramKey = sysConfig.getParamKey();
        // key不允许修改
        sysConfig.setParamKey(null);
        sysConfig.setUpdatedTime(new Date());
        int affectRow = sysConfigMapper.updateByPrimaryKeySelective(sysConfig);
        if(affectRow > 0) {
            SysConfigCache.put(paramKey, sysConfig.getParamValue());
        }
        return affectRow;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(List<Long> idList) {
        for (Long id : idList) {
            sysConfigMapper.deleteByPrimaryKey(id.intValue());
        }
        return 1;
    }

    public List<SysConfig> selectByPage(SysConfig sysConfig, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SysConfigExample sysConfigExample = new SysConfigExample();
        SysConfigExample.Criteria criteria = sysConfigExample.createCriteria();
        if (StringUtils.isNotBlank(sysConfig.getParamKey())) {
            criteria.andParamKeyEqualTo(sysConfig.getParamKey());
        }
        return sysConfigMapper.selectByExample(sysConfigExample);
    }

    /**
     * 从cache获取，获取不到从db获取
     */
    public String findValueFromCache(String key) {
        String value = SysConfigCache.get(key);
        if (StringUtils.isBlank(value)) {
            value = findValueFromDatabase(key);
            SysConfigCache.put(key, value);
        }
        return value;
    }

    private String findValueFromDatabase(String key) {
        return sysConfigMapper.findValueByKey(key);
    }
}
