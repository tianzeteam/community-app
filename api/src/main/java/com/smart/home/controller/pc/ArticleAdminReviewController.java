package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.enums.AuditStatusEnum;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.artcile.ArticleRecommendOnDTO;
import com.smart.home.controller.pc.response.article.ArticleAdminRejectDTO;
import com.smart.home.controller.pc.response.article.ArticleAdminReviewPageVO;
import com.smart.home.controller.pc.response.article.ArticleAdminReviewSearchDTO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.ArticleStateEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Api(tags = "投稿管理-过审稿件")
@RestController
@RequestMapping("/api/pc/articleAdminReview")
public class ArticleAdminReviewController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation("分页查询文章")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/selectByPage")
    public APIResponse<ResponsePageBean<ArticleAdminReviewPageVO>> selectByPage(@RequestBody ArticleAdminReviewSearchDTO articleAdminSearchDTO) {
        Article article = new Article();
        int pageNum = articleAdminSearchDTO.getPageNum();
        int pageSize = articleAdminSearchDTO.getPageSize();
        String sortType = articleAdminSearchDTO.getSortType();
        String sortField = articleAdminSearchDTO.getSortField();
        BeanUtils.copyProperties(articleAdminSearchDTO, article);
        // 只查发布的
        article.setState(ArticleStateEnum.PUBLISH.getState());
        // 只查过审的
        article.setAuditState(AuditStatusEnum.APPROVED.getCode());
        List<Article> list = articleService.selectByPage(article, pageNum, pageSize, sortType, sortField);
        List<ArticleAdminReviewPageVO> resultList = BeanCopyUtils.convertListTo(list, ArticleAdminReviewPageVO::new, (s, t) -> {
            t.setAuthorId(s.getUserId());
            t.setArticleId(s.getId());
        });
        return APIResponse.OK(ResponsePageBean.restPage(resultList));
    }

    @ApiOperation("按文章主键id查详细内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章主键id", required = true)
    })
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @GetMapping("/selectById")
    public APIResponse<Article> selectById(Long articleId) {
        return APIResponse.OK(articleService.findById(articleId));
    }

    @ApiOperation("操作推荐-支持单个和批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/recommend")
    public APIResponse approve(@RequestBody List<ArticleRecommendOnDTO> articleRecommendOnDTOList) {
        Long userId = UserUtils.getLoginUserId();
        Map<Long, Integer> map = new HashMap<>();
        for (ArticleRecommendOnDTO articleRecommendOnDTO : articleRecommendOnDTOList) {
            map.put(articleRecommendOnDTO.getArticleId(), articleRecommendOnDTO.getRecommendType());
        }
        articleService.recommend(map, userId);
        return APIResponse.OK();
    }
    @ApiOperation("操作取消推荐-支持单个和批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelRecommend")
    public APIResponse reject(@RequestBody ArticleAdminRejectDTO articleAdminRejectDTO) {
        Long userId = UserUtils.getLoginUserId();
        articleService.cancelRecommend(articleAdminRejectDTO.getIdList(), userId);
        return APIResponse.OK();
    }

    @ApiOperation("操作撤稿-支持单个和批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/offLine")
    public APIResponse offLine(@RequestBody IdListBean idListBean) {
        Long userId = UserUtils.getLoginUserId();
        articleService.offLine(idListBean.getIdList(), userId);
        return APIResponse.OK();
    }
    @ApiOperation("操作取消撤稿-支持单个和批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/cancelOffLine")
    public APIResponse cancelOffLine(@RequestBody ArticleAdminRejectDTO articleAdminRejectDTO) {
        Long userId = UserUtils.getLoginUserId();
        articleService.cancelOffLine(articleAdminRejectDTO.getIdList(), userId);
        return APIResponse.OK();
    }

}
