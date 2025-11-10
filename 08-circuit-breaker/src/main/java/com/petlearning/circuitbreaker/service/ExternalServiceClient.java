package com.petlearning.circuitbreaker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * 外部服務調用示例
 * 
 * 展示如何使用 Resilience4j 的 @CircuitBreaker 註解
 * TDD 練習：編寫測試模擬服務故障並驗證降級行為
 */
@Service
public class ExternalServiceClient {
    
    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceClient.class);

    /**
     * 調用外部服務
     * 
     * @CircuitBreaker 註解自動應用斷路器邏輯
     * - 監視調用並記錄失敗
     * - 當失敗率達到 50% 時打開斷路器
     * - 新請求直接調用 fallback 方法
     */
    @CircuitBreaker(name = "externalServiceCall", fallbackMethod = "callServiceFallback")
    public String callExternalService(String serviceUrl) {
        logger.info("Calling external service: {}", serviceUrl);
        // 模擬 REST 調用
        // RestTemplate 或 WebClient 實現
        return "Response from " + serviceUrl;
    }

    /**
     * 降級方法
     * 當斷路器打開時調用此方法
     */
    public String callServiceFallback(String serviceUrl, Exception ex) {
        logger.warn("Circuit breaker activated for: {}, using fallback", serviceUrl);
        return "Fallback response - service unavailable";
    }
}
