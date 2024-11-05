package com.bank.transactions.service;

import com.bank.transactions.model.Transaction;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface TransactionProcessor {

    Transaction getTransaction(Long id);

    CompletableFuture<Transaction> addTransaction(double amount);

    void processTransactions(Collection<Long> ids);

    CompletableFuture<Transaction> processTransaction(Long id);

    int getTransactionsCount();
}
