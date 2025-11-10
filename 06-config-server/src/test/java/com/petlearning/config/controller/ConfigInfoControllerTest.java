package com.petlearning.config.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Config Server 控制器測試
 * 
 * TDD 練習：
 * - 測試健康檢查端點
 * - 測試配置伺服器信息端點
 */
@WebMvcTest(ConfigInfoController.class)
class ConfigInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_ReturnSuccess_When_HealthCheckCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk());
    }

    @Test
    void should_ReturnSuccess_When_InfoCalled() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk());
    }
}
