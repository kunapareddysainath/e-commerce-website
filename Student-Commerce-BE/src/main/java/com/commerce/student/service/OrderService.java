package com.commerce.student.service;

import com.commerce.student.model.DTO.OrderDTO;
import com.commerce.student.model.DTO.OrdersListDTO;
import com.commerce.student.model.entity.Orders;
import com.commerce.student.model.entity.OrderItem;
import com.commerce.student.model.entity.User;
import com.commerce.student.model.request.OrderRequest;
import com.commerce.student.model.request.UpdateOrderStatusRequest;
import com.commerce.student.model.response.RazorpayOrder;
import com.commerce.student.repositiory.OrderItemRepository;
import com.commerce.student.repositiory.OrderRepository;
import com.commerce.student.repositiory.UserRepository;
import com.commerce.student.utility.Constants;
import com.commerce.student.utility.Product;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CartService cartService;

    @Autowired
    private EmailService emailService;

    public OrderDTO createOrder(OrderRequest orderRequest, String userId) {
        try {
            User user = getUserById(userId);

            Orders order = initializeOrder(orderRequest, user);
            orderRepository.save(order);

            saveOrderItems(order.getId(), orderRequest.getProductList());


            OrderDTO orderDTO = createOrderDTO(orderRequest, order);

            emailService.orderEmail(user.getName(), user.getEmailAddress(), order.getOrderNumber(), order.getCreatedAt());

            return orderDTO;

        } catch (Exception e) {
            throw new RuntimeException("Error creating order", e);
        }
    }

    private User getUserById(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    private Orders initializeOrder(OrderRequest orderRequest, User user) {
        Orders order = new Orders();
        order.setOrderNumber();
        order.setOrderStatus(Constants.ORDER_PLACED);
        order.setCustomer(user);
        order.setCustomerShippingAddress(orderRequest.getAddress());
        if (orderRequest.getPaymentMethod().equalsIgnoreCase(Constants.ONLINE_MODE)) {
            order.setPaymentMethod(Constants.ONLINE_MODE);
            order.setPaymentStatus("");
        } else {
            order.setPaymentMethod(Constants.COD);
            order.setPaymentStatus(Constants.ORDER_PENDING);
        }
        return order;
    }

    private OrderDTO createOrderDTO(OrderRequest orderRequest, Orders order) throws Exception {
        OrderDTO orderDTO = new OrderDTO();

        if (Constants.ONLINE_MODE.equalsIgnoreCase(orderRequest.getPaymentMethod())) {
            handleOnlinePayment(orderRequest, order, orderDTO);
        }

        orderDTO.setOrders(order);

        return orderDTO;
    }

    private void handleOnlinePayment(OrderRequest orderRequest, Orders order, OrderDTO orderDTO) {
        try {
            Order razorpayOrder = paymentService.createRazorPaymentOrder(orderRequest.getAmount(), order.getId());

            RazorpayOrder razorpayOrderResponse = new RazorpayOrder();
            razorpayOrderResponse.setAmount(razorpayOrder.get("amount"));
            razorpayOrderResponse.setStatus(razorpayOrder.get("status"));
            razorpayOrderResponse.setId(razorpayOrder.get("id"));
            razorpayOrderResponse.setReceipt(razorpayOrder.get("receipt"));
            razorpayOrderResponse.setAmountDue(razorpayOrder.get("amount_due"));
            razorpayOrderResponse.setAmountPaid(razorpayOrder.get("amount_paid"));
            razorpayOrderResponse.setCurrency(razorpayOrder.get("currency"));

            orderDTO.setRazorpayOrder(razorpayOrderResponse);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOrderItems(String orderId, List<Product> productList) {

        try {
            List<OrderItem> orderItemList = new ArrayList<>(productList.size());
            List<Integer> cartedProductIds = new ArrayList<>(productList.size());

            for (Product product : productList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setProduct_name(product.getTitle());
                orderItem.setProductId(product.getProductId());
                orderItem.setQuantity(product.getQuantity());
                orderItem.setPrice(BigDecimal.valueOf(product.getPrice()));
                orderItem.setImageUrl(product.getImageUrl());
                orderItemList.add(orderItem);
                cartedProductIds.add(product.getProductId());
            }
            orderItemRepository.saveAll(orderItemList);
            cartService.deleteListOfCartedProducts(cartedProductIds);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Orders> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<OrdersListDTO> getOrderItemListByListOrders(List<Orders> orders) {

        if (orders == null) throw new NullPointerException();

        List<OrdersListDTO> ordersListDTOS = new ArrayList<>(orders.size());

        for (Orders order : orders) {
            List<OrderItem> orderItem = orderItemRepository.findByOrderId(order.getId());
            if (orderItem != null) {
                OrdersListDTO ordersListDTO = new OrdersListDTO();
                ordersListDTO.setOrderId(order.getId());
                ordersListDTO.setOrderNumber(order.getOrderNumber());
                ordersListDTO.setPaymentStatus(order.getPaymentStatus());
                ordersListDTO.setShippingAddress(order.getCustomerShippingAddress());
                ordersListDTO.setOrderStatus(order.getOrderStatus());
                ordersListDTO.setCustomer(order.getCustomer());
                ordersListDTO.setOrderItemList(orderItem);
                ordersListDTO.setPaymentMethod(order.getPaymentMethod());
                BigDecimal totalAmount = orderItem.stream()
                        .map(orderItem1 -> orderItem1.getPrice().multiply(BigDecimal.valueOf(orderItem1.getQuantity())))
                        .reduce(BigDecimal.valueOf(0), BigDecimal::add);
                ordersListDTO.setTotalAmount(totalAmount.add(BigDecimal.valueOf(40)));
                ordersListDTO.setOrderCreatedAt(order.getCreatedAt());

                ordersListDTOS.add(ordersListDTO);
            }
        }

        return ordersListDTOS;
    }

    public List<Orders> findAllOrders() {
        return orderRepository.findAll();
    }

    public void updateOrderStatus(UpdateOrderStatusRequest updateOrderStatusRequest) {

        Orders order = orderRepository.findById(updateOrderStatusRequest.getOrderId()).orElse(null);
        if (order == null) throw new NullPointerException("Order is not found please check your order number");

        String orderStatus = updateOrderStatusRequest.getOrderStatus();
        order.setOrderStatus(orderStatus);
        if (orderStatus.equalsIgnoreCase(Constants.ORDER_DELIVERED)) {
            if (order.getPaymentStatus().equalsIgnoreCase(Constants.ORDER_PENDING)) {
                order.setPaymentStatus(Constants.ORDER_COMPLETED);
            }
        }
        orderRepository.save(order);

        User customer = order.getCustomer();

        switch (orderStatus) {
            case "Accepted":
                emailService.orderAcceptedEmail(customer.getName(), customer.getEmailAddress(), order.getOrderNumber(), order.getCreatedAt());
                break;
            case "Cancelled":
                emailService.orderCancelledEmail(customer.getName(), customer.getEmailAddress(), order.getOrderNumber(), order.getCreatedAt());
                break;

            case "Delivered":
                emailService.orderDeliveredEmail(customer.getName(), customer.getEmailAddress(), order.getOrderNumber(), order.getCreatedAt());
                break;
        }

    }

    public List<Orders> findDeliveryOrders() {
        return orderRepository.findDeliveryOrders(Constants.ORDER_ACCEPTED);
    }
}
