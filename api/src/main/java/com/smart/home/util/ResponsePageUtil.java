package com.smart.home.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.home.dto.ResponsePageBean;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/25
 **/
public class ResponsePageUtil {

    /**
     * 将PageHelper分页后的list转为分页信息
     * 这个方法废弃掉，这个方法附加的分页信息有问题， 用restPage(List<T> list, List pager) 方法代替
     */
    @Deprecated
    public static <T> ResponsePageBean<T> restPage(List<T> resultList) {
        if (resultList instanceof Page) {
            ResponsePageBean<T> result = new ResponsePageBean<T>();
            PageInfo<T> pageInfo = new PageInfo<T>(resultList);
            result.setTotalPage(pageInfo.getPages());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setTotalCount(pageInfo.getTotal());
            result.setList(pageInfo.getList());
            PageHelper.clearPage();
            return result;
        } else {
            throw new RuntimeException("输入参数必须是Page类型的， 否则请调用restPage(List<T> resultList, List pager)方法");
        }
    }

    public static <T> ResponsePageBean<T> restPage(List<T> resultList, List pager) {
        ResponsePageBean<T> result = new ResponsePageBean<T>();
        if (resultList instanceof Page) {
            PageInfo<T> pageInfo = new PageInfo<T>(resultList);
            result.setTotalPage(pageInfo.getPages());
            result.setPageNum(pageInfo.getPageNum());
            result.setPageSize(pageInfo.getPageSize());
            result.setTotalCount(pageInfo.getTotal());
            result.setList(pageInfo.getList());
            PageHelper.clearPage();
            return result;
        }
        if (pager instanceof Page) {
            Page page = (Page) pager;
            result.setTotalPage(page.getPages());
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setTotalCount(page.getTotal());
            result.setList(resultList);
            PageHelper.clearPage();
            return result;
        }
        throw new RuntimeException("方法使用错误");
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

}
