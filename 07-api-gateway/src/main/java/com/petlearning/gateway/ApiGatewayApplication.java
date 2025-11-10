package com.petlearning.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * API 閘道
 * 
 * 本應用程式作為微服務系統的統一入口點，負責：
 * 1. 請求路由 - 將客戶端請求轉發到對應的微服務
 * 2. 負載均衡 - 根據 Eureka 服務發現動態調整
 * 3. 請求過濾 - 執行跨服務的通用邏輯（認證、限流等）
 * 4. 協議轉換 - 統一客戶端與微服務之間的通訊
 * 
 * 訪問地址：http://localhost:8080
 * 
 * 設計要點：
 * - 客戶端只需要知道 Gateway 地址，無需知道各個微服務位置
 * - 微服務可以獨立擴展，Gateway 通過 Eureka 自動發現
 * - 通過 Filter 可以實現統一的安全、日誌、監控等功能
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    /**
     * 配置路由規則
     * 
     * TDD 練習：
     * 1. 編寫測試驗證路由規則正確性
     * 2. 測試斷路器觸發時的降級行為
     * 3. 測試限流過濾器
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 客戶服務路由
                .route("customers-service", 
                    r -> r.path("/api/customers/**")
                          .uri("lb://customers-service"))
                
                // 寵物服務路由
                .route("pets-service",
                    r -> r.path("/api/pets/**")
                          .uri("lb://pets-service"))
                
                // 健康檢查
                .route("health-check",
                    r -> r.path("/health")
                          .uri("no://op"))
                
                .build();
    }
}
