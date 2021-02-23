package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductShop;
import com.smart.home.modules.product.service.ProductShopService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品商城接口")
@RestController
@RequestMapping("/api/pc/productShop")
public class ProductShopController {

    @Autowired
    private ProductShopService productShopService;

    @ApiOperation("创建产品商城")
    @PostMapping("/create")
    public APIResponse create(ProductShop productShop) {
        productShop.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productShopService.create(productShop));
    }

    @ApiOperation("更新产品商城")
    @PostMapping("/update")
    public APIResponse update(ProductShop productShop) {
        productShop.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productShopService.update(productShop));
    }

    @ApiOperation("删除产品商城")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productShopService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品商城")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductShop>> selectByPage(ProductShop productShop, int pageNum, int pageSize) {
        List<ProductShop> list = productShopService.selectByPage(productShop, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品商城")
    @GetMapping("/selectById")
    public APIResponse<ProductShop> selectById(Long id) {
        return APIResponse.OK(productShopService.findById(id));
    }

}