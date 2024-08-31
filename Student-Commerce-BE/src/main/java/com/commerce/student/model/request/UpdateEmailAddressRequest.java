package com.commerce.student.model.request;

public class UpdateEmailAddressRequest {
    private String oldEmailAddress;
    private String newEmailAddress;

    public String getOldEmailAddress() {
        return oldEmailAddress;
    }

    public void setOldEmailAddress(String oldEmailAddress) {
        this.oldEmailAddress = oldEmailAddress;
    }

    public String getNewEmailAddress() {
        return newEmailAddress;
    }

    public void setNewEmailAddress(String newEmailAddress) {
        this.newEmailAddress = newEmailAddress;
    }
}
