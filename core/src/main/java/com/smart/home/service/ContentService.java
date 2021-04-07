package com.smart.home.service;

import com.github.pagehelper.PageHelper;
import com.smart.home.dto.ContentAdminAuditApproveTO;
import com.smart.home.dto.ContentAdminAuditSearchTO;
import com.smart.home.dto.ContentAuditAdminRecordTO;
import com.smart.home.enums.ContentTypeEnum;
import com.smart.home.modules.article.service.ArticleCommentReplyService;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.product.service.ProductCommentReplyService;
import com.smart.home.modules.product.service.ProductCommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author jason
 * @date 2021/3/5
 **/

@Log4j2
@Service
public class ContentService {

    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductCommentReplyService productCommentReplyService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleCommentReplyService articleCommentReplyService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;

    public void manuallyReject(List<ContentAdminAuditApproveTO> toList) {
        for (ContentAdminAuditApproveTO contentAdminAuditApproveTO : toList) {
            Long id = contentAdminAuditApproveTO.getId();
            try {
                if(ContentTypeEnum.ARTICLE_COMMENT.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    articleCommentService.manuallyReject(id);
                    continue;
                }
                if(ContentTypeEnum.PRODUCT_COMMENT.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    productCommentService.manuallyReject(id);
                    continue;
                }
                if(ContentTypeEnum.COMMUNITY_POST.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    communityPostService.manuallyReject(id);
                    continue;
                }
                if(ContentTypeEnum.COMMUNITY_POST_REPLY.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    communityPostReplyService.manuallyReject(id);
                    continue;
                }
                if (ContentTypeEnum.ARTICLE_COMMENT_REPLY.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    articleCommentReplyService.manuallyReject(id);
                    continue;
                }
                if (ContentTypeEnum.PRODUCT_COMMENT_REPLY.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    productCommentReplyService.manuallyReject(id);
                    continue;
                }
            } catch (Throwable e) {
                log.error(e);
            }
        }
    }

    public void manuallyApprove(List<ContentAdminAuditApproveTO> toList) {
        for (ContentAdminAuditApproveTO contentAdminAuditApproveTO : toList) {
            Long id = contentAdminAuditApproveTO.getId();
            try {
                if(ContentTypeEnum.ARTICLE_COMMENT.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    articleCommentService.manuallyApprove(id);
                    continue;
                }
                if(ContentTypeEnum.PRODUCT_COMMENT.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    productCommentService.manuallyApprove(id);
                    continue;
                }
                if(ContentTypeEnum.COMMUNITY_POST.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    communityPostService.manuallyApprove(id);
                    continue;
                }
                if(ContentTypeEnum.COMMUNITY_POST_REPLY.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    communityPostReplyService.manuallyApprove(id);
                    continue;
                }
                if (ContentTypeEnum.ARTICLE_COMMENT_REPLY.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    articleCommentReplyService.manuallyApprove(id);
                    continue;
                }
                if (ContentTypeEnum.PRODUCT_COMMENT_REPLY.getCode() == contentAdminAuditApproveTO.getContentType()) {
                    productCommentReplyService.manuallyApprove(id);
                    continue;
                }
            } catch (Throwable e) {
                log.error(e);
            }
        }
    }

    public List<ContentAuditAdminRecordTO> selectNeedAuditContent(ContentAdminAuditSearchTO to) {
        Integer pageSize = to.getPageSize();
        if (Objects.isNull(pageSize) || pageSize.intValue() == 0) {
            pageSize = 10;
        }
        if (CollectionUtils.isEmpty(to.getContentTypeList())) {
            to.setContentTypeList(null);
        }
        PageHelper.startPage(1, pageSize);
        List<ContentAuditAdminRecordTO> list = productCommentService.selectAllNeedAuditContent(to);
        PageHelper.clearPage();
        return list;
    }
}
