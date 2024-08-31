package com.commerce.student.repositiory;

import com.commerce.student.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    @Query(value = "SELECT * FROM order_item WHERE order_id = :orderId", nativeQuery = true)
    List<OrderItem> findByOrderId(String orderId);
}
