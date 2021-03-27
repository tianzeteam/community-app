package com.smart.home.dto;

import com.smart.home.util.ResponsePageUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

/**
 * @author jason
 * @param <T>
 */
public class ResponsePageBean<T> {
    @ApiModelProperty("分页页码")
    private Integer pageNum;
    @ApiModelProperty("每页数据")
    private Integer pageSize;
    @ApiModelProperty("总页数")
    private Integer totalPage;
    @ApiModelProperty("总数据量")
    private Long totalCount;
    @ApiModelProperty("分页数据")
    private List<T> list = Collections.EMPTY_LIST;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

}
