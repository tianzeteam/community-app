package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCategoryTag;
import com.smart.home.modules.product.service.ProductCategoryTagService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品类目标签接口")
@RestController
@RequestMapping("/api/pc/productCategoryTag")
public class ProductCategoryTagController {

    @Autowired
    private ProductCategoryTagService productCategoryTagService;

    @ApiOperation("创建产品类目标签")
    @PostMapping("/create")
    public APIResponse create(ProductCategoryTag productCategoryTag) {
        return APIResponse.OK(productCategoryTagService.create(productCategoryTag));
    }

    @ApiOperation("更新产品类目标签")
    @PostMapping("/update")
    public APIResponse update(ProductCategoryTag productCategoryTag) {
        return APIResponse.OK(productCategoryTagService.update(productCategoryTag));
    }

    @ApiOperation("删除产品类目标签")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productCategoryTagService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品类目标签")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductCategoryTag>> selectByPage(ProductCategoryTag productCategoryTag, int pageNum, int pageSize) {
        List<ProductCategoryTag> list = productCategoryTagService.selectByPage(productCategoryTag, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品类目标签")
    @GetMapping("/selectById")
    public APIResponse<ProductCategoryTag> selectById(Long id) {
        return APIResponse.OK(productCategoryTagService.findById(id));
    }

}