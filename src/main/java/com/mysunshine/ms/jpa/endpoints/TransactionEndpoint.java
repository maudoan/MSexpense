package com.mysunshine.ms.jpa.endpoints;

import com.mysunshine.ms.jpa.CrudEndpoint;
import com.mysunshine.ms.jpa.models.Transaction;
import com.mysunshine.ms.jpa.services.TransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/transactions")
public class TransactionEndpoint extends CrudEndpoint<Transaction, Long> {

    private TransactionService transactionService;

    public TransactionEndpoint(TransactionService transactionService) {
        super(transactionService);
        this.transactionService = transactionService;
        this.baseUrl = "/api/transactions";
    }
}
