package com.petlearning.circuitbreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 斷路器示例應用程式
 * 
 * 本模組展示如何使用 Resilience4j 實現斷路器模式。
 * 
 * 斷路器核心概念：
 * 1. 監視遠端服務調用
 * 2. 當失敗次數/比例達到閾值時，打開斷路器
 * 3. 新請求不再轉發到失敗服務，而是直接返回降級響應
 * 4. 經過設定時間後，進入半開狀態進行恢復探測
 * 5. 探測成功則關閉斷路器，恢復正常
 * 
 * 狀態轉換：CLOSED → OPEN → HALF_OPEN → CLOSED
 * 
 * TDD 練習步驟：
 * 1. 編寫測試模擬服務故障
 * 2. 驗證斷路器打開
 * 3. 驗證降級方法被調用
 * 4. 測試恢復流程
 */
@SpringBootApplication
public class CircuitBreakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerApplication.class, args);
    }
}
