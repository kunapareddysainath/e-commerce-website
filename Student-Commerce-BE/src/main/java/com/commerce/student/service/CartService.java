package com.commerce.student.service;

import com.commerce.student.model.entity.Cart;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.AddCartProductRequest;
import com.commerce.student.repositiory.CartRepository;
import com.commerce.student.repositiory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    public Cart convertModelCartToEntity(AddCartProductRequest cartProductRequest, String userId) {

        try {
            if (cartProductRequest == null) throw new NullPointerException();

            User user = userRepository.findByUserId(userId);

            if (user == null) throw new UsernameNotFoundException("No user not found");

            Cart cart = new Cart();

            cart.setTitle(cartProductRequest.getTitle());
            cart.setCategory(cartProductRequest.getCategory());
            cart.setDescription(cart.getDescription());
            cart.setImageUrl(cartProductRequest.getImage());
            cart.setProductId(cartProductRequest.getProductId());
            cart.setQuantity(cartProductRequest.getQuantity());
            cart.setPrice(BigDecimal.valueOf(cartProductRequest.getPrice() * 10));
            cart.setUser(user);

            cartRepository.save(cart);
            return cart;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the product");
        }
    }

    public List<Cart> getCartedProductsByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart findProductByUserIdAndProductId(String userId, int productId) {
        // Validate userId
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        // Validate productId
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be greater than zero");
        }

        return cartRepository.findByUserIdAndProductId(userId, productId);
    }

    public Cart saveCart(Cart cart) {
        //validate cart is not null
        if (cart == null) throw new NullPointerException();
        return cartRepository.save(cart);
    }

    public void deleteCartProduct(String userId, String id) {
        cartRepository.deleteByUserIdAndId(userId,id);
    }

    public void deleteListOfCartedProducts(List<Integer> productIds){
        productIds.forEach(System.out::println);
        cartRepository.deleteListOfProductByProductIds(productIds);
    }
}
