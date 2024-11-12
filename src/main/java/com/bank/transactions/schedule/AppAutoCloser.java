package com.bank.transactions.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@Profile("!test")
public class AppAutoCloser {
    private static final long N_SECONDS_BEFORE_ALERT = 5;
    private static final long N_SECONDS_BEFORE_EXIT = 10;
    private static Instant lastActivity = Instant.now();

    private final ConfigurableApplicationContext context;

    public AppAutoCloser(ConfigurableApplicationContext context) {
        this.context = context;
    }

    public static void updateActivity() {
        lastActivity = Instant.now();
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void everySecond() {
        long secondsBeforeAppClosing = Duration.between(Instant.now(),
                lastActivity.plus(N_SECONDS_BEFORE_EXIT, ChronoUnit.SECONDS)).toSeconds();

        if (secondsBeforeAppClosing <= 0) {
            closeApp();
        } else if (secondsBeforeAppClosing <= N_SECONDS_BEFORE_ALERT) {
            System.out.printf("\nПрограмма завершится через %d секунд", secondsBeforeAppClosing);
        }
    }

    private void closeApp() {
        ThreadPoolTaskScheduler taskScheduler = context.getBean(ThreadPoolTaskScheduler.class);
        taskScheduler.shutdown();
        context.close();

        System.out.printf("\nПрограмма завершена по причине %d-ти секундного простоя", N_SECONDS_BEFORE_EXIT);
        System.exit(0);
    }
}
