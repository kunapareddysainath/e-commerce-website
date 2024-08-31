package com.commerce.student.controller;

import com.commerce.student.model.entity.Payment;
import com.commerce.student.model.request.PaymentCreateOrderRequest;
import com.commerce.student.model.request.VerifyRazorpayPaymentRequest;

import com.commerce.student.service.PaymentService;
import com.commerce.student.utility.Constants;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody @Valid PaymentCreateOrderRequest orderRequest) {
        if (orderRequest == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide valid data to create a razor pay order");
        try {
            Order order = paymentService.createRazorPaymentOrder(orderRequest.getAmount(), orderRequest.getOrderId());
            return ResponseEntity.ok(order.toString());
        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody @Valid VerifyRazorpayPaymentRequest paymentVerificationRequest) {
        if (paymentVerificationRequest == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide valid data for the request");

        com.razorpay.Payment razorpayPayment = paymentService.getRazorIssuedSignature(paymentVerificationRequest);

        if (razorpayPayment != null && razorpayPayment.has("status") && razorpayPayment.get("status").equals(Constants.CAPTURED)) {
            Payment payment = paymentService.savePayment(paymentVerificationRequest);
            if (payment == null) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed payment");
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
        }

    }

}
