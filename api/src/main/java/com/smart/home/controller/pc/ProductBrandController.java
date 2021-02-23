package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductBrand;
import com.smart.home.modules.product.service.ProductBrandService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品品牌接口")
@RestController
@RequestMapping("/api/pc/productBrand")
public class ProductBrandController {

    @Autowired
    private ProductBrandService productBrandService;

    @ApiOperation("创建产品品牌")
    @PostMapping("/create")
    public APIResponse create(ProductBrand productBrand) {
        productBrand.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productBrandService.create(productBrand));
    }

    @ApiOperation("更新产品品牌")
    @PostMapping("/update")
    public APIResponse update(ProductBrand productBrand) {
        productBrand.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productBrandService.update(productBrand));
    }

    @ApiOperation("删除产品品牌")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productBrandService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品品牌")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductBrand>> selectByPage(ProductBrand productBrand, int pageNum, int pageSize) {
        List<ProductBrand> list = productBrandService.selectByPage(productBrand, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品品牌")
    @GetMapping("/selectById")
    public APIResponse<ProductBrand> selectById(Long id) {
        return APIResponse.OK(productBrandService.findById(id));
    }

}