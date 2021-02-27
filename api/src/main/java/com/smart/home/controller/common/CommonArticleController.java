package com.smart.home.controller.common;

import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.controller.common.response.ArticleDetailVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Api(tags = "文章页-文章详情")
@RestController
@RequestMapping("/api/common/article")
public class CommonArticleController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("根据文章主键id获取详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryDetailById")
    public APIResponse<ArticleDetailVO> queryDetailById(Long articleId) {
        ArticleDetailVO articleDetailVO = new ArticleDetailVO();
        Long userId = UserUtils.getLoginUserId();
        if (userId > 0) {
            // 说明是登陆用户
            Article article = articleService.queryDetailByIdWhenLogin(articleId, userId);
            BeanUtils.copyProperties(article, articleDetailVO);
        } else {
            Article article = articleService.queryDetailByIdNoLogin(articleId);
            BeanUtils.copyProperties(article, articleDetailVO);
            articleDetailVO.setCollectFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setFocusFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setLikeFlag(YesNoEnum.NO.getCode());
            articleDetailVO.setStampFlag(YesNoEnum.NO.getCode());
        }
        return APIResponse.OK(articleDetailVO);
    }

}
