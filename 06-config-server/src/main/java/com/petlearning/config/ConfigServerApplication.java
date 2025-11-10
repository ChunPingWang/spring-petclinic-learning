package com.petlearning.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 集中配置伺服器
 * 
 * 本應用程式提供統一的配置管理服務，支援：
 * 1. 從 Git 儲存庫讀取配置
 * 2. 提供環境隔離（開發、測試、生產）
 * 3. 動態配置更新（无需重啟服務）
 * 4. 配置版本管理
 * 
 * 訪問地址：http://localhost:8888
 * API 端點：/config/{app}/{profile}
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
