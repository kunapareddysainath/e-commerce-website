package com.commerce.student.service;

import com.commerce.student.model.entity.Orders;
import com.commerce.student.model.entity.Payment;
import com.commerce.student.model.request.VerifyRazorpayPaymentRequest;
import com.commerce.student.repositiory.OrderRepository;
import com.commerce.student.repositiory.PaymentRepository;

import com.commerce.student.utility.Constants;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${app.razorpay.key_id}")
    private String keyId;

    @Value("${app.razorpay.secret}")
    private String secret;

    @Autowired
    private OrderRepository orderRepository;

    public Order createRazorPaymentOrder(double amount, String orderId) throws RazorpayException {
        try {
            JSONObject orderObject = new JSONObject();

            orderObject.put("amount", amount * 100);
            orderObject.put("currency", "INR");
            orderObject.put("receipt", orderId);

            return razorpayClient.orders.create(orderObject);
        } catch (RazorpayException e) {
            throw new RazorpayException(e.getMessage());
        }
    }

    public Payment savePayment(VerifyRazorpayPaymentRequest verifyRazorpayPaymentRequest) {

        try {
            Orders orders = orderRepository.findById(verifyRazorpayPaymentRequest.getOrderId()).orElse(null);

            if (orders == null) {
                throw new NullPointerException("Order is not found");
            }

            orders.setPaymentStatus(Constants.ORDER_COMPLETED);
            orderRepository.save(orders);

            Payment payment = new Payment();

            payment.setOrderId(verifyRazorpayPaymentRequest.getOrderId());
            payment.setRazorpayOrderId(verifyRazorpayPaymentRequest.getRazorpayOrderId());
            payment.setRazorpayPaymentId(verifyRazorpayPaymentRequest.getPaymentId());
            payment.setAmount(verifyRazorpayPaymentRequest.getAmount());
            payment.setStatus("Success");
            paymentRepository.save(payment);
            return payment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public com.razorpay.Payment getRazorIssuedSignature(VerifyRazorpayPaymentRequest paymentVerificationRequest) {

        // Verification
        try {
            return razorpayClient.payments.fetch(paymentVerificationRequest.getPaymentId());
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        return null;
    }

}
