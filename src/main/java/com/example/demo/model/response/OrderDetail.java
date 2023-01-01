package com.example.demo.model.response;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDetail {
    private Order order;
    private List<OrderItem> items;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
