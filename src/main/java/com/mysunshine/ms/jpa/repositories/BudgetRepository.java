package com.mysunshine.ms.jpa.repositories;

import com.mysunshine.ms.jpa.CustomJpaRepository;
import com.mysunshine.ms.jpa.models.Budget;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends CustomJpaRepository<Budget,Long> {
    @Query(value = "SELECT * FROM budget as t WHERE ?1 <= t.budget_date_end AND t.budget_date_start <= ?2 AND t.category_id = ?3", nativeQuery = true)
    List<Budget> existsBudget(Long budgetDateStart,Long budgetDateEnd, Long categoryId);
    @Query(value = "SELECT * FROM budget as t WHERE ?1 <= t.budget_date_end AND t.budget_date_start <= ?1 AND t.category_id = ?2 LIMIT 1", nativeQuery = true)
    Budget budgetByDateAndCategoryId(Long date, Long categoryId);
}
