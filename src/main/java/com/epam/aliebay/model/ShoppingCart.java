package com.epam.aliebay.model;

import com.epam.aliebay.entity.Product;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, ShoppingCartItem> products;
    private int totalCount = 0;
    private BigDecimal totalCost = BigDecimal.ZERO;

    public ShoppingCart() {
        this.products = new LinkedHashMap<>();
    }

    public Map<Product, ShoppingCartItem> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, ShoppingCartItem> products) {
        this.products = products;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public int getTotalCount() {
        return totalCount;
    }
    public void refreshStatistics() {
        totalCost = new BigDecimal(0);
        totalCount = 0;
        for (Map.Entry<Product, ShoppingCartItem> entry: products.entrySet()) {
            totalCost = totalCost.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue().getCount())));
            totalCount += entry.getValue().getCount();
        }

    }

}


