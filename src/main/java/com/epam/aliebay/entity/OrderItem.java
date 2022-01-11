package com.epam.aliebay.entity;

import java.math.BigDecimal;

public class OrderItem {
    private long id;
    private long orderId;
    private Product product;
    private int count;
    private BigDecimal retainedProductPrice;

    public OrderItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getRetainedProductPrice() {
        return retainedProductPrice;
    }

    public void setRetainedProductPrice(BigDecimal retainedProductPrice) {
        this.retainedProductPrice = retainedProductPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", product=" + product +
                ", count=" + count +
                ", retainedProductPrice=" + retainedProductPrice +
                '}';
    }
}
