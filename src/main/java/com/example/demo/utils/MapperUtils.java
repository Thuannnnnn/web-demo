package com.example.demo.utils;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.enums.StatusType;
import com.example.demo.model.enums.UserRole;
import com.example.demo.model.request.NewOrderRequest;
import com.example.demo.model.request.OrderItemRequest;
import com.example.demo.model.response.OrderDetail;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.User;
import com.example.demo.model.request.RegisterUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapperUtils {

    public static User createNewUser(RegisterUser request) {

        User newUser = new User();
        newUser.setAge(request.getAge());
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setLastName(request.getLastName());
        newUser.setFirstName(request.getFirstName());
        newUser.setRole(UserRole.CUSTOMER);

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(request.getPassword()));
        return newUser;
    }

    private static OrderItem convertToOrderItem(OrderItemRequest itemRequest) {
        OrderItem item = new OrderItem();
        item.setProductName(itemRequest.getProductName());
        item.setProductId(itemRequest.getProductId());
        item.setPrice(itemRequest.getPrice());
        item.setQuantity(item.getQuantity());
        return item;
    }

    public static OrderDetail createNewOrder(NewOrderRequest request) {
        Order newOrder = new Order();
        newOrder.setAddress(request.getAddress());
        newOrder.setCreatedBy(request.getCreatedBy());
        newOrder.setDescription(request.getDescription());
        newOrder.setFullName(request.getFullName());
        newOrder.setTitle(request.getTitle());
        newOrder.setStatus(0);
        newOrder.setEmail(request.getEmail());
        newOrder.setStatusDesc(StatusType.getStatusDescription(newOrder.getStatus()));

        int totalProduct = request.getItems().size();
        long totalQuantity = 0;
        double totalAmount = 0;

        HashMap<String, String> itemMap = new HashMap<>();
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest item : request.getItems()) {

            if (!itemMap.containsKey(item.getProductId())) {
                itemMap.put(item.getProductId(), item.getProductName());

                OrderItem orderItem = convertToOrderItem(item);
                orderItem.setStatus(newOrder.getStatus());
                orderItem.setOrderId(newOrder.getId());

                //tinh tong tien, tong so luong yeu cau, tong san pham
                totalQuantity += orderItem.getQuantity();
                totalAmount += (item.getQuantity() * item.getPrice());

                orderItems.add(orderItem);
            }
        }

        newOrder.setTotalProduct(totalProduct);
        newOrder.setTotalAmount(totalAmount);
        newOrder.setTotalQuantity(totalQuantity);

        OrderDetail detail = new OrderDetail();
        detail.setOrder(newOrder);
        detail.setItems(orderItems);

        return detail;
    }
}
