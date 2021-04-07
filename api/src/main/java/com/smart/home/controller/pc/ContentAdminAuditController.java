package com.smart.home.controller.pc;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.pc.request.ContentAdminAuditApproveDTO;
import com.smart.home.controller.pc.request.ContentAdminAuditSearchDTO;
import com.smart.home.controller.pc.response.ContentAuditAdminRecordVO;
import com.smart.home.controller.pc.response.ContentAuditResultHeadVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ContentAdminAuditApproveTO;
import com.smart.home.dto.ContentAdminAuditSearchTO;
import com.smart.home.dto.ContentAuditAdminRecordTO;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.ContentTypeEnum;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.product.service.ProductCommentService;
import com.smart.home.service.ContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;
    @Autowired
    private ContentService contentService;

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

    @ApiOperation("头部信息-帖子")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @GetMapping("queryPostHeadInfo")
    public APIResponse<ContentAuditResultHeadVO> queryPostHeadInfo() {
        ContentAuditResultHeadVO vo = new ContentAuditResultHeadVO();
        vo.setWaitAuditCount(communityPostService.countWaitAudit());
        vo.setAutoTextExceptionCount(communityPostService.countTextException());
        vo.setAutoImageExceptionCount(communityPostService.countImageException());
        vo.setHasReportCount(communityPostService.countHasReport());
        vo.setHitSensitiveCount(communityPostService.countHitSensitive());
        vo.setTotalNormalCount(communityPostService.countTotalNormal());
        return APIResponse.OK(vo);
    }

    @ApiOperation("头部信息-回帖")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @GetMapping("queryPostReplyHeadInfo")
    public APIResponse<ContentAuditResultHeadVO> queryPostReplyHeadInfo() {
        ContentAuditResultHeadVO vo = new ContentAuditResultHeadVO();
        vo.setWaitAuditCount(communityPostReplyService.countWaitAudit());
        vo.setAutoTextExceptionCount(communityPostReplyService.countTextException());
        vo.setAutoImageExceptionCount(communityPostReplyService.countImageException());
        vo.setHasReportCount(communityPostReplyService.countHasReport());
        vo.setHitSensitiveCount(communityPostReplyService.countHitSensitive());
        vo.setTotalNormalCount(communityPostReplyService.countTotalNormal());
        return APIResponse.OK(vo);
    }

    @ApiOperation("加载待审核内容")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/selectNeedAuditContent")
    public APIResponse<List<ContentAuditAdminRecordVO>> selectNeedAuditContent(@Valid @RequestBody ContentAdminAuditSearchDTO contentAdminAuditSearchDTO, BindingResult bindingResult) {
        ContentAdminAuditSearchTO to = BeanCopyUtils.convertTo(contentAdminAuditSearchDTO, ContentAdminAuditSearchTO::new,(s, t)->{
            List<Integer> contentList = s.getContentType();
            if (CollUtil.isNotEmpty(contentList)) {
                if (contentList.contains(ContentTypeEnum.ARTICLE_COMMENT.getCode())) {
                    contentList.add(ContentTypeEnum.ARTICLE_COMMENT_REPLY.getCode());
                }
                if (contentList.contains(ContentTypeEnum.PRODUCT_COMMENT.getCode())) {
                    contentList.add(ContentTypeEnum.PRODUCT_COMMENT_REPLY.getCode());
                }
            }
            t.setContentTypeList(contentList);

        });
        List<ContentAuditAdminRecordTO> list = contentService.selectNeedAuditContent(to);
        List<ContentAuditAdminRecordVO> resultList = BeanCopyUtils.convertListTo(list, ContentAuditAdminRecordVO::new, (s,t)->{
            if (StringUtils.isNotBlank(s.getImages())) {
                t.setImageList(JSON.parseArray(s.getImages(), String.class));
            }
        });
        return APIResponse.OK(resultList);
    }

    @ApiOperation("认证正常-支持批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/approveAuditContent")
    public APIResponse approveAuditContent(@RequestBody List<ContentAdminAuditApproveDTO> contentAdminAuditApproveDTOList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ContentAdminAuditApproveTO> toList = BeanCopyUtils.convertListTo(contentAdminAuditApproveDTOList, ContentAdminAuditApproveTO::new);
                contentService.manuallyApprove(toList);
            }
        }).start();
        return APIResponse.OK();
    }

    @ApiOperation("认证违规-支持批量")
    @RoleAccess({RoleConsts.ADMIN, RoleConsts.AUDITOR})
    @PostMapping("/rejectAuditContent")
    public APIResponse rejectAuditContent(@RequestBody List<ContentAdminAuditApproveDTO> contentAdminAuditApproveDTOList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ContentAdminAuditApproveTO> toList = BeanCopyUtils.convertListTo(contentAdminAuditApproveDTOList, ContentAdminAuditApproveTO::new);
                contentService.manuallyReject(toList);
            }
        }).start();
        return APIResponse.OK();
    }

}
