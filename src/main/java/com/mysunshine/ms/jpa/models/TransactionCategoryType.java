package com.mysunshine.ms.jpa.models;

import com.mysunshine.ms.jpa.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_category_type")
public class TransactionCategoryType extends IdEntity {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
