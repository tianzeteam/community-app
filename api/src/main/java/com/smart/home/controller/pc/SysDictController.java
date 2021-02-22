package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.util.UserUtils;
import com.smart.home.controller.pc.request.SysDictDTO;
import com.smart.home.controller.pc.request.SysDictSearchDTO;
import com.smart.home.modules.system.entity.SysDict;
import com.smart.home.modules.system.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Api(tags = "system数据字典接口")
@RestController
@RequestMapping("/api/pc/sysDict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation(value = "创建数据字典")
    @PostMapping(value = "/create")
    public APIResponse create(SysDictDTO sysDictDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictDTO, sysDict);
        sysDict.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(sysDictService.insert(sysDict));
    }

    @ApiOperation(value = "更新数据字典")
    @PostMapping(value = "/update")
    public APIResponse update(SysDictDTO sysDictDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictDTO, sysDict);
        sysDict.setUpdatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(sysDictService.update(sysDict));
    }

    @ApiOperation(value = "删除数据字典")
    @PostMapping(value = "/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        return APIResponse.OK(sysDictService.delete(idListBean.getIdList()));
    }

    @ApiOperation(value = "分页查询数据字典")
    @PostMapping(value = "selectByPage")
    public APIResponse<ResponsePageBean<SysDict>> selectByPage(SysDictSearchDTO sysDictSearchDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictSearchDTO, sysDict);
        return APIResponse.OK(ResponsePageBean.restPage(sysDictService.selectByPage(sysDict, sysDictSearchDTO.getPageNum(), sysDictSearchDTO.getPageSize())));
    }

}
