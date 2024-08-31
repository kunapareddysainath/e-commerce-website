package com.commerce.student.model.request;

import com.commerce.student.model.entity.Address;
import com.commerce.student.utility.Product;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {

    @NotNull(message = "Amount cannot be null")
    @NotEmpty(message = "Amount cannot be empty")
    private int amount;
    @NotNull(message = "Payment Method cannot be null")
    private String paymentMethod;
    @NotNull(message = "Products cannot be null")
    private List<Product> productList;
    @NotNull(message = "Address cannot be null")
    private Address address;

    public List<Product> getProductList() {
        return productList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
