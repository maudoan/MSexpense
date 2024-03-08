package com.mysunshine.ms.jpa.endpoints;

import com.mysunshine.ms.jpa.CrudEndpoint;
import com.mysunshine.ms.jpa.models.TransactionCategoryParent;
import com.mysunshine.ms.jpa.services.TransactionCategoryParentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/transaction-category-parent")
public class TransactionCategoryParentEndpoint extends CrudEndpoint<TransactionCategoryParent, Long> {

    private TransactionCategoryParentService transactionCategoryParentService;

    public TransactionCategoryParentEndpoint(TransactionCategoryParentService transactionCategoryParentService) {
        super(transactionCategoryParentService);
        this.transactionCategoryParentService = transactionCategoryParentService;
        this.baseUrl = "/api/transaction-category-parent";
    }
}
