package com.smart.home.controller.app;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.smart.home.common.contants.RoleConsts;
import com.smart.home.common.exception.ServiceException;
import com.smart.home.common.util.BeanCopyUtils;
import com.smart.home.controller.app.request.PrimaryKeyPageDTO;
import com.smart.home.controller.app.request.ProductCommentCreateDTO;
import com.smart.home.controller.app.request.ProductCommentReplyCreateDTO;
import com.smart.home.controller.app.response.product.ProductCommentReplyVO;
import com.smart.home.controller.app.response.product.ProductCommentVO;
import com.smart.home.controller.app.response.product.ProductHeadInfoVO;
import com.smart.home.controller.app.response.product.ProductPageCommentTabHeadVO;
import com.smart.home.dto.APIResponse;
import com.smart.home.dto.ResponsePageBean;
import com.smart.home.dto.auth.annotation.AnonAccess;
import com.smart.home.dto.auth.annotation.RoleAccess;
import com.smart.home.enums.FunCategoryEnum;
import com.smart.home.enums.LikeCategoryEnum;
import com.smart.home.enums.MessageSubTypeEnum;
import com.smart.home.enums.StampCategoryEnum;
import com.smart.home.modules.product.entity.Product;
import com.smart.home.modules.product.entity.ProductComment;
import com.smart.home.modules.product.entity.ProductCommentReply;
import com.smart.home.modules.product.service.ProductCommentReplyService;
import com.smart.home.modules.product.service.ProductCommentService;
import com.smart.home.modules.product.service.ProductService;
import com.smart.home.service.FunService;
import com.smart.home.service.LikeService;
import com.smart.home.service.MessageService;
import com.smart.home.service.StampService;
import com.smart.home.util.ResponsePageUtil;
import com.smart.home.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@Api(tags = "产品库-产品评价")
@RestController
@RequestMapping("/api/app/productComment")
public class AppProductCommentController {

    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductCommentReplyService productCommentReplyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StampService stampService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FunService funService;
    @Autowired
    private MessageService messageService;

