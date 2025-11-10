package com.petlearning.circuitbreaker.controller;

import com.petlearning.circuitbreaker.service.ExternalServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 斷路器演示端點
 * 
 * TDD 測試場景：
 * 1. 正常情況：服務可用，返回正常響應
 * 2. 故障情況：服務不可用，返回降級響應
 * 3. 恢復情況：服務恢復後，斷路器自動關閉
 */
@RestController
@RequestMapping("/api/circuit-breaker")
public class CircuitBreakerDemoController {

    @Autowired
    private ExternalServiceClient externalServiceClient;

    /**
     * 測試斷路器
     * 
     * 使用方式：
     * GET /api/circuit-breaker/test?url=http://example.com
     */
    @GetMapping("/test")
    public String testCircuitBreaker(@RequestParam(defaultValue = "http://external-service.com") String url) {
        return externalServiceClient.callExternalService(url);
    }

    /**
     * 健康檢查
     */
    @GetMapping("/health")
    public String health() {
        return "Circuit Breaker Demo is running";
    }
}
