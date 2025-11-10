package com.petlearning.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 應用程式啟動類
 * 
 * @SpringBootApplication 是一個組合註解，包含：
 * - @Configuration: 標記為配置類
 * - @EnableAutoConfiguration: 啟用自動配置
 * - @ComponentScan: 啟用元件掃描
 */
@SpringBootApplication
public class BasicSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicSpringBootApplication.class, args);
    }
}
