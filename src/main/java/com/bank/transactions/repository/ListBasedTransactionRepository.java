
package com.bank.transactions.repository;

import com.bank.transactions.annotation.AmountAuditable;
import com.bank.transactions.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Класс используется только для тестирования.
 */

@Repository("ListBasedRepository")
public class ListBasedTransactionRepository implements TransactionRepository {
    private final CopyOnWriteArrayList<Transaction> transactions = new CopyOnWriteArrayList<>();

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findFirst();
    }

    @Override
    @AmountAuditable
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public int size() {
        return transactions.size();
    }
}
