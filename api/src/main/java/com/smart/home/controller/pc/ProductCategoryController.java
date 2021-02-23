package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCategory;
import com.smart.home.modules.product.service.ProductCategoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品类目接口")
@RestController
@RequestMapping("/api/pc/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation("创建产品类目")
    @PostMapping("/create")
    public APIResponse create(ProductCategory productCategory) {
        productCategory.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productCategoryService.create(productCategory));
    }

    @ApiOperation("更新产品类目")
    @PostMapping("/update")
    public APIResponse update(ProductCategory productCategory) {
        productCategory.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productCategoryService.update(productCategory));
    }

    @ApiOperation("删除产品类目")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productCategoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品类目")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductCategory>> selectByPage(ProductCategory productCategory, int pageNum, int pageSize) {
        List<ProductCategory> list = productCategoryService.selectByPage(productCategory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品类目")
    @GetMapping("/selectById")
    public APIResponse<ProductCategory> selectById(Long id) {
        return APIResponse.OK(productCategoryService.findById(id));
    }

}