package com.smart.home.controller.app.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author jason
 * @date 2021/4/17
 *  @ApiImplicitParam(name = "data", value = "{\n" +
 *                     "     * \"openId\": \"oRrdQt4h823nm3Xf79OFEiKUjD90\",\n" +
 *                     "     * \"nickName\": \"阿白\",\n" +
 *                     "     * \"gender\": 0,\n" +
 *                     "     * \"city\": \"\",\n" +
 *                     "     * \"province\": \"\",\n" +
 *                     "     * \"country\": \"\",\n" +
 *                     "     * \"avatarUrl\": \"https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM6PoXL9wZ7rTb9niaicokPwiaAH0WibvZXSFLkSQuBPg4d5OkklibP8HAYosuqjzJIiaZtxDWtFrSnRibdGg/132\",\n" +
 *                     "     * \"unionId\": \"oU5Yyt449NKWxoTJ3ohIAdcIjRoI\"\n" +
 *                     "     * }", required = true)
 **/
@Data
@ToString
public class WechatLoginRequest {

    @ApiModelProperty("微信openid")
    private String opendId;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String avatarUrl;

}
