package com.commerce.student.repositiory;

import com.commerce.student.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    List<Cart> findByUserId(String userId);

    @Query(value = "SELECT * FROM cart WHERE user_id = :userId AND product_id = :productId", nativeQuery = true)
    Cart findByUserIdAndProductId(@Param("userId") String userId, @Param("productId") int productId);

    @Transactional
    void deleteByUserIdAndId(String userId, String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart c WHERE c.product_id IN :productIds",nativeQuery = true)
    void deleteListOfProductByProductIds(@Param("productIds") List<Integer> productIds);
}
