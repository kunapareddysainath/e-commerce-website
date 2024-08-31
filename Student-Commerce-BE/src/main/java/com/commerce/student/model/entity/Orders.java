package com.commerce.student.model.entity;

import com.commerce.student.utility.UserDateAuditWithDeletion;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Random;

@Entity
@Table(name = "orders", schema = "public")
public class Orders extends UserDateAuditWithDeletion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "order_number", nullable = false)
    private int orderNumber;
    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;
    @Column(name = "order_status", nullable = false)
    private String orderStatus;
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;
    @ManyToOne()
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    public Orders() {
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber() {
        Random rand = new Random();
        this.orderNumber = 100000 + rand.nextInt(900000);
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Address getCustomerShippingAddress() {
        return shippingAddress;
    }

    public void setCustomerShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
