package com.smart.home.controller.pc;

import com.smart.home.common.exception.RestfulRequestException;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.controller.pc.request.system.SysDictDTO;
import com.smart.home.controller.pc.request.system.SysDictSearchDTO;
import com.smart.home.controller.pc.request.system.SysDictUpdateDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.system.entity.SysDict;
import com.smart.home.modules.system.service.SysDictService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/18
 **/
@Api(tags = "配置中心-数据字典")
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
    public APIResponse update(SysDictUpdateDTO sysDictDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictDTO, sysDict);
        sysDict.setUpdatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(sysDictService.update(sysDict));
    }

    @ApiOperation("按主键id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true)
    })
    @GetMapping("/selectById")
    public APIResponse<SysDictUpdateDTO> selectById(@RequestParam(required = true) Integer id) {
        SysDict sysDict = sysDictService.findById(id);
        SysDictUpdateDTO to = new SysDictUpdateDTO();
        BeanUtils.copyProperties(sysDict, to);
        return APIResponse.OK(to);
    }

    @ApiOperation(value = "删除数据字典")
    @PostMapping(value = "/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        try {
            sysDictService.delete(idListBean.getIdList());
        } catch (ServiceException e) {
            throw new RestfulRequestException(e.getCode(), e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation(value = "分页查询数据字典")
    @PostMapping(value = "selectByPage")
    public APIResponse<ResponsePageBean<SysDict>> selectByPage(SysDictSearchDTO sysDictSearchDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(sysDictSearchDTO, sysDict);
        List<SysDict> list = sysDictService.selectByPage(sysDict, sysDictSearchDTO.getPageNum(), sysDictSearchDTO.getPageSize());
        return APIResponse.OK(ResponsePageUtil.restPage(list, list));
    }

    @ApiOperation("按照字段编码查询字典值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典编码", required = true)
    })
    @GetMapping("/selectByCode")
    public APIResponse<SysDictUpdateDTO> selectByCode(@RequestParam(required = true) String code) {
        SysDict sysDict = sysDictService.queryByDictCode(code);
        SysDictUpdateDTO to = new SysDictUpdateDTO();
        BeanUtils.copyProperties(sysDict, to);
        return APIResponse.OK(to);
    }

}
