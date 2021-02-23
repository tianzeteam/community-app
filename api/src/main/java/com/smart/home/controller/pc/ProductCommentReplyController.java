package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.product.entity.ProductCommentReply;
import com.smart.home.modules.product.service.ProductCommentReplyService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "product产品评价回复接口")
@RestController
@RequestMapping("/api/pc/productCommentReply")
public class ProductCommentReplyController {

    @Autowired
    private ProductCommentReplyService productCommentReplyService;

    @ApiOperation("创建产品评价回复")
    @PostMapping("/create")
    public APIResponse create(ProductCommentReply productCommentReply) {
        return APIResponse.OK(productCommentReplyService.create(productCommentReply));
    }

    @ApiOperation("更新产品评价回复")
    @PostMapping("/update")
    public APIResponse update(ProductCommentReply productCommentReply) {
        return APIResponse.OK(productCommentReplyService.update(productCommentReply));
    }

    @ApiOperation("删除产品评价回复")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        productCommentReplyService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("分页查询产品评价回复")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ProductCommentReply>> selectByPage(ProductCommentReply productCommentReply, int pageNum, int pageSize) {
        List<ProductCommentReply> list = productCommentReplyService.selectByPage(productCommentReply, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询产品评价回复")
    @GetMapping("/selectById")
    public APIResponse<ProductCommentReply> selectById(Long id) {
        return APIResponse.OK(productCommentReplyService.findById(id));
    }

}