package com.petlearning.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 監控和指標應用程式
 * 
 * 本應用程式整合 Prometheus 指標收集和 Grafana 可視化。
 * 
 * 核心功能：
 * 1. 通過 Micrometer 收集應用指標
 * 2. Actuator 端點暴露健康檢查和性能指標
 * 3. Prometheus 抓取指標數據
 * 4. Grafana 創建告警規則和告警通知
 * 
 * 訪問地址：
 * - 應用健康檢查：http://localhost:8080/actuator/health
 * - Prometheus 指標：http://localhost:8080/actuator/prometheus
 * - Prometheus 伺服器：http://localhost:9090
 * - Grafana 儀表板：http://localhost:3000
 * 
 * 常見指標：
 * - jvm.memory.used - JVM 記憶體使用
 * - http.server.requests - HTTP 請求延遲
 * - process.cpu.usage - CPU 使用率
 * 
 * TDD 練習：
 * 1. 編寫測試驗證自定義指標
 * 2. 測試 Actuator 端點可用性
 * 3. 驗證指標導出格式符合 Prometheus 標準
 */
@SpringBootApplication
public class MonitoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringApplication.class, args);
    }
}
