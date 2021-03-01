package com.smart.home.controller.pc;

import com.alibaba.fastjson.JSON;
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
        return APIResponse.OK(productParamSettingService.create(productParamSetting));
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
        return APIResponse.OK(productParamSettingService.update(productParamSetting));
    }

    @ApiOperation("删除产品参数库")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productParamSettingService.delete(idListBean.getIdList());
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
    public APIResponse<ProductParamSetting> selectById(Integer id) {
        return APIResponse.OK(productParamSettingService.findById(id));
    }

    @ApiOperation("下拉选择参数库")
    @GetMapping("selectSelectItems")
    public APIResponse<List<ProductParamSettingSelectVO>> selectSelectItems() {
        List<ProductParamSetting> list = productParamSettingService.queryAllValid();
        List<ProductParamSettingSelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductParamSettingSelectVO::new);
        return APIResponse.OK(resultList);
    }

}