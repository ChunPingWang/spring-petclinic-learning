package com.petlearning.circuitbreaker.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 斷路器服務測試
 * 
 * TDD 練習：
 * - 測試正常調用
 * - 測試降級行為
 * - 測試斷路器狀態轉換
 */
@SpringJUnitConfig
class ExternalServiceClientTest {

    @Test
    void should_Demo_ExternalServiceCallConcepts() {
        // TDD 練習：編寫測試驗證外部服務調用
        // 1. 正常情況：成功返回結果
        // 2. 故障情況：達到失敗閾值時打開斷路器
        // 3. 降級情況：返回降級響應
        System.out.println("External Service Client Demo Test");
    }
}
