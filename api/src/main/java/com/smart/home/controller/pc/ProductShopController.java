package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.product.ProductShopCreateDTO;
import com.smart.home.controller.pc.request.product.ProductShopUpdateDTO;
import com.smart.home.controller.pc.response.product.ProductShopSelectVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.product.entity.ProductShop;
import com.smart.home.modules.product.service.ProductShopService;
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
@Api(tags = "配置中心-产品商城")
@RestController
@RequestMapping("/api/pc/productShop")
public class ProductShopController {

    @Autowired
    private ProductShopService productShopService;

    @ApiOperation("创建产品商城")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/create")
    public APIResponse create(@Valid ProductShopCreateDTO productShopCreateDTO, BindingResult bindingResult) {
        ProductShop productShop = new ProductShop();
        BeanUtils.copyProperties(productShopCreateDTO, productShop);
        productShop.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productShopService.create(productShop));
    }

    @ApiOperation("更新产品商城")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/update")
    public APIResponse update(@Valid ProductShopUpdateDTO productShopUpdateDTO, BindingResult bindingResult) {
        ProductShop productShop = new ProductShop();
        BeanUtils.copyProperties(productShopUpdateDTO, productShop);
        productShop.setCreatedBy(UserUtils.getLoginUserId());
        return APIResponse.OK(productShopService.update(productShop));
    }

    @ApiOperation("删除产品商城")
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productShopService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品商城")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = false),
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RoleAccess(RoleConsts.ADMIN)
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductShop>> selectByPage(String name, int pageNum, int pageSize) {
        ProductShop productShop = new ProductShop();
        productShop.setShopName(name);
        List<ProductShop> list = productShopService.selectByPage(productShop, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品商城")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectById")
    public APIResponse<ProductShop> selectById(Long id) {
        return APIResponse.OK(productShopService.findById(id));
    }

    @ApiOperation("下拉选择商城")
    @RoleAccess(RoleConsts.ADMIN)
    @GetMapping("/selectSelectItems")
    public APIResponse<List<ProductShopSelectVO>> selectSelectItems() {
        List<ProductShop> list = productShopService.queryAllValid();
        List<ProductShopSelectVO> resutList = BeanCopyUtils.convertListTo(list, ProductShopSelectVO::new);
        return APIResponse.OK(resutList);
    }
}