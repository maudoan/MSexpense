package com.mysunshine.ms.jpa.services;

import com.mysunshine.ms.jpa.CrudService;
import com.mysunshine.ms.jpa.models.Budget;
import com.mysunshine.ms.jpa.models.Transaction;
import com.mysunshine.ms.jpa.models.User;
import com.mysunshine.ms.jpa.repositories.BudgetRepository;
import com.mysunshine.ms.jpa.repositories.TransactionRepository;
import com.mysunshine.ms.jpa.repositories.UserRepository;
import com.mysunshine.ms.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionService extends CrudService<Transaction, Long> {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    UserRepository userRepository;
    public TransactionService(TransactionRepository transactionRepository) {
        super(Transaction.class);
        this.repository = this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction create(Transaction entity) {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + SecurityUtils.getCurrentUserLogin()));

        entity.setUser(user);
        long totalBalance;
        if(entity.getTransactionType() == 1) {
            totalBalance = user.getTotalBalance() - entity.getAmount();
        } else {
            totalBalance = user.getTotalBalance() + entity.getAmount();
        }
        user.setTotalBalance(totalBalance);
        super.create(entity);
        return entity;
    }

    @Override
    public void afterDelete(Transaction entity){
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + SecurityUtils.getCurrentUserLogin()));
        long totalBalance;
        if(entity.getTransactionType() == 1) {
            totalBalance = user.getTotalBalance() + entity.getAmount();
        } else {
            totalBalance = user.getTotalBalance() - entity.getAmount();
        }
        user.setTotalBalance(totalBalance);
        userRepository.save(user);
        Budget budget = budgetRepository.budgetByDateAndCategoryId(entity.getTransactionDate(),entity.getCategoryId());

        if(budget != null && entity.getTransactionType() == 1) {
            entity.setBudgetId(budget.getId());
            budget.setBudgetBalance(budget.getBudgetBalance() + entity.getAmount());
            budgetRepository.save(budget);
        }
    }

    @Override
    public void afterCreate(Transaction entity){
        Budget budget = budgetRepository.budgetByDateAndCategoryId(entity.getTransactionDate(),entity.getCategoryId());

        if(budget != null && entity.getTransactionType() == 1) {
            entity.setBudgetId(budget.getId());
            budget.setBudgetBalance(budget.getAmount() - entity.getAmount());
            budgetRepository.save(budget);
        }
    }

    @Override
    public void beforeUpdate(Transaction entity){

    }

    @Override
    public void afterUpdate(Transaction old, Transaction entity){

        if(!Objects.equals(old.getCategoryId(), entity.getCategoryId())) {
            Budget budget = budgetRepository.budgetByDateAndCategoryId(old.getTransactionDate(),old.getCategoryId());
            if(budget != null && entity.getTransactionType() == 1) {
                entity.setBudgetId(budget.getId());
                budget.setBudgetBalance(budget.getBudgetBalance() - entity.getAmount());
                budgetRepository.save(budget);
            }
        } else {
            Budget budget = budgetRepository.budgetByDateAndCategoryId(entity.getTransactionDate(),entity.getCategoryId());

            if(budget != null && entity.getTransactionType() == 1) {
                entity.setBudgetId(budget.getId());
                budget.setBudgetBalance(budget.getBudgetBalance() + old.getAmount() - entity.getAmount());
                budgetRepository.save(budget);
            }
        }

    }
}
