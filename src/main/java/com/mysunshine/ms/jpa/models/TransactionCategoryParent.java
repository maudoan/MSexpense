package com.mysunshine.ms.jpa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mysunshine.ms.jpa.IdEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "transaction_category_parent")
public class TransactionCategoryParent extends IdEntity {
    private String name;
    private String description;
    private Integer transactionType;
    private String icon;
    @OneToMany(mappedBy = "transactionCategoryParent", cascade = CascadeType.ALL)
    private List<TransactionCategoryChild> transactionCategoryChild;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
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

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public List<TransactionCategoryChild> getTransactionCategoryChild() {
        return transactionCategoryChild;
    }

    public void setTransactionCategoryChild(List<TransactionCategoryChild> transactionCategoryChild) {
        this.transactionCategoryChild = transactionCategoryChild;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
