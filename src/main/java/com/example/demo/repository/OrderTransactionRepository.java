package com.example.demo.repository;

import com.example.demo.model.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderTransactionRepository extends JpaRepository<OrderTransaction, String> {
    @Query(value = "SELECT * FROM order_transaction o WHERE o.id = ?1 and o.orderId= ?2", nativeQuery = true)
    OrderTransaction findByIdAndOrderId(String id, String orderId);

    OrderTransaction findByOrderId(String orderId);
}
