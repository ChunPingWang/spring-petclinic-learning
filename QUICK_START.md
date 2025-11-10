# 🚀 Spring Pet Learning - 開始使用

## 5 分鐘快速開始

### 1️⃣ 環境要求

```bash
✅ Java 17+
✅ Maven 3.8+
✅ Git
```

驗證：
```bash
java -version        # Java 17+
mvn -version        # Maven 3.8+
```

### 2️⃣ 克隆並編譯

```bash
# 編譯所有 10 個模組
cd /Users/rexwang/workspace/spring-pet-learning
mvn clean install -DskipTests
```

✅ **預期結果**: `BUILD SUCCESS`

### 3️⃣ 啟動第一個服務

```bash
# 打開新終端標籤頁
cd 01-basic-spring-boot
mvn spring-boot:run
```

✅ **訪問**: http://localhost:8080/actuator/health

應該看到：
```json
{"status":"UP"}
```

### 4️⃣ 嘗試一個 API

```bash
# 新終端標籤頁
curl -X GET http://localhost:8080/hello
```

應該看到：
```
Hello, Spring Boot!
```

---

## 📚 30 天學習路線圖

### 第 1 週 - 基礎（Day 1-7）

| 天 | 模組 | 主題 | 所需時間 |
|----|------|------|---------|
| 1-3 | 01 | Spring Boot 基礎 | 3-4 小時 |
| 4-6 | 02 | 數據持久化（JPA） | 3-4 小時 |
| 7 | 03 | REST API 設計 | 3-4 小時 |

### 第 2 週 - 微服務（Day 8-14）

| 天 | 模組 | 主題 | 所需時間 |
|----|------|------|---------|
| 8-10 | 04 | 微服務分解 | 4-5 小時 |
| 11-13 | 05 | 服務發現（Eureka） | 3-4 小時 |
| 14 | - | 複習 + 練習 | 3-4 小時 |

### 第 3 週 - 進階（Day 15-21）

| 天 | 模組 | 主題 | 所需時間 |
|----|------|------|---------|
| 15-17 | 06 | 配置管理 | 3-4 小時 |
| 18-20 | 07 | API 閘道 | 3-4 小時 |
| 21 | - | 集成練習 | 4-5 小時 |

### 第 4 週 - 生產化（Day 22-30）

| 天 | 模組 | 主題 | 所需時間 |
|----|------|------|---------|
| 22-24 | 08 | 容錯設計 | 3-4 小時 |
| 25-27 | 09 | 分散式追蹤 | 3-4 小時 |
| 28-30 | 10 | 監控和告警 | 4-5 小時 |

---

## 📖 逐模組啟動

### 模組 01 - Spring Boot 基礎

```bash
cd 01-basic-spring-boot
mvn spring-boot:run
# 訪問 http://localhost:8080/hello
```

### 模組 02 - Spring Data JPA

```bash
cd 02-spring-data-jpa
mvn spring-boot:run
# 訪問 http://localhost:8080/h2-console
# 輸入 JDBC URL: jdbc:h2:mem:testdb
```

### 模組 03 - REST API

```bash
cd 03-rest-api
mvn spring-boot:run
# 測試 CRUD API
curl http://localhost:8080/api/owners
```

### 模組 04 - 微服務（兩個獨立服務）

```bash
# 終端 1
cd 04-customers-service
mvn spring-boot:run  # 端口 8081

# 終端 2
cd 04-pets-service
mvn spring-boot:run  # 端口 8082
```

### 模組 05-10 - 基礎設施服務

```bash
# 終端 1 - Eureka 服務發現
cd 05-discovery-server
mvn spring-boot:run  # 訪問 http://localhost:8761

# 終端 2 - 配置中心
cd 06-config-server
mvn spring-boot:run  # 端口 8888

# 終端 3 - API 閘道
cd 07-api-gateway
mvn spring-boot:run  # 端口 8080
```

---

## 🧪 測試

### 運行所有測試

```bash
cd /Users/rexwang/workspace/spring-pet-learning
mvn test
```

### 運行特定模組測試

```bash
mvn test -pl 03-rest-api
```

### 查看測試覆蓋率

```bash
mvn test jacoco:report
open target/site/jacoco/index.html
```

---

## 🛠️ 常用命令

