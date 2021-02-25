package com.smart.home.common.bean;

/**
 * @author jason
 * 分页请求
 */
public class RequestPageBean {

    private int pageNum;

    private int pageSize = 10;

    public int getPageNum() {
        return pageNum + 1;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        this.pageNum = pageNum - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        this.pageSize = pageSize;
    }

}
