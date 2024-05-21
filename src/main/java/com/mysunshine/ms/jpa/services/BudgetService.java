package com.mysunshine.ms.jpa.services;

import com.mysunshine.ms.jpa.CrudService;
import com.mysunshine.ms.jpa.models.Budget;
import com.mysunshine.ms.jpa.models.User;
import com.mysunshine.ms.jpa.repositories.BudgetRepository;
import com.mysunshine.ms.jpa.repositories.UserRepository;
import com.mysunshine.ms.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService extends CrudService<Budget, Long> {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    UserRepository userRepository;
    public BudgetService(BudgetRepository budgetRepository) {
        super(Budget.class);
        this.repository = this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget create(Budget entity) {

        List<Budget> budgetList = budgetRepository.existsBudget(entity.getBudgetDateStart(), entity.getBudgetDateEnd(), entity.getCategoryId());
        if(!budgetList.isEmpty()) {
            return null;
        }
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + SecurityUtils.getCurrentUserLogin()));

        entity.setUser(user);
        entity.setBudgetBalance(0L);
        super.create(entity);
        return entity;
    }
}
