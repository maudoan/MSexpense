package com.mysunshine.ms.jpa.services;

import com.mysunshine.ms.jpa.CrudService;
import com.mysunshine.ms.jpa.models.TransactionCategoryParent;
import com.mysunshine.ms.jpa.repositories.TransactionCategoryParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionCategoryParentService extends CrudService<TransactionCategoryParent, Long> {
    @Autowired
    TransactionCategoryParentRepository transactionCategoryParentRepository;

    public TransactionCategoryParentService(TransactionCategoryParentRepository transactionCategoryParentRepository) {
        super(TransactionCategoryParent.class);
        this.repository = this.transactionCategoryParentRepository = transactionCategoryParentRepository;
    }
}
