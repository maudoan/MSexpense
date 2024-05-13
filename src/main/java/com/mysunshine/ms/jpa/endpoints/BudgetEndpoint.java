package com.mysunshine.ms.jpa.endpoints;

import com.mysunshine.ms.jpa.CrudEndpoint;
import com.mysunshine.ms.jpa.models.Budget;
import com.mysunshine.ms.jpa.services.BudgetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/budget")
public class BudgetEndpoint extends CrudEndpoint<Budget, Long> {

    private BudgetService budgetService;

    public BudgetEndpoint(BudgetService budgetService) {
        super(budgetService);
        this.budgetService = budgetService;
        this.baseUrl = "/api/budget";
    }
}
