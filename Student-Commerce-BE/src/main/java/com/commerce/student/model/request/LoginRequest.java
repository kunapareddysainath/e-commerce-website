package com.commerce.student.model.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 1, max = 30,
            message = "Username length must be between 1 and 30 characters")
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username always in lowercase")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 30, message = "Password length must be between 8 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9%,$,#,@,_,&,*!]+$",
            message = "Password must only contain alphanumerical characters and symbols %,$,#,@,_,*,&,!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

