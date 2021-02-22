package com.smart.home.common.exception;

import com.smart.home.common.enums.APIResponseCodeEnum;

/**
 * @author jason
 * @date 2021/2/21
 **/
public class DuplicateDataException extends ServiceException {

    public DuplicateDataException(String message) {
        super(APIResponseCodeEnum.ERROR_DUPLICATE_DATA.getCode(), message);
    }

}
