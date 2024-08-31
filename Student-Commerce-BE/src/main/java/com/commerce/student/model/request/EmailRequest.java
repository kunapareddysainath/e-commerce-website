package com.commerce.student.model.request;

import jakarta.validation.constraints.NotNull;

public class EmailRequest {

    @NotNull(message = "Send mail Aaddress cannot be null")
    private String senderMailAddress;
    @NotNull(message = "Subject cannot be null")
    private String subject;
    @NotNull(message = "Body cannot be null")
    private String body;

    public String getSenderMailAddress() {
        return senderMailAddress;
    }

    public void setSenderMailAddress(String sendMailAddress) {
        this.senderMailAddress = sendMailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
