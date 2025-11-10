package com.petlearning.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Data JPA 應用程式啟動類
 * 
 * 本模組學習內容：
 * - Spring Data JPA 基礎
 * - H2 記憶體資料庫
 * - Entity 與 Repository
 * - 資料關聯映射
 */
@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }
}
