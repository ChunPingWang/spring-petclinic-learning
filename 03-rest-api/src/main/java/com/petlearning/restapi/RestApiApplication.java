package com.petlearning.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RESTful API 應用程式啟動類
 * 
 * 本模組學習內容：
 * - REST 架構設計
 * - 完整的 CRUD API
 * - 全域異常處理
 * - 統一回應格式
 */
@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }
}
