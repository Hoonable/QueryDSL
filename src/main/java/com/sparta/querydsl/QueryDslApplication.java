package com.sparta.querydsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class QueryDslApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryDslApplication.class, args);
    }

}
