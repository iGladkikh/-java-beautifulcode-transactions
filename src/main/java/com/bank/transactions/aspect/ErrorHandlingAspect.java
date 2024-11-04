package com.bank.transactions.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Класс предназначен для централизованной обработки исключений в сервисном слое.
 */

@Slf4j
@Aspect
@Component
@Profile("!test")
public class ErrorHandlingAspect {

    @Pointcut("execution(* com.bank.transactions.service.*.*(..))")
    static void allServiceMethods() {
    }

    @AfterThrowing(value = "allServiceMethods()", throwing = "e")
    public void afterThrowingAdvice(Exception e) {
        log.error("After throwing advice. Exception: {}", e.getMessage());
    }
}
