package com.commerce.student.controller;

import com.commerce.student.model.DTO.OrderDTO;
import com.commerce.student.model.DTO.OrdersListDTO;
import com.commerce.student.model.entity.Orders;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.OrderRequest;
import com.commerce.student.model.request.UpdateOrderStatusRequest;
import com.commerce.student.security.UserPrincipal;
import com.commerce.student.service.EmailService;
import com.commerce.student.service.OrderService;

import com.commerce.student.utility.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest orderRequest) {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User doesn't exist");

        if (orderRequest == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Provide Valid Order Request");

        OrderDTO orderDTO = orderService.createOrder(orderRequest, userPrincipal.getId());



        if (orderDTO == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Order");

        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/getOrdersByUser")
    public ResponseEntity<?> getOrdersByUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User doesn't exist");

        List<Orders> orders = orderService.getOrdersByUserId(userPrincipal.getId());

        List<OrdersListDTO> ordersListDTO = orderService.getOrderItemListByListOrders(orders);

        if (ordersListDTO == null)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("No Order placed by this user");

        return ResponseEntity.ok(ordersListDTO);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getOrdersForOrg() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //checking the role of user whether authorized to register an employee or not! Only Admin or Manager as the authority to hire an employee
        if (userPrincipal != null && !(userPrincipal.getRole().getLabel().equalsIgnoreCase(Constants.ADMIN) || userPrincipal.getRole().getLabel().equalsIgnoreCase(Constants.MANAGER))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not authorized to hire an employee");
        }

        List<Orders> orders = orderService.findAllOrders();

        List<OrdersListDTO> ordersListDTO = orderService.getOrderItemListByListOrders(orders);

        if (ordersListDTO == null)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("No Order placed by this user");

        return ResponseEntity.ok(ordersListDTO);
    }

    @PostMapping("/updateOrderStatus")
    public ResponseEntity<?> updateOrderStatus(@RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null || updateOrderStatusRequest == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not authenticate to process the request");

        orderService.updateOrderStatus(updateOrderStatusRequest);

        return ResponseEntity.ok("Order status updated successfully");

    }

    @GetMapping("/getDeliveryOrders")
    public ResponseEntity<?> getDeliveryOrders() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //checking the role of user whether role is delivery or not
        if (userPrincipal != null && !(userPrincipal.getRole().getLabel().equalsIgnoreCase(Constants.DELIVERY))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not authorized to delivery the orders");
        }

        List<Orders> orders = orderService.findDeliveryOrders();

        List<OrdersListDTO> ordersListDTO = orderService.getOrderItemListByListOrders(orders);

        if (ordersListDTO == null)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("No Order placed by this user");

        return ResponseEntity.ok(ordersListDTO);
    }

}
