# Spring Pet Learning - 完整啟動指南

本文件說明如何啟動完整的微服務系統（所有 10 個模組）。

## 🚀 快速啟動

### 前置條件
- Java 17+
- Maven 3.8+
- 建議使用 IntelliJ IDEA 或 VS Code

### 步驟 1: 編譯所有模組

```bash
# 在根目錄執行
mvn clean install
```

### 步驟 2: 按順序啟動模組

**啟動順序很重要！** 需要先啟動基礎設施服務，再啟動應用服務。

#### 第一階段：基礎設施服務（必須先啟動）

**1. 啟動 Eureka Server（模組 05）**
```bash
cd 05-discovery-server
mvn spring-boot:run
```
- 訪問 Dashboard：http://localhost:8761

**2. 啟動 Config Server（模組 06）**
```bash
cd 06-config-server
mvn spring-boot:run
```
- 健康檢查：http://localhost:8888/api/health

**3. 啟動 API Gateway（模組 07）**
```bash
cd 07-api-gateway
mvn spring-boot:run
```
- 訪問 Gateway：http://localhost:8080/api/info

#### 第二階段：應用服務

**4. 啟動 Customers Service（模組 04a）**
```bash
cd 04-customers-service
mvn spring-boot:run
```
- 服務端口：8081
- 訪問：http://localhost:8081/actuator/health

**5. 啟動 Pets Service（模組 04b）**
```bash
cd 04-pets-service
mvn spring-boot:run
```
- 服務端口：8082
- 訪問：http://localhost:8082/actuator/health

#### 第三階段：進階功能

**6. 啟動 Circuit Breaker Demo（模組 08）**
```bash
cd 08-circuit-breaker
mvn spring-boot:run
```
- 測試：http://localhost:8084/api/circuit-breaker/health

**7. 啟動 Distributed Tracing Server（模組 09）**
```bash
cd 09-distributed-tracing
mvn spring-boot:run
```
- Zipkin UI：http://localhost:9411

**8. 啟動 Monitoring Server（模組 10）**
```bash
cd 10-monitoring
mvn spring-boot:run
```
- 健康檢查：http://localhost:8080/actuator/health
- Prometheus 指標：http://localhost:8080/actuator/prometheus

**9-10. 其他基礎模組（開發用）**
```bash
cd 01-basic-spring-boot
mvn spring-boot:run

cd 02-spring-data-jpa
mvn spring-boot:run

cd 03-rest-api
mvn spring-boot:run
```

## 📊 Eureka Dashboard 驗證

啟動所有服務後，訪問 Eureka Dashboard：
http://localhost:8761

應該看到以下已註冊的服務：
- CUSTOMERS-SERVICE
- PETS-SERVICE
- CIRCUIT-BREAKER-DEMO
- ZIPKIN-SERVER
- MONITORING-SERVER
- API-GATEWAY

## 🧪 測試 API 端點

### 通過 Gateway 訪問服務

```bash
# 訪問 Customers 服務（通過 Gateway）
curl http://localhost:8080/api/customers

# 訪問 Pets 服務（通過 Gateway）
curl http://localhost:8080/api/pets

# Gateway 健康檢查
curl http://localhost:8080/api/health
```

### 直接訪問服務

```bash
# Customers Service（直接訪問）
curl http://localhost:8081/api/owners

# Pets Service（直接訪問）
curl http://localhost:8082/api/pets

# Circuit Breaker 測試
curl http://localhost:8084/api/circuit-breaker/test

# 追蹤演示
curl http://localhost:9411/api/tracing/demo

# 監控指標
curl http://localhost:8080/actuator/metrics
```

## 🛑 優雅關閉

按 `Ctrl + C` 順序停止各個服務（建議反向順序停止）

## 🐛 故障排除

### Q: 某個服務無法啟動
A: 檢查端口是否被佔用
```bash
# macOS/Linux - 查看端口使用
lsof -i :8761
lsof -i :8080
lsof -i :8081

# 殺死進程
kill -9 <PID>
```

### Q: Gateway 無法路由到後端服務
A: 確保：
1. Eureka Server 已啟動
2. 後端服務已成功註冊到 Eureka
3. 檢查 Eureka Dashboard 確認服務狀態

### Q: 看不到 Trace 信息
A: 確保 Zipkin Server 已啟動，且應用配置正確

### Q: 指標為空
A: 確保應用已處理至少一個請求，Prometheus 指標需要數據才能顯示

## 📚 學習路徑

建議按以下順序學習：

1. **Day 1-3**: 基礎 Spring Boot（模組 01）
   - 啟動 01-basic-spring-boot
   
2. **Day 4-6**: 數據持久化（模組 02）
   - 啟動 02-spring-data-jpa
   
3. **Day 7-9**: REST API（模組 03）
   - 啟動 03-rest-api
   
4. **Day 10-12**: 微服務基礎（模組 04）
   - 同時啟動 04-customers-service 和 04-pets-service
   
5. **Day 13-15**: 服務發現（模組 05）
   - 啟動 05-discovery-server
   - 觀察服務如何自動發現
   
6. **Day 16-18**: 配置管理（模組 06）
   - 啟動 06-config-server
   
7. **Day 19-21**: API 閘道（模組 07）
   - 啟動 07-api-gateway
   - 通過閘道路由請求
   
8. **Day 22-24**: 容錯模式（模組 08）
   - 啟動 08-circuit-breaker
   
9. **Day 25-27**: 分散式追蹤（模組 09）
   - 啟動 09-distributed-tracing
   - 在 Zipkin 查看調用鏈
   
10. **Day 28-30**: 監控與告警（模組 10）
    - 啟動 10-monitoring
    - 在 Prometheus 查看指標

## 🎯 常用命令速查

```bash
# 編譯所有模組
mvn clean install

# 編譯特定模組
mvn clean install -pl 05-discovery-server

# 跳過測試編譯
mvn clean install -DskipTests

# 運行特定模組的測試
mvn test -pl 08-circuit-breaker

# 查看依賴樹
mvn dependency:tree

# 清理所有編譯文件
mvn clean
```

## 📝 注意事項

1. **端口衝突**：確保所有分配的端口未被佔用
2. **JVM 記憶體**：如果同時運行多個服務，可能需要增加 JVM 記憶體
   ```bash
   export JAVA_OPTS="-Xmx512m"
   ```
3. **H2 Database**：每個服務使用獨立的 H2 記憶體數據庫
4. **TDD 實踐**：所有模組都包含測試，建議在開發過程中頻繁執行

## 🔗 重要鏈接

| 服務 | 端口 | 地址 | 功能 |
|------|------|------|------|
| Eureka | 8761 | http://localhost:8761 | 服務註冊與發現 |
| Config Server | 8888 | http://localhost:8888 | 配置中心 |
| API Gateway | 8080 | http://localhost:8080 | 統一入口 |
| Customers | 8081 | http://localhost:8081 | 客戶服務 |
| Pets | 8082 | http://localhost:8082 | 寵物服務 |
| Circuit Breaker | 8084 | http://localhost:8084 | 容錯演示 |
| Zipkin | 9411 | http://localhost:9411 | 分散式追蹤 |
| Monitoring | 8080 | http://localhost:8080/actuator | 監控指標 |

---

**祝您學習愉快！** 🎓
