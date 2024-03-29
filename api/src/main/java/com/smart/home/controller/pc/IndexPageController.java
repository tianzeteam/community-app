package com.smart.home.controller.pc;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.article.ArticleCardVO;
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
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true),
            @ApiImplicitParam(name = "channelId", value = "文章频道主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/selectArticleCardByPage")
    public APIResponse<ResponsePageBean<IndexArticleCardVO>> selectArticleCardByPage(@RequestParam(required = true) Integer channelId, int pageNum, int pageSize) {
        List<Article> list = null;
        if (channelId == 1) {
            // 说明是推荐列表
            list = articleService.queryIndexArticleCardByPageIncludeTop(pageNum, pageSize);
        } else {
            list = articleService.selectArticleCardByPage(channelId, pageNum, pageSize);
        }
        List<IndexArticleCardVO> resultList = BeanCopyUtils.convertListTo(list, IndexArticleCardVO::new, (s, t)->{
            t.setChannelName(articleService.queryChannelNameByChannelId(s.getChannelId()));
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

}
