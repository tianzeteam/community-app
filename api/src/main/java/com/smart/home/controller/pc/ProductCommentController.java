package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.service.ProductCommentService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品评价接口")
@RestController
@RequestMapping("/api/pc/productComment")
public class ProductCommentController {

    @Autowired
    private ProductCommentService productCommentService;

    @ApiOperation("创建产品评价")
    @PostMapping("/create")
    public APIResponse create(ProductComment productComment) {
        return APIResponse.OK(productCommentService.create(productComment));
    }

    @ApiOperation("更新产品评价")
    @PostMapping("/update")
    public APIResponse update(ProductComment productComment) {
        return APIResponse.OK(productCommentService.update(productComment));
    }

    @ApiOperation("删除产品评价")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productCommentService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品评价")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductComment>> selectByPage(ProductComment productComment, int pageNum, int pageSize) {
        List<ProductComment> list = productCommentService.selectByPage(productComment, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品评价")
    @GetMapping("/selectById")
    public APIResponse<ProductComment> selectById(Long id) {
        return APIResponse.OK(productCommentService.findById(id));
    }

}