package com.example.poatenumergi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication

@EntityScan("com.example.poatenumergi.model")
public class PoatenumergiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoatenumergiApplication.class, args);
    }

}
