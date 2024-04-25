package com.mysunshine.ms.jpa.services;

import com.mysunshine.ms.jpa.CrudService;
import com.mysunshine.ms.jpa.models.Transaction;
import com.mysunshine.ms.jpa.models.User;
import com.mysunshine.ms.jpa.repositories.TransactionRepository;
import com.mysunshine.ms.jpa.repositories.UserRepository;
import com.mysunshine.ms.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends CrudService<Transaction, Long> {

    @Autowired
    TransactionRepository transactionRepository;

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
        Long totalBalance;
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
        Long totalBalance;
        if(entity.getTransactionType() == 1) {
            totalBalance = user.getTotalBalance() + entity.getAmount();
        } else {
            totalBalance = user.getTotalBalance() - entity.getAmount();
        }
        user.setTotalBalance(totalBalance);
        userRepository.save(user);
    }
}
