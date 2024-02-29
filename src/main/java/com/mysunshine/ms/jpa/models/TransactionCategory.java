package com.mysunshine.ms.jpa.models;

import com.mysunshine.ms.jpa.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactionCategories")
public class TransactionCategory extends IdEntity {
    private Long category_name;
    private String description;

    public Long getCategory_name() {
        return category_name;
    }

    public void setCategory_name(Long category_name) {
        this.category_name = category_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
