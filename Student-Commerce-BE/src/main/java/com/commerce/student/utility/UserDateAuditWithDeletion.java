package com.commerce.student.utility;

import com.commerce.student.utility.DateAuditWithDeletion;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserDateAuditWithDeletion extends DateAuditWithDeletion {
    @CreatedBy
    @JsonProperty(value = "created_by")
    private String createdBy;
    @LastModifiedBy
    @JsonProperty(value = "last_updated_by")
    private String lastUpdatedBy;

    @JsonProperty(value = "deleted_by")
    private String deletedBy;

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

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
}
