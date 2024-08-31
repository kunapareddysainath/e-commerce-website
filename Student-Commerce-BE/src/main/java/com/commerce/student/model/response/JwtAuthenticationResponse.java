package com.commerce.student.model.response;


import com.commerce.student.security.UserPrincipal;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserPrincipal userDetails;

    public JwtAuthenticationResponse(String accessToken, UserPrincipal userDetails) {
        this.accessToken = accessToken;
        this.userDetails = userDetails;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserPrincipal getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserPrincipal userDetails) {
        this.userDetails = userDetails;
    }
}

