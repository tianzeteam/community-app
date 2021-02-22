package com.smart.home.common.exception;

import com.smart.home.common.enums.APIResponseCodeEnum;

/**
 * @author jason
 * @date 2021/2/18
 **/
public class AuthorizationException extends RuntimeException  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int code;

    public AuthorizationException(String message) {
        super(message);
        this.code = APIResponseCodeEnum.NO_AUTH.getCode();
    }

    public int getCode() {
        return code;
    }

}
