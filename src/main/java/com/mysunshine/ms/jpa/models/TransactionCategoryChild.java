package com.mysunshine.ms.jpa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mysunshine.ms.jpa.IdEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction_category_child")
public class TransactionCategoryChild extends IdEntity {
    private String name;
    private String description;
    private String icon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "transaction_category_parent_id", nullable = false)
    private TransactionCategoryParent transactionCategoryParent;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TransactionCategoryParent getTransactionCategoryParent() {
        return transactionCategoryParent;
    }

    public void setTransactionCategoryParent(TransactionCategoryParent transactionCategoryParent) {
        this.transactionCategoryParent = transactionCategoryParent;
    }
}
