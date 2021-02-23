package com.smart.home.modules.system.service;

/**
 * @author jason
 * @date 2021/2/23
 **/

import com.smart.home.modules.system.dao.SysHelpMapper;
import com.smart.home.modules.system.entity.SysHelp;
import com.smart.home.modules.system.entity.SysHelpExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysHelpService {

    @Resource
    private SysHelpMapper sysHelpMapper;

    public List<SysHelp> findByPidAndState(int pid, int status) {
        SysHelpExample example = new SysHelpExample();
        example.createCriteria().andPidEqualTo(pid).andStateEqualTo(status);
        example.setOrderByClause("sort desc");
        return this.sysHelpMapper.selectByExample(example);
    }
}
