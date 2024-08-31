package com.commerce.student.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public class EmailResponse<T> {

    private int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;

    public EmailResponse(int statusCode, T body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
