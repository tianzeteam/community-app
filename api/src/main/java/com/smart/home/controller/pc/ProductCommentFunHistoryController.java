package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCommentFunHistory;
import com.smart.home.modules.product.service.ProductCommentFunHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品评价有趣历史接口")
@RestController
@RequestMapping("/api/pc/productCommentFunHistory")
public class ProductCommentFunHistoryController {

    @Autowired
    private ProductCommentFunHistoryService productCommentFunHistoryService;

    @ApiOperation("分页查询产品评价有趣历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductCommentFunHistory>> selectByPage(ProductCommentFunHistory productCommentFunHistory, int pageNum, int pageSize) {
        List<ProductCommentFunHistory> list = productCommentFunHistoryService.selectByPage(productCommentFunHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品评价有趣历史")
    @GetMapping("/selectById")
    public APIResponse<ProductCommentFunHistory> selectById(Long id) {
        return APIResponse.OK(productCommentFunHistoryService.findById(id));
    }

}