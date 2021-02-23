package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductParamValue;
import com.smart.home.modules.product.service.ProductParamValueService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品参数值接口")
@RestController
@RequestMapping("/api/pc/productParamValue")
public class ProductParamValueController {

    @Autowired
    private ProductParamValueService productParamValueService;

    @ApiOperation("创建产品参数值")
    @PostMapping("/create")
    public APIResponse create(ProductParamValue productParamValue) {
        return APIResponse.OK(productParamValueService.create(productParamValue));
    }

    @ApiOperation("更新产品参数值")
    @PostMapping("/update")
    public APIResponse update(ProductParamValue productParamValue) {
        return APIResponse.OK(productParamValueService.update(productParamValue));
    }

    @ApiOperation("删除产品参数值")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productParamValueService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品参数值")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductParamValue>> selectByPage(ProductParamValue productParamValue, int pageNum, int pageSize) {
        List<ProductParamValue> list = productParamValueService.selectByPage(productParamValue, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品参数值")
    @GetMapping("/selectById")
    public APIResponse<ProductParamValue> selectById(Long id) {
        return APIResponse.OK(productParamValueService.findById(id));
    }

}