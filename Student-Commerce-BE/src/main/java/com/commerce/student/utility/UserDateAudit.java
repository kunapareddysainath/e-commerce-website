package com.commerce.student.utility;

import com.commerce.student.utility.DateAudit;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserDateAudit extends DateAudit {
    @CreatedBy
    @JsonProperty(value = "created_by")
    private String createdBy;
    @LastModifiedBy
    @JsonProperty(value = "last_updated_by")
    private String lastUpdatedBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}

