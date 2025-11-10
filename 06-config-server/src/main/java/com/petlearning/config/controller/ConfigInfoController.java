package com.petlearning.config.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Config Server 演示端點
 * 
 * 說明：
 * - Config Server 通過 REST API 提供配置
 * - 客戶端應用程式通過 bootstrap.yml 指定 Config Server 位置
 * - 配置更新後，可通過 @RefreshScope 動態刷新（無需重啟）
 * 
 * 使用場景：
 * 1. 集中管理所有服務的配置
 * 2. 支援不同環境的配置隔離（dev/test/prod）
 * 3. 配置版本控制（使用 Git）
 */
@RestController
@RequestMapping("/api")
public class ConfigInfoController {

    /**
     * 健康檢查
     */
    @GetMapping("/health")
    public String health() {
        return "Config Server is running";
    }

    /**
     * 獲取伺服器信息
     */
    @GetMapping("/info")
    public String info() {
        return "Config Server - Centralized Configuration Management";
    }
}
