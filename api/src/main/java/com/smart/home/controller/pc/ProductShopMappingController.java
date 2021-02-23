package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductShopMapping;
import com.smart.home.modules.product.service.ProductShopMappingService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author jason
**/
@Api(tags = "product产品商城映射接口")
@RestController
@RequestMapping("/api/pc/productShopMapping")
public class ProductShopMappingController{

    @Autowired
    private ProductShopMappingService productShopMappingService;

    @ApiOperation("创建产品商城映射")
    @PostMapping("/create")
    public APIResponse create(ProductShopMapping productShopMapping) {
                                                                                                                                        return APIResponse.OK(productShopMappingService.create(productShopMapping));
    }

    @ApiOperation("更新产品商城映射")
    @PostMapping("/update")
    public APIResponse update(ProductShopMapping productShopMapping) {
                                                                                                                                        return APIResponse.OK(productShopMappingService.update(productShopMapping));
    }

    @ApiOperation("删除产品商城映射")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productShopMappingService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品商城映射")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductShopMapping>> selectByPage(ProductShopMapping productShopMapping, int pageNum, int pageSize) {
        List<ProductShopMapping> list = productShopMappingService.selectByPage(productShopMapping, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品商城映射")
    @GetMapping("/selectById")
    public APIResponse<ProductShopMapping> selectById(Long id) {
        return APIResponse.OK(productShopMappingService.findById(id));
    }

}