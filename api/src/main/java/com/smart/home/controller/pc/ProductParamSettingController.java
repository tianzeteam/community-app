package com.smart.home.controller.pc;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.product.ProductParamSettingCreateDTO;
import com.smart.home.controller.pc.request.product.ProductParamSettingUpdateDTO;
import com.smart.home.controller.pc.response.product.ProductParamSettingSelectVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductParamSetting;
import com.smart.home.modules.product.service.ProductParamSettingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "配置中心-参数库")
@RestController
@RequestMapping("/api/pc/productParamSetting")
public class ProductParamSettingController {

    @Autowired
    private ProductParamSettingService productParamSettingService;

    @ApiOperation("创建产品参数库")
    @PostMapping("/create")
    public APIResponse create(@Valid @RequestBody ProductParamSettingCreateDTO productParamSettingCreateDTO, BindingResult bindingResult) {
        ProductParamSetting productParamSetting = new ProductParamSetting();
        BeanUtils.copyProperties(productParamSettingCreateDTO, productParamSetting);
        productParamSetting.setCreatedBy(UserUtils.getLoginUserId());
        if (!CollectionUtils.isEmpty(productParamSettingCreateDTO.getEnumValueList())) {
            productParamSetting.setEnumValues(JSON.toJSONString(productParamSettingCreateDTO.getEnumValueList()));
        }
        try {
            return APIResponse.OK(productParamSettingService.create(productParamSetting));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("更新产品参数库")
    @PostMapping("/update")
    public APIResponse update(@Valid @RequestBody ProductParamSettingUpdateDTO productParamSettingUpdateDTO, BindingResult bindingResult) {
        ProductParamSetting productParamSetting = new ProductParamSetting();
        BeanUtils.copyProperties(productParamSettingUpdateDTO, productParamSetting);
        productParamSetting.setCreatedBy(UserUtils.getLoginUserId());
        if (!CollectionUtils.isEmpty(productParamSettingUpdateDTO.getEnumValueList())) {
            productParamSetting.setEnumValues(JSON.toJSONString(productParamSettingUpdateDTO.getEnumValueList()));
        }
        try {
            return APIResponse.OK(productParamSettingService.update(productParamSetting));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("删除产品参数库")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        try {
            productParamSettingService.delete(idListBean.getIdList());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品参数库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paramName", value = "参数名称", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductParamSetting>> selectByPage(String paramName, int pageNum, int pageSize) {
        ProductParamSetting productParamSetting = new ProductParamSetting();
        productParamSetting.setParamName(paramName);
        List<ProductParamSetting> list = productParamSettingService.selectByPage(productParamSetting, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品参数库")
    @GetMapping("/selectById")
    public APIResponse<ProductParamSettingUpdateDTO> selectById(Integer id) {
        ProductParamSetting productParamSetting = productParamSettingService.findById(id);
        ProductParamSettingUpdateDTO to = new ProductParamSettingUpdateDTO();
        BeanUtils.copyProperties(productParamSetting, to);
        if (StringUtils.isNotBlank(productParamSetting.getEnumValues())) {
            to.setEnumValueList(JSON.parseArray(productParamSetting.getEnumValues(), String.class));
        }
        return APIResponse.OK();
    }

    @ApiOperation("下拉选择参数库")
    @GetMapping("selectSelectItems")
    public APIResponse<List<ProductParamSettingSelectVO>> selectSelectItems() {
        List<ProductParamSetting> list = productParamSettingService.queryAllValidExceptEnableAll();
        List<ProductParamSettingSelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductParamSettingSelectVO::new,(s, t)->{
            if (StringUtils.isNotBlank(s.getEnumValues())) {
                t.setEnumValueList(JSON.parseArray(s.getEnumValues(), String.class));
            }
        });
        return APIResponse.OK(resultList);
    }

}