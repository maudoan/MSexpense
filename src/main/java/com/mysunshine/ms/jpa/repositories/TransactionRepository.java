package com.mysunshine.ms.jpa.repositories;

import com.mysunshine.ms.jpa.CustomJpaRepository;
import com.mysunshine.ms.jpa.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CustomJpaRepository<Transaction,Long> {
    @Query(value = "SELECT * FROM transactions as t WHERE ?1 <= t.transaction_date AND t.transaction_date <= ?2 AND t.budget_id = ?3", nativeQuery = true)
    List<Transaction> getTransactionByBudget(Long budgetDateStart, Long budgetDateEnd, Long budgetId);
}
