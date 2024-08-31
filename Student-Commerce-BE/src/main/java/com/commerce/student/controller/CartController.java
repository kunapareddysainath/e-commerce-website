package com.commerce.student.controller;

import com.commerce.student.model.entity.Cart;
import com.commerce.student.model.request.AddCartProductRequest;
import com.commerce.student.model.request.DeleteCartedProduct;
import com.commerce.student.security.UserPrincipal;
import com.commerce.student.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProductToCart(@RequestBody @Valid AddCartProductRequest cartProductRequest) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User doesn't exist");
        }

        Cart cart = cartService.findProductByUserIdAndProductId(userPrincipal.getId(), cartProductRequest.getProductId());

        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + 1);
        } else {
            cart = cartService.convertModelCartToEntity(cartProductRequest, userPrincipal.getId());
            if (cart == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the product to cart.");
            }
        }

        return ResponseEntity.ok(cartService.saveCart(cart));
    }

    @GetMapping("/getCartedProducts")
    public ResponseEntity<?> getCartedProducts(@RequestParam String userId) {

        List<Cart> cartList = cartService.getCartedProductsByUserId(userId);
        if (cartList == null)
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("No product added to cart by this user");

        return ResponseEntity.ok(cartList);
    }

    @PostMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestBody @Valid DeleteCartedProduct deleteCartedProduct) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User doesn't exist");
        }

        Cart cart = cartService.findProductByUserIdAndProductId(userPrincipal.getId(), deleteCartedProduct.getProductId());

        if (cart == null) {
            return ResponseEntity.ok("This product is not found in the cart");
        }

        if (cart.getQuantity() > 1) {
            cart.setQuantity(cart.getQuantity() - 1);
            return ResponseEntity.ok(cartService.saveCart(cart));
        }

        try {
            cartService.deleteCartProduct(userPrincipal.getId(), deleteCartedProduct.getId());
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.ok("Product removed successfully");
    }

}
