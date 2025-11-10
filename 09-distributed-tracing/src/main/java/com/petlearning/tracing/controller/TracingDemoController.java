package com.petlearning.tracing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分散式追蹤示例端點
 * 
 * 說明：
 * 當客戶端調用此端點時，Sleuth 會自動：
 * 1. 生成 Trace ID 和 Span ID
 * 2. 將它們注入到日誌中
 * 3. 如果有跨服務調用，會自動傳播 Trace ID
 * 
 * 日誌格式範例：
 * [trace-id,span-id] - message
 */
@RestController
@RequestMapping("/api/tracing")
public class TracingDemoController {

    private static final Logger logger = LoggerFactory.getLogger(TracingDemoController.class);

    /**
     * 簡單追蹤端點
     * 
     * 使用方式：
     * GET /api/tracing/demo
     * 
     * 查看日誌輸出會看到 Trace ID
     */
    @GetMapping("/demo")
    public String demo() {
        logger.info("Processing trace demo request");
        return "Trace demo - check logs for trace ID";
    }

    /**
     * 多步操作追蹤
     * 
     * 模擬一個包含多個步驟的業務流程
     */
    @GetMapping("/multi-step")
    public String multiStep() {
        logger.info("Step 1: 接收請求");
        step1();
        logger.info("Step 2: 處理業務邏輯");
        step2();
        logger.info("Step 3: 返回結果");
        return "Multi-step operation completed";
    }

    private void step1() {
        logger.info("Executing step 1");
    }

    private void step2() {
        logger.info("Executing step 2");
    }

    /**
     * 健康檢查
     */
    @GetMapping("/health")
    public String health() {
        logger.info("Health check called");
        return "Tracing Server is running";
    }
}
