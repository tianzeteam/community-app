package com.smart.home.controller.pc;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.product.ProductCategoryCreateDTO;
import com.smart.home.controller.pc.response.product.ProductCategorySelectVO;
import com.smart.home.controller.pc.request.product.ProductCategoryUpdateDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCategory;
import com.smart.home.modules.product.service.ProductCategoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "配置中心-产品库类目")
@RestController
@RequestMapping("/api/pc/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation("创建产品类目")
    @PostMapping("/create")
    public APIResponse create(@Valid ProductCategoryCreateDTO productCategoryCreateDTO, BindingResult bindingResult) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryCreateDTO, productCategory);
        productCategory.setCreatedBy(UserUtils.getLoginUserId());
        int affectRow = productCategoryService.create(productCategory, productCategoryCreateDTO.getParamIdList());
        return APIResponse.OK(affectRow);
    }

    @ApiOperation("更新产品类目")
    @PostMapping("/update")
    public APIResponse update(@Valid ProductCategoryUpdateDTO productCategoryUpdateDTO, BindingResult bindingResult) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryUpdateDTO, productCategory);
        productCategory.setUpdatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productCategoryService.update(productCategory, productCategoryUpdateDTO.getParamIdList()));
    }

    @ApiOperation("删除产品类目")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productCategoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "父级别主键id", required = false),
            @ApiImplicitParam(name = "title", value = "按名称查询", required = false),
            @ApiImplicitParam(name = "level", value = "等级三级：1/2/3", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductCategory>> selectByPage(Integer pid, String title, Integer level,int pageNum, int pageSize) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setPid(pid);
        productCategory.setTitle(title);
        productCategory.setLevel(level);
        List<ProductCategory> list = productCategoryService.selectByPage(productCategory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品类目")
    @GetMapping("/selectById")
    public APIResponse<ProductCategory> selectById(Long id) {
        return APIResponse.OK(productCategoryService.findById(id));
    }

    @ApiOperation("下拉选择类目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "父主键id，顶级为0", required = true)
    })
    @GetMapping("selectSelectItems")
    public APIResponse<List<ProductCategorySelectVO>> selectSelectItems(Integer pid) {
        List<ProductCategory> list = productCategoryService.queryAllValidByPid(pid);
        List<ProductCategorySelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductCategorySelectVO::new);
        return APIResponse.OK(resultList);
    }

}