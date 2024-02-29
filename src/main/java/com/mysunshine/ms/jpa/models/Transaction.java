package com.mysunshine.ms.jpa.models;

import com.mysunshine.ms.jpa.IdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction extends IdEntity {
    private Long transaction_date;
    private Long transaction_type;
    private Long amount;
    private String description;
    private Long category_id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Long transaction_date) {
        this.transaction_date = transaction_date;
    }

    public Long getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(Long transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
