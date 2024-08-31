package com.commerce.student.model.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class AddCartProductRequest {

    @NotNull(message = "ID cannot be null")
    private String id;
    @NotNull(message = "Product ID cannot be null")
    private int productId;
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Price cannot be null")
    private double price;
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotNull(message = "Category cannot be null")
    private String category;
    @NotNull(message = "Image cannot be null")
    private String image;
    @NotNull(message = "Quantity cannot be null")
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
