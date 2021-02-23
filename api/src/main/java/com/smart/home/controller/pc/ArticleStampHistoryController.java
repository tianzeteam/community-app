package com.smart.home.controller.pc;

import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.modules.article.entity.ArticleStampHistory;
import com.smart.home.modules.article.service.ArticleStampHistoryService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("创建文章被踩历史")
    @PostMapping("/create")
    public APIResponse create(ArticleStampHistory articleStampHistory) {
        return APIResponse.OK(articleStampHistoryService.create(articleStampHistory));
    }

    @ApiOperation("更新文章被踩历史")
    @PostMapping("/update")
    public APIResponse update(ArticleStampHistory articleStampHistory) {
        return APIResponse.OK(articleStampHistoryService.update(articleStampHistory));
    }

    @ApiOperation("删除文章被踩历史")
    @PostMapping("/delete")
    public APIResponse delete(@RequestBody IdListBean idListBean) {
        articleStampHistoryService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

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