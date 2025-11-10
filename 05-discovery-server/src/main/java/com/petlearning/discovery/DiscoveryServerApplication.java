package com.petlearning.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服務發現伺服器 - Eureka
 * 
 * 本應用程式作為微服務架構的服務註冊中心，負責：
 * 1. 接收各個微服務的註冊請求
 * 2. 管理服務實例的生命週期（心跳檢測）
 * 3. 提供服務發現能力給消費者
 * 4. 提供 Eureka Dashboard 用於監控
 * 
 * 訪問地址：http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
