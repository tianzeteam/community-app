package com.smart.home.controller.pc;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.response.article.ArticleChannelSelectVO;
import com.smart.home.controller.pc.response.article.IndexArticleCardVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleChannel;
import com.smart.home.modules.article.service.ArticleChannelService;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.util.ResponsePageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/**
 * @author jason
 * @date 2021/3/2
 **/
@Api(tags = "首页")
@RestController
@RequestMapping("/api/pc/index")
public class IndexPageController {

    @Autowired
    private ArticleChannelService articleChannelService;
    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取频道列表")
    @AnonAccess
    @GetMapping("/selectIndexArticleChannel")
    public APIResponse<List<ArticleChannelSelectVO>> selectIndexArticleChannel() {
        List<ArticleChannel> list = articleChannelService.selectAllIndexValid();
        List<ArticleChannelSelectVO> resultList = BeanCopyUtils.convertListTo(list, ArticleChannelSelectVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("根据频道id获取文章卡片列表-分页")
    @AnonAccess
    @GetMapping("/selectArticleCardByPage")
    public APIResponse<ResponsePageBean<IndexArticleCardVO>> selectArticleCardByPage(Integer channelId, int pageNum, int pageSize) {
        List<Article> list = articleService.selectArticleCardByPage(channelId, pageNum, pageSize);
        List<IndexArticleCardVO> resultList = BeanCopyUtils.convertListTo(list, IndexArticleCardVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }
}
