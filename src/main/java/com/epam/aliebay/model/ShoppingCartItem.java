package com.epam.aliebay.model;

import java.math.BigDecimal;

public class ShoppingCartItem {
    private int count;
    private BigDecimal cost;

    public ShoppingCartItem(int countByProduct, BigDecimal costByProduct) {
        this.count = countByProduct;
        this.cost = costByProduct;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}