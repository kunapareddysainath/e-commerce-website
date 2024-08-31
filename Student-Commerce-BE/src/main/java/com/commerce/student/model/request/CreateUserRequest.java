package com.commerce.student.model.request;

import jakarta.validation.constraints.NotNull;

public class CreateUserRequest {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "User name cannot be null")
    private String username;
    @NotNull(message = "Phone Number cannot be null")
    private String phoneNumber;
    @NotNull(message = "Email address cannot be null")
    private String emailAddress;
    @NotNull(message = "Password cannot be null")
    private String password;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
