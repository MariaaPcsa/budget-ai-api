package com.budgetai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class BudgetAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BudgetAiApplication.class, args);
    }
}