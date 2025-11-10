package com.petlearning.tracing.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 分散式追蹤控制器測試
 * 
 * TDD 練習：
 * - 測試追蹤端點可用性
 * - 驗證日誌包含 Trace ID
 * - 測試多步操作追蹤
 */
@WebMvcTest(TracingDemoController.class)
class TracingDemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_ReturnSuccess_When_TracingDemoCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/tracing/demo"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnSuccess_When_MultiStepCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/tracing/multi-step"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnSuccess_When_HealthCheckCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/tracing/health"))
                .andExpect(status().isOk());
    }
}
