package com.petlearning.tracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分散式追蹤伺服器 - Zipkin
 * 
 * 本應用程式集成 Spring Cloud Sleuth 和 Zipkin，提供：
 * 1. 自動 Trace ID 和 Span ID 生成
 * 2. 跨服務追蹤
 * 3. 性能分析和延遲監測
 * 4. 可視化界面查看調用鏈
 * 
 * 核心概念：
 * - Trace ID：整個分散式調用鏈的唯一識別符
 * - Span：調用鏈中的單個操作
 * - 例如：客戶端 → Gateway → CustomerService → DatabaseQuery
 *         這個完整流程就是一個 Trace
 * 
 * 訪問地址：
 * - Zipkin UI: http://localhost:9411
 * - 查看 Trace：http://localhost:9411/zipkin/
 * 
 * TDD 練習：
 * 1. 編寫測試驗證 Trace ID 傳播
 * 2. 測試跨服務調用的 Trace 完整性
 * 3. 驗證日誌中包含 Trace ID
 */
@SpringBootApplication
public class TracingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TracingServerApplication.class, args);
    }
}
