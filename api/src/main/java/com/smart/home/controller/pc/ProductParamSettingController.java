package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductParamSetting;
import com.smart.home.modules.product.service.ProductParamSettingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品参数配置接口")
@RestController
@RequestMapping("/api/pc/productParamSetting")
public class ProductParamSettingController {

    @Autowired
    private ProductParamSettingService productParamSettingService;

    @ApiOperation("创建产品参数配置")
    @PostMapping("/create")
    public APIResponse create(ProductParamSetting productParamSetting) {
        productParamSetting.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productParamSettingService.create(productParamSetting));
    }

    @ApiOperation("更新产品参数配置")
    @PostMapping("/update")
    public APIResponse update(ProductParamSetting productParamSetting) {
        productParamSetting.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productParamSettingService.update(productParamSetting));
    }

    @ApiOperation("删除产品参数配置")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productParamSettingService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品参数配置")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductParamSetting>> selectByPage(ProductParamSetting productParamSetting, int pageNum, int pageSize) {
        List<ProductParamSetting> list = productParamSettingService.selectByPage(productParamSetting, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品参数配置")
    @GetMapping("/selectById")
    public APIResponse<ProductParamSetting> selectById(Long id) {
        return APIResponse.OK(productParamSettingService.findById(id));
    }

}