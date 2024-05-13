package com.mysunshine.ms.jpa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysunshine.ms.jpa.IdEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "budget")
public class Budget extends IdEntity {
    private Long categoryType;
    private String categoryName;
    private Long budgetDateStart;
    private Long budgetDateEnd;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private Long amount;
    private Long budgetBalance;
    private String categoryIcon;
    private Long categoryId;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Transaction> transactions;

    public Long getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Long categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getBudgetDateStart() {
        return budgetDateStart;
    }

    public void setBudgetDateStart(Long budgetDateStart) {
        this.budgetDateStart = budgetDateStart;
    }

    public Long getBudgetDateEnd() {
        return budgetDateEnd;
    }

    public void setBudgetDateEnd(Long budgetDateEnd) {
        this.budgetDateEnd = budgetDateEnd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBudgetBalance() {
        return budgetBalance;
    }

    public void setBudgetBalance(Long budgetBalance) {
        this.budgetBalance = budgetBalance;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
