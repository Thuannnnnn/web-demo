package com.example.demo.repository;


import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(value = "SELECT * FROM order_db o WHERE o.id = ?1 and o.active_code= ?2", nativeQuery = true)
    Order findByIdAndActiveCode(String id, String activeCode);
}
