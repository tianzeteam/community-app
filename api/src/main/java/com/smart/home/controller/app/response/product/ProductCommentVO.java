package com.smart.home.controller.app.response.product;

import com.smart.home.dto.ResponsePageBean;
import com.smart.home.util.ResponsePageUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
/**
 * @author jason
 * @date 2021/2/25
 **/
@Data
@ToString
public class ProductCommentVO {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("用户头像")
    private String headUrl;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户等级")
    private Integer userLevel;
    @ApiModelProperty("评价星等")
    private BigDecimal starCount;
    @ApiModelProperty("评价内容")
    private String details;
    @ApiModelProperty("图片数组")
    private List<String> imageList;
    @ApiModelProperty("有趣数量")
    private Integer funCount;
    @ApiModelProperty("赞数量")
    private Integer likeCount;
    @ApiModelProperty("踩数量")
    private Integer stampCount;

    @ApiModelProperty("点过有趣0否1是-登陆后才有值")
    private Integer funFlag;
    @ApiModelProperty("点过赞0否1是-登陆后才有值")
    private Integer likeFlag;
    @ApiModelProperty("点过踩0否1是-登陆后才有值")
    private Integer stampFlag;

    @ApiModelProperty("回复列表-分页")
    private ResponsePageBean<ProductCommentReplyVO> replyResultList;

}
