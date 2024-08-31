package com.commerce.student.model.DTO;

import com.commerce.student.model.entity.Orders;
import com.commerce.student.model.response.RazorpayOrder;

public class OrderDTO {
   private Orders orders;
   private RazorpayOrder razorpayOrder;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public RazorpayOrder getRazorpayOrder() {
        return razorpayOrder;
    }

    public void setRazorpayOrder(RazorpayOrder razorpayOrder) {
        this.razorpayOrder = razorpayOrder;
    }
}
