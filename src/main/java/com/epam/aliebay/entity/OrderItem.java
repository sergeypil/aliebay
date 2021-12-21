package com.epam.aliebay.entity;

import java.math.BigDecimal;

public class OrderItem {
    private long id;
    private long idOrder;
    private Product product;
    private int count;
    private BigDecimal retainedProductPrice;

    public OrderItem() {
    }

    public OrderItem(long idOrder, Product product, int count, BigDecimal retainedProductPrice) {
        this.idOrder = idOrder;
        this.product = product;
        this.count = count;
        this.retainedProductPrice = retainedProductPrice;
    }

    public OrderItem(long id, long idOrder, Product product, int count, BigDecimal retainedProductPrice) {
        this.id = id;
        this.idOrder = idOrder;
        this.product = product;
        this.count = count;
        this.retainedProductPrice = retainedProductPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
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
                ", idOrder=" + idOrder +
                ", product=" + product +
                ", count=" + count +
                ", retainedProductPrice=" + retainedProductPrice +
                '}';
    }
}
