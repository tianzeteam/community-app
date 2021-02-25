package com.smart.home.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.home.common.bean.ResponsePageBean;

import java.util.List;

/**
 * @author jason
 * @date 2021/2/25
 **/
public class ResponsePageUtil {

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

}
