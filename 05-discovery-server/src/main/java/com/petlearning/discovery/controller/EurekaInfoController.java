package com.petlearning.discovery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Eureka Server 演示端點
 * 
 * 說明：
 * - Eureka Dashboard 提供 Web UI: http://localhost:8761
 * - 此端點提供 API 方式訪問服務實例信息
 * 
 * 使用場景：
 * 1. 驗證服務是否正確註冊
 * 2. 檢查服務實例的健康狀態
 * 3. 手動觸發服務下線
 */
@RestController
@RequestMapping("/api")
public class EurekaInfoController {

    /**
     * 健康檢查
     */
    @GetMapping("/health")
    public String health() {
        return "Eureka Server is running";
    }

    /**
     * 獲取伺服器信息
     */
    @GetMapping("/info")
    public String info() {
        return "Eureka Discovery Server - Service Registry for Microservices";
    }
}
