package com.smart.home.dto;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author jason
 * @param <T>
 */
public class ResponsePageBean<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long totalCount;
    private List<T> list;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> ResponsePageBean<T> restPage(List<T> list) {
        ResponsePageBean<T> result = new ResponsePageBean<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalCount(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        PageHelper.clearPage();
        return result;
    }

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> ResponsePageBean<T> restPage(List<T> list, ResponsePageBean tempResPageHelperBean) {
        ResponsePageBean<T> result = new ResponsePageBean<T>();
        result.setTotalPage(tempResPageHelperBean.getTotalPage());
        result.setPageNum(tempResPageHelperBean.getPageNum());
        result.setPageSize(tempResPageHelperBean.getPageSize());
        result.setTotalCount(tempResPageHelperBean.getTotalCount());
        result.setList(list);
        PageHelper.clearPage();
        return result;
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
