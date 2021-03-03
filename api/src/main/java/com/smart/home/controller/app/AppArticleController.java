package com.smart.home.controller.app;

import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.article.ArticleBigImageCardVO;
import com.smart.home.controller.app.response.article.ArticleCardVO;
import com.smart.home.controller.app.response.article.SubjectCardVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.other.entity.SubjectCard;
import com.smart.home.modules.other.service.SubjectCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@Api(tags = "首页-文章")
@RestController
@RequestMapping("/api/app/article")
public class AppArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private SubjectCardService subjectCardService;

    @ApiOperation("获取推荐置顶大图")
    @AnonAccess
    @GetMapping("/queryIndexTopBigImageCard")
    public APIResponse<ArticleBigImageCardVO> queryIndexTopBigImageCard() {
        Article article = articleService.queryIndexTopBigImageCard();
        ArticleBigImageCardVO vo = new ArticleBigImageCardVO();
        BeanUtils.copyProperties(article, vo);
        return APIResponse.OK(vo);
    }

    @ApiOperation("获取推荐的文章卡片列表")
    @AnonAccess
    @GetMapping("/queryIndexArticleCard")
    public APIResponse<List<ArticleCardVO>> queryIndexArticleCard() {
        List<Article> list = articleService.queryIndexArticleCard();
        List<ArticleCardVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCardVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("根据频道主键id获取置顶大图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleChannelId", value = "频道主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryBigImageCardByChannelId")
    public APIResponse<ArticleBigImageCardVO> queryBigImageCardByChannelId(Integer articleChannelId) {
        Article article = articleService.queryBigImageCardByChannelId(articleChannelId);
        ArticleBigImageCardVO vo = new ArticleBigImageCardVO();
        BeanUtils.copyProperties(article, vo);
        return APIResponse.OK(vo);
    }

    @ApiOperation("获取其他频道的文章卡片列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleChannelId", value = "频道主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryArticleCardByChannelId")
    public APIResponse<List<ArticleCardVO>> queryArticleCardByChannelId(Integer articleChannelId) {
        List<Article> list = articleService.queryArticleCardByChannelId(articleChannelId);
        List<ArticleCardVO> resultList = BeanCopyUtils.convertListTo(list, ArticleCardVO::new);
        return APIResponse.OK(resultList);
    }

    @ApiOperation("获取专题卡片")
    @AnonAccess
    @GetMapping("/querySubjectCard")
    public APIResponse<SubjectCardVO> querySubjectCard() {
        SubjectCard subjectCard = subjectCardService.queryLatestSubjectCard();
        if (subjectCard == null) {
            return APIResponse.OK();
        }
        SubjectCardVO subjectCardVO = new SubjectCardVO();
        BeanUtils.copyProperties(subjectCard, subjectCardVO);
        return APIResponse.OK(subjectCardVO);
    }


}
