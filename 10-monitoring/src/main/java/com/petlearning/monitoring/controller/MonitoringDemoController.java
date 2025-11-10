package com.petlearning.monitoring.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 監控指標演示端點
 * 
 * 說明：
 * - Micrometer 自動收集 HTTP 請求指標
 * - 自定義指標可通過 MeterRegistry 實現
 * - 所有指標通過 /actuator/prometheus 端點暴露
 */
@RestController
@RequestMapping("/api/monitoring")
public class MonitoringDemoController {

    @Autowired
    private MeterRegistry meterRegistry;

    private final Counter customCounter;

    public MonitoringDemoController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        // 創建自定義計數器
        this.customCounter = Counter.builder("custom.operation.count")
                .description("Custom operation counter")
                .register(meterRegistry);
    }

    /**
     * 生成自定義指標
     * 
     * 使用方式：
     * GET /api/monitoring/custom-metric
     * 
     * 然後查看 /actuator/prometheus 可以看到 custom_operation_count 增加
     */
    @GetMapping("/custom-metric")
    public String customMetric() {
        customCounter.increment();
        return "Custom metric incremented";
    }

    /**
     * 健康檢查
     */
    @GetMapping("/health")
    public String health() {
        return "Monitoring Server is running";
    }

    /**
     * 獲取當前指標統計
     */
    @GetMapping("/stats")
    public String stats() {
        double count = customCounter.count();
        return "Custom operation count: " + count;
    }
}
