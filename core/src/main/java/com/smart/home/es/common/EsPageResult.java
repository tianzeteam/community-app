package com.smart.home.es.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tangchenglong on 2021/2/25.
 */
@Data
public class EsPageResult<T> implements Serializable
{
    private Integer pageNum = 0;
    private Integer pageSize = 0;
    private Integer totalPage = 0;
    private Long totalCount = 0L;
    private List<T> list;

    public EsPageResult()
    {

    }

    public EsPageResult(int pageNum, int pageSize, long totalCount, List<T> list)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;

        if (totalCount <= 0)
        {
            this.totalCount = 0L;
            this.totalPage = 0;
        }

        if (pageSize > 0)
        {
            this.totalPage = (int) (totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1));
        }
        else
        {
            this.totalPage = 0;
        }
        if (pageNum > totalPage)
        {
            this.totalPage = 0;
        }
    }
}
