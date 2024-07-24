package com.example.awaysuse.model.vo;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVo {
    // 商品名称
    private String productName;

    // 商品价格
    private BigDecimal productPrice;

    // 上架状态
    private Integer productStatus;



}
