package com.smart.home.service;

import com.alibaba.fastjson.JSON;
import com.smart.home.common.enums.YesNoEnum;
import com.smart.home.enums.MessageSubTypeEnum;
import com.smart.home.enums.MessageTypeEnum;
import com.smart.home.modules.article.entity.Article;
import com.smart.home.modules.article.service.ArticleService;
import com.smart.home.modules.community.entity.CommunityPost;
import com.smart.home.modules.community.service.CommunityPostReplyService;
import com.smart.home.modules.community.service.CommunityPostService;
import com.smart.home.modules.message.entity.MessageContent;
import com.smart.home.modules.message.service.MessageContentService;
import com.smart.home.modules.user.entity.UserAccount;
import com.smart.home.modules.user.service.UserAccountService;
import com.smart.home.modules.user.service.UserDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * @author jason
 * @date 2021/3/11
 **/
@Service
public class MessageService {

    @Autowired
    private MessageContentService messageContentService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private CommunityPostReplyService communityPostReplyService;

    /**
     * 各模块点赞需要调用的接口
     * @param messageSubTypeEnum
     * @param sourceId
     * @param fromUserId
     * @param toUserId
     */
    public void createLikeMessage(MessageSubTypeEnum messageSubTypeEnum,Long sourceId, Long fromUserId, Long toUserId) {
        CompletableFuture.runAsync(()->{
            UserAccount fromUser = userAccountService.findUserByUserId(fromUserId);
            MessageContent messageContent = new MessageContent();
            messageContent.withMessageType(MessageTypeEnum.LIKE.getType())
                    .withSourceId(sourceId)
                    .withSenderId(fromUserId)
                    .withReceiverId(toUserId)
                    .withHeadUrl(fromUser.getHeadUrl())
                    .withNickName(fromUser.getNickName())
                    .withMessageContent("点赞了你的"+messageSubTypeEnum.getDesc())
                    .withCreatedTime(new Date())
                    .withMessageSubType(messageSubTypeEnum.getCode())
                    .withReadFlag(YesNoEnum.NO.getCode())
                    .withDeleteFlag(YesNoEnum.NO.getCode());
            switch (messageSubTypeEnum) {
                case ARTICLE_COMMENT:
                case PRODUCT_COMMENT:
                case COMMUNITY_POST_REPLY:
                    messageContent.withTitle("我的"+messageSubTypeEnum.getDesc()+"原文");
                    break;
                case ARTICLE:
                    Article article = articleService.findById(sourceId);
                    messageContent.withCoverImage(article.getCoverImage())
                            .withTitle(article.getTitle());
                    break;
                case COMMUNITY_POST:
                    CommunityPost communityPost = communityPostService.findById(sourceId);
                    String coverImage = null;
                    if (StringUtils.isNotBlank(communityPost.getImages())) {
                        coverImage = JSON.parseArray(communityPost.getImages(), String.class).get(0);
                    }
                    messageContent.withCoverImage(coverImage).withTitle(communityPost.getTitle());
                    break;
                default:
                    break;
            }
            messageContentService.create(messageContent);
        });
    }

}
