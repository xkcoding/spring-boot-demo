package com.xkcoding.codegen.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 分页结果集
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019-03-22 11:24
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 总条数
     */
    private Long total;

    /**
     * 页码
     */
    private int pageNumber;

    /**
     * 每页结果数
     */
    private int pageSize;

    /**
     * 结果集
     */
    private List<T> list;
}
