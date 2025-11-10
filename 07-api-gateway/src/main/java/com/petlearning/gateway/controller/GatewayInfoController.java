package com.petlearning.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API Gateway 演示端點
 * 
 * 說明：
 * - Gateway 作為統一入口，所有客戶端請求都通過此端點
 * - 通過路由規則將請求轉發到後端微服務
 * - 可在此層實現通用邏輯（認證、限流、日誌等）
 * 
 * 使用場景：
 * 1. 版本控制和 API 文檔
 * 2. 連接信息和健康狀態
 * 3. 路由規則檢查
 */
@RestController
@RequestMapping("/api")
public class GatewayInfoController {

    /**
     * 健康檢查
     */
    @GetMapping("/health")
    public String health() {
        return "API Gateway is running";
    }

    /**
     * 獲取路由信息
     */
    @GetMapping("/routes")
    public String routes() {
        return "Available routes: /customers, /pets";
    }

    /**
     * 獲取伺服器信息
     */
    @GetMapping("/info")
    public String info() {
        return "API Gateway - Unified Service Entry Point";
    }
}
