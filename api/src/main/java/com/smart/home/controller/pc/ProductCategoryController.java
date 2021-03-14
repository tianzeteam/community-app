package com.smart.home.controller.pc;

import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.product.ProductCategoryCreateDTO;
import com.smart.home.controller.pc.request.product.ProductCategoryUpdateDTO;
import com.smart.home.controller.pc.response.product.ProductCategorySelectVO;
import com.smart.home.controller.pc.response.product.ProductCategoryVO;
import com.smart.home.controller.pc.response.product.ProductParamSettingSelectVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCategory;
import com.smart.home.modules.product.entity.ProductParamSetting;
import com.smart.home.modules.product.service.ProductCategoryService;
import com.smart.home.modules.product.service.ProductParamSettingService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @Autowired
    private ProductParamSettingService productParamSettingService;

    @ApiOperation("创建产品类目")
    @PostMapping("/create")
    public APIResponse create(@Valid @RequestBody ProductCategoryCreateDTO productCategoryCreateDTO, BindingResult bindingResult) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryCreateDTO, productCategory);
        productCategory.setCreatedBy(UserUtils.getLoginUserId());
        int affectRow = 0;
        try {
            affectRow = productCategoryService.create(productCategory, productCategoryCreateDTO.getParamIdList());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK(affectRow);
    }

    @ApiOperation("更新产品类目")
    @PostMapping("/update")
    public APIResponse update(@Valid @RequestBody ProductCategoryUpdateDTO productCategoryUpdateDTO, BindingResult bindingResult) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryUpdateDTO, productCategory);
        productCategory.setUpdatedBy(UserUtils.getLoginUserId());
        try {
            return APIResponse.OK(productCategoryService.update(productCategory, productCategoryUpdateDTO.getParamIdList()));
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
    }

    @ApiOperation("删除产品类目")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        try {
            productCategoryService.delete(idListBean.getIdList());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
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
        return APIResponse.OK(ResponsePageUtil.restPage(list, list));
    }

    @ApiOperation("按主键ID查询产品类目")
    @GetMapping("/selectById")
    public APIResponse<ProductCategoryVO> selectById(Integer id) {
        ProductCategory productCategory = productCategoryService.findById(id);
        ProductCategoryVO vo = assemblerProductCategoryVO(productCategory);
        if(vo.getPid().intValue() != 0) {
            productCategory = productCategoryService.findById(vo.getPid());
            vo.setParent(assemblerProductCategoryVO(productCategory));
            if (productCategory.getPid() != 0) {
                productCategory = productCategoryService.findById(productCategory.getPid());
                vo.getParent().setParent(assemblerProductCategoryVO(productCategory));
            }
        }
        return APIResponse.OK(vo);
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

    private ProductCategoryVO assemblerProductCategoryVO(ProductCategory productCategory) {
        ProductCategoryVO vo = new ProductCategoryVO();
        BeanUtils.copyProperties(productCategory, vo);
        if (!CollectionUtils.isEmpty(productCategory.getParamIdList())) {
            List<ProductParamSettingSelectVO> paramList = new ArrayList<>();
            for (Integer paramId : productCategory.getParamIdList()) {
                ProductParamSetting productParamSetting = productParamSettingService.findById(paramId);
                ProductParamSettingSelectVO productParamSettingSelectVO = new ProductParamSettingSelectVO();
                BeanUtils.copyProperties(productParamSetting, productParamSettingSelectVO);
                paramList.add(productParamSettingSelectVO);
            }
            vo.setParamList(paramList);
        }
        return vo;
    }

}