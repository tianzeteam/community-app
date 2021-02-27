package com.smart.home.controller.pc.request.user;

import com.smart.home.dto.RequestPageBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import java.util.List;

/**
 * @author jason
 * @date 2021/2/27
 **/
@Data
@ToString
public class UserAdminSearchDTO extends RequestPageBean {

    @ApiModelProperty("用户主键id数组")
    private List<Long> idList;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("权限标识")
    private String permitCode;

}