### 編譯

```bash
# 編譯所有模組（跳過測試）
mvn clean install -DskipTests

# 編譯特定模組
mvn clean install -pl 05-discovery-server

# 查看依賴樹
mvn dependency:tree
```

### 執行

```bash
# 運行應用
mvn spring-boot:run

# 指定配置文件
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### 清理

```bash
# 清理編譯文件
mvn clean

# 清理 IDE 緩存
rm -rf .idea target *.iml
```

---

## 📋 項目結構

```
spring-pet-learning/
├── 01-basic-spring-boot/          # Day 1-3: Spring Boot 基礎
├── 02-spring-data-jpa/            # Day 4-6: 數據持久化
├── 03-rest-api/                   # Day 7-9: REST API
├── 04-customers-service/          # Day 10-12: 微服務 (Customers)
├── 04-pets-service/               # Day 10-12: 微服務 (Pets)
├── 05-discovery-server/           # Day 13-15: 服務發現
├── 06-config-server/              # Day 16-18: 配置中心
├── 07-api-gateway/                # Day 19-21: API 閘道
├── 08-circuit-breaker/            # Day 22-24: 容錯設計
├── 09-distributed-tracing/        # Day 25-27: 分散式追蹤
├── 10-monitoring/                 # Day 28-30: 監控告警
├── pom.xml                        # 父 POM（依賴管理）
├── README.md                      # 項目說明
├── spec.md                        # 30 天學習計劃
├── STARTUP_GUIDE.md               # 詳細啟動指南
├── CHECKLIST.md                   # 進度檢查清單
└── DELIVERY_REPORT.md             # 交付完成報告
```

---

## 💡 學習建議

### TDD 開發流程

每個功能開發都應遵循：

1. **🔴 Red** - 編寫失敗的測試
2. **🟢 Green** - 寫最小化實現讓測試通過
3. **🔵 Refactor** - 優化代碼保持測試通過

### 練習方式

1. 閱讀模組 README.md
2. 查看示例代碼
3. 編寫測試（根據 README 的 TDD 示例）
4. 實現功能
5. 運行測試驗證

### 深度學習

- 修改代碼並觀察行為
- 添加新的測試用例
- 集成相鄰模組
- 部署到容器環境

---

## 🐛 故障排除

### 編譯失敗

```bash
# 清理並強制更新
mvn clean install -U -DskipTests
```

### 端口衝突

```bash
# 查看端口佔用 (macOS/Linux)
lsof -i :8080

# 殺死進程
kill -9 <PID>
```

### 服務無法註冊

確保：
1. Eureka Server (05-discovery-server) 已啟動
2. 應用配置包含 Eureka 客戶端設置
3. 檢查日誌看是否有註冊錯誤

### 依賴解析失敗

```bash
# 清理本地 Maven 緩存
rm -rf ~/.m2/repository

# 重新下載
mvn clean install -U
```

---

## 📚 推薦資源

- [Spring Boot 官方文檔](https://spring.io/projects/spring-boot)
- [Spring Cloud 官方文檔](https://spring.io/projects/spring-cloud)
- [Spring Data JPA 指南](https://spring.io/projects/spring-data-jpa)
- [Microservices Patterns](https://microservices.io/patterns/index.html)

---

## 🎯 完成標準

每個模組完成後應該能夠：

✅ 理解核心概念  
✅ 運行示例代碼  
✅ 編寫新的測試  
✅ 實現新的功能  
✅ 集成相鄰服務  

---

## 📞 需要幫助？

- 📖 查看各模組的 README.md
- 🔍 參考 spec.md 的詳細說明
- 🚀 檢查 STARTUP_GUIDE.md 的故障排除
- ✅ 查看 CHECKLIST.md 確認進度

---

## 🎓 下一步？

完成 30 天課程後，建議：

1. **加強實戰** - 創建自己的微服務應用
2. **雲部署** - 部署到 AWS/Azure/GCP
3. **容器化** - 使用 Docker 和 Kubernetes
4. **進階特性** - Spring Security、事件驅動架構等
5. **性能優化** - 緩存、數據庫優化、JVM 調優

---

**開始學習吧！** 🎉

在 Day 1 執行：`cd 01-basic-spring-boot && mvn spring-boot:run`

