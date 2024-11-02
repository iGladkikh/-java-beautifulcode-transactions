package com.bank.transactions.service;

import org.springframework.beans.factory.annotation.Qualifier;

class MapBasedTransactionProcessorTest extends AbstractTransactionProcessorTest {

    MapBasedTransactionProcessorTest(@Qualifier("MapBasedTransactionProcessor") TransactionProcessor service) {
        super(service);
    }
}