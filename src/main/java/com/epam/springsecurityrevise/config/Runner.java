package com.epam.springsecurityrevise.config;

import com.epam.springsecurityrevise.service.DataInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final DataInitService dataInitService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Override
    public void run(String... args) {
        if (ddlAuto.contains("create")) {
            dataInitService.initConstants();
        }
    }
}
