package com.commerce.student.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UpdateUserRequest {
@NotNull(message = "User ID is required")
    @NotEmpty(message = "User ID cannot be empty")
    private String userId;

    @NotNull(message = "Address ID is required")
    @NotEmpty(message = "Address ID cannot be empty")
    private String addressId;

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Email address is required")
    @NotEmpty(message = "Email address cannot be empty")
    private String emailAddress;

    @NotNull(message = "Phone number is required")
    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;

    @NotNull(message = "Address line 1 is required")
    @NotEmpty(message = "Address line 1 cannot be empty")
    private String address1;

    @NotNull(message = "Address line 2 is required")
    @NotEmpty(message = "Address line 2 cannot be empty")
    private String address2;

    @NotNull(message = "State is required")
    @NotEmpty(message = "State cannot be empty")
    private String state;

    @NotNull(message = "Country is required")
    @NotEmpty(message = "Country cannot be empty")
    private String country;

    @NotNull(message = "ZIP code is required")
    @NotEmpty(message = "ZIP code cannot be empty")
    private String zip;

    public UpdateUserRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
