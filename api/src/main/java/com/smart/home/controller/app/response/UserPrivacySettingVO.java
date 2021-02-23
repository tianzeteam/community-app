package com.smart.home.controller.app.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Objects;

/**
 * @author jason
 * @date 2021/2/23
 **/
@Data
@ToString
public class UserPrivacySettingVO {

    @ApiModelProperty("隐私基础数据主键ID")
    private Integer privacyId;
    @ApiModelProperty("用户根据隐私基础设置的配置数据主键ID")
    private Long privacySettingId;
    @ApiModelProperty("隐私基础数据名称")
    private String privacyName;
    @ApiModelProperty("用户是否勾选0否1是")
    private Integer checkFlag;

    public Integer getCheckFlag() {
        if (Objects.isNull(checkFlag)) {
            return 0;
        }
        return checkFlag;
    }

}
