package com.mysunshine.ms.jpa.repositories;

import com.mysunshine.ms.jpa.CustomJpaRepository;
import com.mysunshine.ms.jpa.models.TransactionCategoryParent;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionCategoryParentRepository extends CustomJpaRepository<TransactionCategoryParent,Long> {
}
