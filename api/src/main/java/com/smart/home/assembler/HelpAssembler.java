package com.smart.home.assembler;

import com.smart.home.controller.app.response.HelpVO;
import com.smart.home.modules.system.entity.SysHelp;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/23
 **/
public class HelpAssembler {

    public static List<HelpVO> assemblerHelpVO(List<SysHelp> list) {
        List<HelpVO> helpVOList = new ArrayList<>();
        for (SysHelp sysHelp : list) {
            HelpVO vo = new HelpVO();
            BeanUtils.copyProperties(sysHelp, vo);
            helpVOList.add(vo);
        }
        return helpVOList;
    }

}