    @ApiOperation("获取顶部综合评分信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品主键id", required = true)
    })
    @AnonAccess
    @GetMapping("/queryHeadSummaryScore")
    public APIResponse<ProductPageCommentTabHeadVO> queryHeadSummaryScore(Integer productId) {
        Product product = productService.queryProductCommentCountInfo(productId);
        ProductPageCommentTabHeadVO vo = BeanCopyUtils.convertTo(product, ProductPageCommentTabHeadVO::new, (s, t)->{
            // 保留一位小数
            BigDecimal score = s.getAverageScore().setScale(1,  BigDecimal.ROUND_HALF_UP);
            t.setAverageScore(score);
        });
        return APIResponse.OK(vo);
    }

    @ApiOperation("获取产品评价卡片下的评价列表-分页")
    @AnonAccess
    @PostMapping("/queryCommentByPage")
    public APIResponse<ResponsePageBean<ProductCommentVO>> queryCommentByPage(@Valid @RequestBody PrimaryKeyPageDTO primaryKeyPageDTO, BindingResult bindingResult) {
        Long userId = UserUtils.getLoginUserId();
        int productId = primaryKeyPageDTO.getId().intValue();
        int pageNum = primaryKeyPageDTO.getPageNum();
        int pageSize = primaryKeyPageDTO.getPageSize();
        List<ProductComment> list = productCommentService.queryCommentByPage(userId, productId, pageNum, pageSize);
        List<ProductCommentVO> resultList = BeanCopyUtils.convertListTo(list, ProductCommentVO::new, (s, t) -> {
            t.setFunFlag(s.getFunId() == null ? 0 : 1);
            t.setLikeFlag(s.getLikeId() == null ? 0 : 1);
            t.setStampFlag(s.getStampId() == null ? 0 : 1);
            if (StringUtils.isNotBlank(s.getImages())) {
                t.setImageList(JSON.parseArray(s.getImages(), String.class));
            }
        });
        if (CollUtil.isNotEmpty(resultList)) {
            for (ProductCommentVO productCommentVO : resultList) {
                List<ProductCommentReply> replyList = productCommentReplyService.queryCommentDetailReplyByPage(userId, productCommentVO.getId(), 0L, pageNum, 5);
                List<ProductCommentReplyVO> replyResultList = BeanCopyUtils.convertListTo(replyList, ProductCommentReplyVO::new, (s,t)->{
                    t.setLikeFlag(s.getLikeId() == null ? 0 : 1);
                    t.setStampFlag(s.getStampId() == null ? 0 : 1);
                });
                productCommentVO.setReplyResultList(ResponsePageUtil.restPage(replyResultList, replyList));
            }
        }
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("发表评价")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("createComment")
    public APIResponse createComment(@Valid @RequestBody ProductCommentCreateDTO productCommentCreateDTO, BindingResult bindingResult) {
        Integer productId = productCommentCreateDTO.getProductId();
        String details = productCommentCreateDTO.getDetails();
        Long userId = UserUtils.getLoginUserId();
        BigDecimal starCount = productCommentCreateDTO.getStartCount();
        try {
            productCommentService.create(userId, starCount, details,productId, productCommentCreateDTO.getImageList());
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("评价详情-顶部信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCommentId",value = "评价主键ID", required = true)
    })
    @AnonAccess
    @GetMapping("/queryCommentDetailHead")
    public APIResponse<ProductCommentVO> queryCommentDetailHead(@RequestParam(required = true) Long productCommentId) {
        Long userId = UserUtils.getLoginUserId();
        ProductComment productComment = productCommentService.queryCommentDetailHead(userId, productCommentId);
        ProductCommentVO vo = BeanCopyUtils.convertTo(productComment, ProductCommentVO::new, (s, t) -> {
            t.setFunFlag(s.getFunId() == null ? 0 : 1);
            t.setLikeFlag(s.getLikeId() == null ? 0 : 1);
            t.setStampFlag(s.getStampId() == null ? 0 : 1);
            if(StringUtils.isNotBlank(s.getImages())) {
                t.setImageList(JSON.parseArray(s.getImages(), String.class));
            }
        });
        int productId = productComment.getProductId();
        Product product = productService.findById(productId);
        ProductHeadInfoVO productHeadInfoVO = new ProductHeadInfoVO();
        BeanUtils.copyProperties(product, productHeadInfoVO);
        vo.setProductHeadInfoVO(productHeadInfoVO);
        return APIResponse.OK(vo);
    }

    @ApiOperation("评价的回复-回复列表-分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCommentId",value = "评价主键ID", required = true),
            @ApiImplicitParam(name = "pid",value = "父级回复主键id，一级传0", required = true),
            @ApiImplicitParam(name = "pageNum",value = "分页页码", required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页数量", required = true)
    })
    @AnonAccess
    @GetMapping("/queryCommentDetailReplyByPage")
    public APIResponse<ResponsePageBean<ProductCommentReplyVO>> queryCommentDetailReplyByPage(Long productCommentId,  Long pid, int pageNum, int pageSize) {
        Long userId = UserUtils.getLoginUserId();
        List<ProductCommentReply> list = productCommentReplyService.queryCommentDetailReplyByPage(userId, productCommentId, pid, pageNum, pageSize);
        List<ProductCommentReplyVO> resultList = BeanCopyUtils.convertListTo(list, ProductCommentReplyVO::new, (s,t)->{
            t.setLikeFlag(s.getLikeId() == null ? 0 : 1);
            t.setStampFlag(s.getStampId() == null ? 0 : 1);
        });
        return APIResponse.OK(ResponsePageUtil.restPage(resultList, list));
    }

    @ApiOperation("产品评价-点有用/取消点有用-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCommentId",value = "评价主键ID", required = true),
            @ApiImplicitParam(name = "authorId",value = "评价人的主键ID", required = true),
            @ApiImplicitParam(name = "action",value = "0点有用1取消点有用", required = true)

    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/clickOrCancelClickUseful")
    public APIResponse clickOrCancelClickUseful(Long productCommentId, Long authorId, Integer action) {
        try {
            if (action == 0) {
                Long fromUserId = UserUtils.getLoginUserId();
                likeService.like(LikeCategoryEnum.PRODUCT_COMMENT, fromUserId, productCommentId);
                messageService.createLikeMessage(MessageSubTypeEnum.PRODUCT_COMMENT, productCommentId, fromUserId, authorId);
            } else {
                likeService.cancelLike(LikeCategoryEnum.PRODUCT_COMMENT, UserUtils.getLoginUserId(), productCommentId);

            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("产品评价-点无用/取消点无用-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCommentId",value = "评价主键ID", required = true),
            @ApiImplicitParam(name = "action",value = "0点无用1取消点无用", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/clickOrCancelClickUseless")
    public APIResponse clickOrCancelClickUseless(Long productCommentId, Integer action) {
        try {
            if (action == 0) {
                stampService.stamp(StampCategoryEnum.PRODUCT_COMMENT, UserUtils.getLoginUserId(), productCommentId);
            } else {
                stampService.cancelStamp(StampCategoryEnum.PRODUCT_COMMENT, UserUtils.getLoginUserId(), productCommentId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("产品评价-点有趣/取消点有趣-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCommentId",value = "评价主键ID", required = true),
            @ApiImplicitParam(name = "action",value = "0点有趣1取消点有趣", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/clickOrCancelFun")
    public APIResponse clickOrCancelFun(Long productCommentId, Integer action) {
        try {
            if (action == 0) {
                funService.fun(FunCategoryEnum.PRODUCT_COMMENT, UserUtils.getLoginUserId(), productCommentId);
            } else {
                funService.cancelFun(FunCategoryEnum.PRODUCT_COMMENT, UserUtils.getLoginUserId(), productCommentId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("评价的回复-回复别人的评价")
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/createCommentReply")
    public APIResponse createCommentReply(@Valid @RequestBody ProductCommentReplyCreateDTO productCommentReplyCreateDTO, BindingResult bindingResult) {
        Long productCommentId = productCommentReplyCreateDTO.getProductCommentId();
        String details = productCommentReplyCreateDTO.getDetails();
        Long userId = UserUtils.getLoginUserId();
        Long pid = productCommentReplyCreateDTO.getPid();
        Long productCommentAuthorId = productCommentReplyCreateDTO.getProductCommentAuthorId();
        productCommentReplyService.create(userId, details, productCommentId, pid);
        messageService.createReplyMessage(MessageSubTypeEnum.PRODUCT_COMMENT, productCommentId, userId, productCommentAuthorId, details);
        return APIResponse.OK();
    }

    @ApiOperation("评价的回复-点赞/取消赞别人的回复-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCommentReplyId", value = "产品回复主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0点赞1取消点赞", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/likeOrCancelLikeCommentReply")
    public APIResponse likeOrCancelLikeCommentReply(Long productCommentReplyId, Integer action) {
        try {
            if (action == 0) {
                likeService.like(LikeCategoryEnum.PRODUCT_REPLY, UserUtils.getLoginUserId(), productCommentReplyId);
            } else {
                likeService.cancelLike(LikeCategoryEnum.PRODUCT_REPLY, UserUtils.getLoginUserId(), productCommentReplyId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

    @ApiOperation("评价的回复-点踩/取消点踩别人的回复-二合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productCommentReplyId", value = "产品回复主键id", required = true),
            @ApiImplicitParam(name = "action", value = "0点踩1取消点踩", required = true)
    })
    @RoleAccess(RoleConsts.REGISTER)
    @PostMapping("/stampOrCancelStampCommentReply")
    public APIResponse stampOrCancelStampCommentReply(Long productCommentReplyId, Integer action) {
        try {
            if (action == 0) {
                stampService.stamp(StampCategoryEnum.PRODUCT_REPLY, UserUtils.getLoginUserId(), productCommentReplyId);
            } else {
                stampService.cancelStamp(StampCategoryEnum.PRODUCT_REPLY, UserUtils.getLoginUserId(), productCommentReplyId);
            }
        } catch (ServiceException e) {
            return APIResponse.ERROR(e.getMessage());
        }
        return APIResponse.OK();
    }

}
