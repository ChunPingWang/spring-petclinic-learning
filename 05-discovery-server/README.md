# 模組 05: Eureka 服務發現

## 學習目標

完成本模組後，你將能夠：

- ✅ 理解服務發現（Service Discovery）的概念
- ✅ 配置和啟動 Eureka Server
- ✅ 將微服務註冊到 Eureka
- ✅ 實現服務自動發現和負載平衡
- ✅ 監控服務的健康狀態

## 核心概念

### 1. 服務發現的必要性

**問題**：
- 微服務數量多時，難以手動管理服務地址
- 服務實例動態增減，IP 不固定
- 如何讓客戶端發現可用的服務？

**解決方案**：
- Eureka：Netflix 開源的服務發現框架
- 自動註冊、自動發現、自動負載平衡

### 2. Eureka 架構

```
┌─────────────────────────────────────────┐
│      Eureka Server (Port 8761)          │
│  ┌─────────────────────────────────┐   │
│  │  Service Registry (服務註冊表)  │   │
│  │  ┌─────────────────────────────┐│   │
│  │  │ customers-service (8081)    ││   │
│  │  │ customers-service (8081)    ││   │
│  │  │ pets-service (8082)         ││   │
│  │  └─────────────────────────────┘│   │
│  └─────────────────────────────────┘   │
│          ▲         ▲         ▲          │
└──────────┼─────────┼─────────┼──────────┘
           │         │         │
      Heartbeat   Heartbeat  Heartbeat
      (30秒)      (30秒)     (30秒)
           │         │         │
    ┌──────┴─────────┴─────────┴──────┐
    │                                  │
Client (API Gateway)   Service (需要調用其他服務)
```

### 3. 心跳檢測（Heartbeat）

- 每 30 秒服務發送一次心跳
- 90 秒未收到心跳視為服務離線
- 自動從註冊表移除離線服務

## 快速開始

### 前置要求
- 完成模組 04（微服務基礎）

### 啟動步驟

```bash
# 1. 編譯本模組
mvn clean install

# 2. 啟動 Eureka Server
mvn spring-boot:run

# 3. 訪問 Eureka Dashboard
http://localhost:8761
```

### Eureka Dashboard

訪問 http://localhost:8761 可看到：
- 所有已註冊的服務
- 各服務的實例信息
- 服務健康狀態

## 學習計劃

### Day 13: Eureka 服務發現基礎

**理論**：Eureka 架構、服務註冊、心跳機制

**實作**：
1. 建立 Eureka Server
2. 配置服務端口 8761
3. 啟用 Dashboard

### Day 14: 服務註冊與發現

**實作**：
1. Customers Service 註冊到 Eureka
2. Pets Service 註冊到 Eureka
3. 驗證 Dashboard 顯示所有服務

### Day 15: 負載平衡與故障轉移

**實作**：
1. 配置負載平衡
2. 啟動多個實例
3. 驗證請求分散到不同實例

## 依賴配置

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

## 服務端點

| 端點 | 說明 |
|------|------|
| GET / | Eureka 首頁 |
| GET /eureka/apps | 所有已註冊應用 |
| GET /eureka/apps/{appName} | 特定應用的實例 |

---

**相關模組**: [模組 06 - Config Server](../06-config-server/README.md)
