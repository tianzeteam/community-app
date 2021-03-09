package com.smart.home.controller.pc.request.community;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author jason
 * @date 2021/2/26
 **/
@Data
@ToString
public class CommunityCreateDTO {

    @ApiModelProperty("所属社区类目主键id")
    @NotNull(message = "所属社区类目主键id不能为空")
    private Long categoryId;

    @ApiModelProperty("图标")
    @NotBlank(message = "图标不能为空")
    private String coverImage;

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String title;

    @ApiModelProperty("排序，越大越靠前")
    @NotNull(message = "排序不能为空")
    private Integer sort;
    @ApiModelProperty("是否置顶：0否1是")
    @NotNull(message = "是否置顶不能为空")
    private Integer topFlag;

    @ApiModelProperty("是否加精：0否1是")
    @NotNull(message = "是否加精不能为空")
    private Integer boutiqueFlag;

}
