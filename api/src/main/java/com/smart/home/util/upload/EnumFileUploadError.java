package com.smart.home.util.upload;

public enum EnumFileUploadError {

    FILE_IS_EMPTY("文件为空"),
    FILE_IS_EXISTS("文件已经存在"),
    INVALID_FILE_TYPE("非法的文件格式");

    private String message;

    EnumFileUploadError(String message) {
        this.message = message;
    }

    public String getMessage() {
        //TODO 以后这里可以通过i18n的code转换为真实的语言
        return this.message;
    }

}
