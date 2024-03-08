package com.mysunshine.ms.jpa.repositories;

import com.mysunshine.ms.jpa.CustomJpaRepository;
import com.mysunshine.ms.jpa.models.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CustomJpaRepository<Transaction,Long> {
}
