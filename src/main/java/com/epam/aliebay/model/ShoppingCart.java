package com.epam.aliebay.model;

import com.epam.aliebay.entity.Product;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, ShoppingCartItem> products;
    private int totalCount;
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
        totalCost = products.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue().getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        totalCount = products.values().stream()
                .mapToInt(ShoppingCartItem::getCount)
                .sum();
    }

}


