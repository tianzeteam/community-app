package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品接口")
@RestController
@RequestMapping("/api/pc/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("创建产品")
    @PostMapping("/create")
    public APIResponse create(Product product) {
        product.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productService.create(product));
    }

    @ApiOperation("更新产品")
    @PostMapping("/update")
    public APIResponse update(Product product) {
        product.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productService.update(product));
    }

    @ApiOperation("删除产品")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<Product>> selectByPage(Product product, int pageNum, int pageSize) {
        List<Product> list = productService.selectByPage(product, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品")
    @GetMapping("/selectById")
    public APIResponse<Product> selectById(Integer id) {
        return APIResponse.OK(productService.findById(id));
    }

}