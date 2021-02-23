package com.smart.home.modules.system.service;

import com.smart.home.modules.system.dao.SysAgreementMapper;
import com.smart.home.modules.system.entity.SysAgreement;
import com.smart.home.modules.system.entity.SysAgreementExample;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/23
 **/
@Service
public class SysAgreementService {

    @Resource
    private SysAgreementMapper sysAgreementMapper;

    public SysAgreement findByType(Integer type) {
        SysAgreementExample example = new SysAgreementExample();
        example.createCriteria().andTypeEqualTo(type);
        List<SysAgreement> list = sysAgreementMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

}
