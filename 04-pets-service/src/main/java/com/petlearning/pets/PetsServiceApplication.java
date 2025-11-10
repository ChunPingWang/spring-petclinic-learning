package com.petlearning.pets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Pets Service 啟動類
 * 獨立的寵物管理微服務
 */
@SpringBootApplication
public class PetsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetsServiceApplication.class, args);
    }
}
