package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCategoryParam;
import com.smart.home.modules.product.service.ProductCategoryParamService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品类别参数接口")
@RestController
@RequestMapping("/api/pc/productCategoryParam")
public class ProductCategoryParamController {

    @Autowired
    private ProductCategoryParamService productCategoryParamService;

    @ApiOperation("更新产品类别参数")
    @PostMapping("/update")
    public APIResponse update(ProductCategoryParam productCategoryParam) {
        return APIResponse.OK(productCategoryParamService.update(productCategoryParam));
    }

    @ApiOperation("删除产品类别参数")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productCategoryParamService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品类别参数")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductCategoryParam>> selectByPage(ProductCategoryParam productCategoryParam, int pageNum, int pageSize) {
        List<ProductCategoryParam> list = productCategoryParamService.selectByPage(productCategoryParam, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品类别参数")
    @GetMapping("/selectById")
    public APIResponse<ProductCategoryParam> selectById(Long id) {
        return APIResponse.OK(productCategoryParamService.findById(id));
    }

}