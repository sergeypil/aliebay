package com.epam.aliebay.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private long id;
    private int idUser;
    private List<OrderItem> items;
    private Timestamp created;
    private OrderStatus status;
    private BigDecimal cost;
    private String address;

    public Order() {
        this.items = new ArrayList<>();
    }

    public Order(int idUser) {
        this.idUser = idUser;

    }

    public Order(int idUser, Timestamp created) {
        this.idUser = idUser;
        this.items = new ArrayList<>();
        this.created = created;
    }

    public Order(long id, int idUser, Timestamp created) {
        this.id = id;
        this.idUser = idUser;
        this.items = new ArrayList<>();
        this.created = created;
    }

    public Order(long id, int idUser, List<OrderItem> items, Timestamp created) {
        this.id = id;
        this.idUser = idUser;
        this.items = items;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", items=" + items +
                ", created=" + created +
                ", status=" + status +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
