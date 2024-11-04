package com.bank.transactions.console;

import com.bank.transactions.model.Transaction;
import com.bank.transactions.service.TransactionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("!test")
public class ConsoleRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final TransactionProcessor service;

    @Autowired
    public ConsoleRunner(@Qualifier("MapBasedTransactionProcessor") TransactionProcessor service) {
        this.service = service;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void consoleRun() {
        while (true) {
            try {
                printMenu();

                String command = scanner.nextLine();
                switch (command) {
                    case "1":
                        System.out.println("Введите сумму");
                        double amount = Double.parseDouble(scanner.nextLine());
                        service.addTransaction(amount);
                        break;
                    case "2":
                        System.out.println("Введите id");
                        service.processTransaction(Long.parseLong(scanner.nextLine()));
                        break;
                    case "3":
                        System.out.println("Введите id");
                        Transaction transaction = service.getTransaction(Long.parseLong(scanner.nextLine()));
                        System.out.println(transaction);
                        break;
                    case "0":
                        return;
                }
            } catch (Exception e) {
                System.out.println("Ошибочка вышла");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Добавить новую транзакцию");
        System.out.println("2 - Провести транзакцию");
        System.out.println("3 - Показать транзакцию");
        System.out.println("0 - Выход");
    }
}