package com.xkcoding.distributed.lock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xkcoding.distributed.lock.model.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 货物 Mapper
 *
 * @author yangkai.shen
 * @date 2022-09-02 14:09
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
}
