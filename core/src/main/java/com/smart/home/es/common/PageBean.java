package com.smart.home.es.common;


import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageBean<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalCount;
    private List<T> list;

    public static PageBean info2Bean(PageInfo pageInfo, List list){
        PageBean pageBean = new PageBean();
        pageBean.setTotalPage(pageInfo.getPages());
        pageBean.setPageNum(pageInfo.getPageNum());
        pageBean.setPageSize(pageInfo.getPageSize());
        pageBean.setList(list);
        pageBean.setTotalCount(pageInfo.getTotal());
        return pageBean;
    }

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
