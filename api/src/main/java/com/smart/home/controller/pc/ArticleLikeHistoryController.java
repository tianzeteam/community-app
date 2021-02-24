package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleLikeHistory;
import com.smart.home.modules.article.service.ArticleLikeHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jason
 **/
@Api(tags = "article文章点赞历史接口")
@RestController
@RequestMapping("/api/pc/articleLikeHistory")
public class ArticleLikeHistoryController {

    @Autowired
    private ArticleLikeHistoryService articleLikeHistoryService;

    @ApiOperation("分页查询文章点赞历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleLikeHistory>> selectByPage(ArticleLikeHistory articleLikeHistory, int pageNum, int pageSize) {
        List<ArticleLikeHistory> list = articleLikeHistoryService.selectByPage(articleLikeHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章点赞历史")
    @GetMapping("/selectById")
    public APIResponse<ArticleLikeHistory> selectById(Long id) {
        return APIResponse.OK(articleLikeHistoryService.findById(id));
    }

}