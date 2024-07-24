package com.example.awaysuse.model;

import lombok.Data;

/**
 * @Author:
 * @Date:
 * @Description:
 */
@Data
public class PageData<T> {
    /**
     * 当前页面
     */
    private int pageNum;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 记录总数
     */
    private long totalCount;

    /**
     * 页码总数
     */
    private int totalPages;

    /**
     * 分页数据
     */
    private T data;

    public PageData() {
    }

    public PageData(T data) {
        this.data = data;
    }

    public PageData(int pageNum, int pageSize, long totalCount, int totalPage, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = totalPage;
        this.data = data;
    }
}
