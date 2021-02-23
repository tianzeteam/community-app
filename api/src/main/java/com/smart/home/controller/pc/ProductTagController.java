package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductTag;
import com.smart.home.modules.product.service.ProductTagService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author jason
**/
@Api(tags = "product产品标签接口")
@RestController
@RequestMapping("/api/pc/productTag")
public class ProductTagController{

    @Autowired
    private ProductTagService productTagService;

    @ApiOperation("创建产品标签")
    @PostMapping("/create")
    public APIResponse create(ProductTag productTag) {
                                                                                                                                        return APIResponse.OK(productTagService.create(productTag));
    }

    @ApiOperation("更新产品标签")
    @PostMapping("/update")
    public APIResponse update(ProductTag productTag) {
                                                                                                                                        return APIResponse.OK(productTagService.update(productTag));
    }

    @ApiOperation("删除产品标签")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productTagService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品标签")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductTag>> selectByPage(ProductTag productTag, int pageNum, int pageSize) {
        List<ProductTag> list = productTagService.selectByPage(productTag, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品标签")
    @GetMapping("/selectById")
    public APIResponse<ProductTag> selectById(Long id) {
        return APIResponse.OK(productTagService.findById(id));
    }

}