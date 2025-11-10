package com.petlearning.monitoring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 監控控制器測試
 * 
 * TDD 練習：
 * - 測試自定義指標端點
 * - 驗證健康檢查端點
 * - 測試統計端點
 */
@WebMvcTest(MonitoringDemoController.class)
class MonitoringDemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_ReturnSuccess_When_HealthCheckCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/monitoring/health"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnSuccess_When_CustomMetricCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/monitoring/custom-metric"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnSuccess_When_StatsCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/monitoring/stats"))
                .andExpect(status().isOk());
    }
}
