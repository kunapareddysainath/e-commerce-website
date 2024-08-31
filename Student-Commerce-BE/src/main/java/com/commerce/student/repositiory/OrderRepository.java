package com.commerce.student.repositiory;

import com.commerce.student.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {
    @Query(value = "SELECT * FROM orders WHERE customer_id = :userId", nativeQuery = true)
    List<Orders> findByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM orders WHERE order_status = :orderAccepted", nativeQuery = true)
    List<Orders> findDeliveryOrders(String orderAccepted);
}
