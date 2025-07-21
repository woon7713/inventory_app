package com.woon7713.inventory_app.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockSearchCondition {
    private String productName;
    private String warehouseName;
    private Integer minQuantity;
    private Integer maxQuantity;
}
// 사실상 중간 테이블