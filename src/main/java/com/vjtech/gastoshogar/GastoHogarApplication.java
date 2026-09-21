package com.vjtech.gastoshogar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class GastoHogarApplication {
    public static void main(String[] args) {
        SpringApplication.run(GastoHogarApplication.class, args);
    }
}