package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.OrderTransaction;
import com.example.demo.model.enums.StatusType;
import com.example.demo.model.request.ChangeStatus;
import com.example.demo.model.request.EmailSimpleRequest;
import com.example.demo.model.request.NewOrderRequest;
import com.example.demo.model.response.OrderDetail;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderTransactionRepository;
import com.example.demo.utils.MapperUtils;
import com.example.demo.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderTransactionRepository transactionRepository;
    @Autowired
    private EmailService emailService;

    @Transactional(rollbackFor = Exception.class)
    public OrderDetail createOrder(NewOrderRequest request) {

        OrderDetail detail = MapperUtils.createNewOrder(request);
        detail.setOrder(orderRepository.save(detail.getOrder()));
        detail.setItems(orderItemRepository.saveAll(detail.getItems()));

        return detail;
    }

    @Transactional(rollbackFor = Exception.class)
    public Order updateOrderRequestStatus(String orderId, ChangeStatus request, boolean isCheck) {
        Optional<Order> orderResult = orderRepository.findById(orderId);
        Order order = orderResult.get();

        ValidateUtils.validateOrderStatus(order, request);
        if (isCheck && 2 == request.getStatus()) {
            throw new ResourceNotFoundException("Bạn không được cập nhật trạng thái: " + StatusType.getStatusDescription(2));
        }

        order.setStatus(request.getStatus());
        order.setStatusDesc(StatusType.getStatusDescription(order.getStatus()));
        order.setUpdateAt(new Date());
        order.setUpdatedBy(request.getUpdatedBy());

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            orderItem.setStatus(order.getStatus());
            orderItem.setUpdateAt(order.getUpdateAt());
        }

        //cap nhat status
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        if (1 == order.getStatus()) {
            request.setStatus(0);
            createOrderTransaction(orderId, request);
        } else if (3 == order.getStatus()) {
            OrderTransaction transaction = transactionRepository.findByOrderId(orderId);
            if (transaction != null) {
                updateOrderTransactionStatus(transaction.getId(), request);
            }
        }

        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createOrderTransaction(String orderId, ChangeStatus request) {
        OrderTransaction transaction = new OrderTransaction();
        transaction.setStatus(0);
        transaction.setCreatedBy(request.getUpdatedBy());
        transaction.setOrderId(orderId);
        transactionRepository.save(transaction);
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderTransaction updateOrderTransactionStatus(String orderTransactionId, ChangeStatus request) {
        Optional<OrderTransaction> transactionResult = transactionRepository.findById(orderTransactionId);
        OrderTransaction transaction = transactionResult.get();
        ValidateUtils.validateOrderTransactionStatus(transaction, request);
        transaction.setUpdatedBy(request.getUpdatedBy());
        transaction.setUpdateAt(new Date());
        transaction.setStatus(request.getStatus());

        //cap nhat status phieu cua khach hang

        if (2 == transaction.getStatus()) {
            Order orderCustomer = updateOrderRequestStatus(transaction.getOrderId(), request, false);

            String desc = "đã giao thành công";

            EmailSimpleRequest emailRequest = new EmailSimpleRequest();
            emailRequest.setSubject("Thông tin giao dịch: " + transaction.getOrderId());
            emailRequest.setMessageBody("Mã đơn hàng: " + transaction.getOrderId() + ", của bạn " + desc);
            emailRequest.setRecipient(orderCustomer.getEmail());
            emailService.sendSimpleMail(emailRequest);
        }

        return transactionRepository.save(transaction);
    }
}
