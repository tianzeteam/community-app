package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.util.UserUtils;
import com.smart.home.controller.pc.request.SysConfigDTO;
import com.smart.home.controller.pc.request.SysConfigSearchDTO;
import com.smart.home.modules.system.entity.SysConfig;
import com.smart.home.modules.system.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Api(tags = "system系统参数接口")
@RestController
@RequestMapping("/api/pc/sysConfig")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @ApiOperation(value = "创建系统参数", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse create(SysConfigDTO sysConfigDTO) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigDTO, sysConfig);
        sysConfig.setCreatedBy(String.valueOf(UserUtils.getLoginUserId()));
        return APIResponse.OK(sysConfigService.insert(sysConfig));
    }

    @ApiOperation(value = "更新系统参数", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse update(SysConfigDTO sysConfigDTO) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigDTO, sysConfig);
        sysConfig.setUpdatedBy(String.valueOf(UserUtils.getLoginUserId()));
        return APIResponse.OK(sysConfigService.update(sysConfig));
    }

    @ApiOperation(value = "删除系统参数")
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        return APIResponse.OK(sysConfigService.delete(idListBean.getIdList()));
    }

    @ApiOperation(value = "分页查询系统参数", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @PostMapping(value = "selectByPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public APIResponse<ResponsePageBean<SysConfig>> selectByPage(SysConfigSearchDTO sysConfigSearchDTO) {
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigSearchDTO, sysConfig);
        return APIResponse.OK(ResponsePageBean.restPage(sysConfigService.selectByPage(sysConfig, sysConfigSearchDTO.getPageNum(), sysConfigSearchDTO.getPageSize())));
    }

}
