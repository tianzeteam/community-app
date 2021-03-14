package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.product.ProductBrandCreateDTO;
import com.smart.home.controller.pc.request.product.ProductBrandUpdateDTO;
import com.smart.home.controller.pc.response.product.ProductBrandSelectVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.product.entity.ProductBrand;
import com.smart.home.modules.product.service.ProductBrandService;
import com.smart.home.util.ResponsePageUtil;
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
@Api(tags = "配置中心-产品品牌")
@RestController
@RequestMapping("/api/pc/productBrand")
public class ProductBrandController {

    @Autowired
    private ProductBrandService productBrandService;

    @ApiOperation("创建产品品牌")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/create")
    public APIResponse create(@Valid ProductBrandCreateDTO productBrandCreateDTO, BindingResult bindingResult) {
        ProductBrand productBrand = new ProductBrand();
        BeanUtils.copyProperties(productBrandCreateDTO, productBrand);
        productBrand.setCreatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(productBrandService.create(productBrand));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("更新产品品牌")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/update")
    public APIResponse update(@Valid ProductBrandUpdateDTO productBrandUpdateDTO, BindingResult bindingResult) {
        ProductBrand productBrand = new ProductBrand();
        BeanUtils.copyProperties(productBrandUpdateDTO, productBrand);
        productBrand.setCreatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(productBrandService.update(productBrand));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("删除产品品牌")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        try {
            productBrandService.delete(idListBean.getIdList());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "", required = false),
            @ApiImplicitParam(name = "pageNum", value = "", required = true),
            @ApiImplicitParam(name = "pageSize", value = "", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductBrand>> selectByPage(String name, int pageNum, int pageSize) {
        ProductBrand productBrand = new ProductBrand();
        productBrand.setBrandName(name);
        List<ProductBrand> list = productBrandService.selectByPage(productBrand, pageNum, pageSize);
        return APIResponse.OK(ResponsePageUtil.restPage(list, list));
    }

    @ApiOperation("按主键ID查询产品品牌")
    @GetMapping("/selectById")
    public APIResponse<ProductBrand> selectById(Long id) {
        return APIResponse.OK(productBrandService.findById(id));
    }

    @ApiOperation("下拉选择品牌")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectSelectItems")
    public APIResponse<List<ProductBrandSelectVO>> selectSelectItems() {
        List<ProductBrand> list = productBrandService.queryAllValid();
        List<ProductBrandSelectVO> resultList = BeanCopyUtils.convertListTo(list, ProductBrandSelectVO::new);
        return APIResponse.OK(resultList);
    }

}