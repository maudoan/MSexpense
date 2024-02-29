package com.mysunshine.ms.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.MappedSuperclass;
import org.javers.core.metamodel.annotation.DiffIgnore;

import java.io.Serializable;

@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractEntity implements Serializable {
    private Long created;
    @DiffIgnore
    private Long updated;
    private String createdBy;
    @DiffIgnore
    private String updatedBy;

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
