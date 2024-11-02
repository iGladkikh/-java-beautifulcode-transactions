package com.bank.transactions.service;

import org.springframework.beans.factory.annotation.Qualifier;

class ListBasedTransactionProcessorTest extends AbstractTransactionProcessorTest {

    ListBasedTransactionProcessorTest(@Qualifier("ListBasedTransactionProcessor") TransactionProcessor service) {
        super(service);
    }
}