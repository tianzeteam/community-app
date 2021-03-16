package com.smart.home.controller.app;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.product.ProductCategoryVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.product.entity.ProductCategory;
import com.smart.home.modules.product.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@Api(tags = "产品库-产品库")
@RestController
@RequestMapping("/api/app/productCategory")
public class AppProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation("查询一级分类")
    @AnonAccess
    @GetMapping("/queryCategoryOne")
    public APIResponse<List<ProductCategoryVO>> queryCategoryOne() {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(0);
        List<ProductCategoryVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategoryVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("查询二级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "上一级别的主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryCategoryTwo")
    public APIResponse<List<ProductCategoryVO>> queryCategoryTwo(Integer pid) {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(pid);
        List<ProductCategoryVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategoryVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("查询三级分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "上一级别的主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryCategoryThree")
    public APIResponse<List<ProductCategoryVO>> queryCategoryThree(Integer pid) {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(pid);
        List<ProductCategoryVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategoryVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("根据一级分类查询二级和三级所有")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "一级分类主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/querySubCategories")
    public APIResponse<List<ProductCategoryVO>> querySubCategories(@RequestParam(required = true) Integer pid) {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(pid);
        List<ProductCategoryVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategoryVO::new, (s, t)->{
            List<ProductCategory> subList = productCategoryService.queryAllValidByPid(s.getId());
            t.setSubCategories(BeanCopyUtils.convertListTo(subList, ProductCategoryVO::new));
        });
        return APIResponse.OK(resultList);
    }

}
