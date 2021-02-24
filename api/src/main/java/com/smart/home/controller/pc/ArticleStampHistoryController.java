package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleStampHistory;
import com.smart.home.modules.article.service.ArticleStampHistoryService;
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
@Api(tags = "article文章被踩历史接口")
@RestController
@RequestMapping("/api/pc/articleStampHistory")
public class ArticleStampHistoryController {

    @Autowired
    private ArticleStampHistoryService articleStampHistoryService;

    @ApiOperation("分页查询文章被踩历史")
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleStampHistory>> selectByPage(ArticleStampHistory articleStampHistory, int pageNum, int pageSize) {
        List<ArticleStampHistory> list = articleStampHistoryService.selectByPage(articleStampHistory, pageNum, pageSize);
        return APIResponse.OK(ResponsePageBean.restPage(list));
    }

    @ApiOperation("按主键ID查询文章被踩历史")
    @GetMapping("/selectById")
    public APIResponse<ArticleStampHistory> selectById(Long id) {
        return APIResponse.OK(articleStampHistoryService.findById(id));
    }

}