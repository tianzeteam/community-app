package com.smart.home.controller.pc;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.controller.pc.response.ContentAuditResultHeadVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.product.service.ProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jason
 * @date 2021/3/5
 **/
@Api(tags = "管理后台-内容审核")
@RestController
@RequestMapping("/api/pc/contentAdmin")
public class ContentAdminAuditController {

    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ArticleCommentService articleCommentService;

    // TODO

    @ApiOperation("头部信息-文章评论")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @GetMapping("queryArticleCommentHeadInfo")
    public APIResponse<ContentAuditResultHeadVO> queryArticleCommentHeadInfo() {
        ContentAuditResultHeadVO vo = new ContentAuditResultHeadVO();
        vo.setWaitAuditCount(articleCommentService.countWaitAudit());
        vo.setAutoTextExceptionCount(articleCommentService.countTextException());
        vo.setAutoImageExceptionCount(articleCommentService.countImageException());
        vo.setHasReportCount(articleCommentService.countHasReport());
        vo.setHitSensitiveCount(articleCommentService.countHitSensitive());
        vo.setTotalNormalCount(articleCommentService.countTotalNormal());
        return APIResponse.OK(vo);
    }

    @ApiOperation("头部信息-产品评价")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @GetMapping("queryProductCommentHeadInfo")
    public APIResponse<ContentAuditResultHeadVO> queryProductCommentHeadInfo() {
        ContentAuditResultHeadVO vo = new ContentAuditResultHeadVO();
        vo.setWaitAuditCount(productCommentService.countWaitAudit());
        vo.setAutoTextExceptionCount(productCommentService.countTextException());
        vo.setAutoImageExceptionCount(productCommentService.countImageException());
        vo.setHasReportCount(productCommentService.countHasReport());
        vo.setHitSensitiveCount(productCommentService.countHitSensitive());
        vo.setTotalNormalCount(productCommentService.countTotalNormal());
        return APIResponse.OK(vo);
    }


}
