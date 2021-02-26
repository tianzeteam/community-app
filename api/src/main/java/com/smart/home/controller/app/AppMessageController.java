package com.smart.home.controller.app;

import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.response.message.MessageLikeVO;
import com.smart.home.controller.app.response.message.MessageNotifyVO;
import com.smart.home.controller.app.response.message.MessageReplyVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.IdListBean;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.MessageTypeEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.entity.ArticleComment;
import com.smart.home.modules.article.service.ArticleCommentService;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.message.entity.MessageContent;
import com.smart.home.modules.message.service.MessageContentService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author jason
 * @date 2021/2/26
 * 消息排序规则：最后回复时间由新到旧
 **/
@Api(tags = "消息")
@RestController
@RequestMapping("/api/app/message")
public class AppMessageController {

    @Autowired
    private MessageContentService messageContentService;
    @Autowired
    private ArticleCommentService articleCommentService;
    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取赞列表消息-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryLikeMessageByPage")
    public APIResponse<ResponsePageBean<MessageLikeVO>> queryLikeMessageByPage(int pageNum, int pageSize) {
        List<MessageContent> list = messageContentService.queryMessageByPage(MessageTypeEnum.LIKE, UserUtils.getLoginUserId(),pageNum, pageSize);
        List<MessageLikeVO> resultList = BeanCopyUtils.convertListTo(list, MessageLikeVO::new,(s,t)->{
            t.setUserId(s.getSenderId());
            t.setLikeCategory(s.getMessageSubType());
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }
    @ApiOperation("获取赞列表消息ACK-用户删除服务器消息数据")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/queryLikeMessageByPageACK")
    public APIResponse queryLikeMessageByPageACK(@RequestBody IdListBean idListBean) {
        messageContentService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("获取回复列表消息-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryReplyMessageByPage")
    public APIResponse<ResponsePageBean<MessageReplyVO>> queryReplyMessageByPage(int pageNum, int pageSize) {
        List<MessageContent> list = messageContentService.queryMessageByPage(MessageTypeEnum.REPLY_ME, UserUtils.getLoginUserId(),pageNum, pageSize);
        List<MessageReplyVO> resultList = BeanCopyUtils.convertListTo(list, MessageReplyVO::new,(s,t)->{
            t.setUserId(s.getSenderId());
            t.setReplyCategory(s.getMessageSubType());
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("获取回复列表消息ACK-用户删除服务器消息数据")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/queryReplyMessageByPageACK")
    public APIResponse queryReplyMessageByPageACK(@RequestBody IdListBean idListBean) {
        messageContentService.delete(idListBean.getIdList());
        return APIResponse.OK();
    }

    @ApiOperation("获取通知列表消息-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @GetMapping("/queryNotifyMessageByPage")
    public APIResponse<ResponsePageBean<MessageNotifyVO>> queryNotifyMessageByPage(int pageNum, int pageSize) {
        List<MessageContent> list = messageContentService.queryMessageByPage(MessageTypeEnum.NOTIFY, UserUtils.getLoginUserId(),pageNum, pageSize);
        List<MessageNotifyVO> resultList = BeanCopyUtils.convertListTo(list, MessageNotifyVO::new);
        return APIResponse.OK(ResponsePageUtil.restPage(resultList));
    }

    @ApiOperation("获取通知列表消息ACK-用户删除服务器消息数据")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/queryNotifyMessageByPageACK")
    public APIResponse queryNotifyMessageByPageACK(@RequestBody IdListBean idListBean) {
        messageContentService.markRead(idListBean.getIdList(), UserUtils.getLoginUserId());
        return APIResponse.OK();
    }

    @ApiOperation("跳转我的评论原文")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "原文主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/gotoMyArticleCommentPage")
    public APIResponse gotoMyArticleCommentPage(Long sourceId) {
        ArticleComment articleComment = articleCommentService.findById(sourceId);
        // TODO 还不知道前端要什么样的数据呢
        return APIResponse.OK(articleComment);
    }
    @ApiOperation("跳转我的文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceId", value = "原文主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/gotoMyArticlePage")
    public APIResponse gotoMyArticlePage(Long sourceId) {
        Article article = articleService.findById(sourceId);
        // TODO 还不知道前端要什么样的数据呢
        return APIResponse.OK(article);
    }

}