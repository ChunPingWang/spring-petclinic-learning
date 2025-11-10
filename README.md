# Spring Pet Learning - 春天微服務學習實戰

![License](https://img.shields.io/badge/license-Apache%202.0-blue)
![Java](https://img.shields.io/badge/java-17-orange)
![Spring Boot](https://img.shields.io/badge/spring%20boot-3.2.x-green)
![Spring Cloud](https://img.shields.io/badge/spring%20cloud-2023.0.x-green)

> 一份為高中生程度量身打造的 **30 天 Spring Boot / Spring Cloud 微服務學習計劃**。
> 
> 採用 **Monorepo 架構**，包含 10+ 獨立學習模組，每個模組都是完全可執行的應用程式。
> 
> 強制使用 **TDD (Test-Driven Development)** 開發方式，培養專業開發習慣。

## 📚 課程結構

### 第一階段：Spring Boot 基礎 (模組 01-03)

| 模組 | 主題 | 天數 | 重點 |
|------|------|------|------|
| **01** | [Spring Boot 基礎應用](./01-basic-spring-boot/README.md) | 3天 | REST 端點、配置、Actuator |
| **02** | [資料持久化與 JPA](./02-spring-data-jpa/README.md) | 3天 | Entity、Repository、H2 資料庫 |
| **03** | [RESTful API 設計](./03-rest-api/README.md) | 3天 | CRUD API、異常處理、統一回應 |

### 第二階段：微服務架構 (模組 04-07)

| 模組 | 主題 | 天數 | 重點 |
|------|------|------|------|
| **04** | [微服務基礎](./04-customers-service/README.md) | 3天 | 獨立服務、多埠部署、服務隔離 |
| **05** | [Eureka 服務發現](./05-discovery-server/README.md) | 3天 | 服務註冊、自動發現、負載平衡 |
| **06** | [Config Server 配置中心](./06-config-server/README.md) | 3天 | 集中配置、Profile 切換、動態更新 |
| **07** | [API Gateway 閘道](./07-api-gateway/README.md) | 3天 | 統一入口、路由、認證 |

### 第三階段：高級功能 (模組 08-10)

| 模組 | 主題 | 天數 | 重點 |
|------|------|------|------|
| **08** | [Resilience4j 斷路器](./08-circuit-breaker/README.md) | 3天 | 容錯、降級、重試 |
| **09** | [Zipkin 分散式追蹤](./09-distributed-tracing/README.md) | 3天 | 請求追蹤、性能分析 |
| **10** | [Prometheus & Grafana 監控](./10-monitoring/README.md) | 3天 | 指標收集、實時監控、告警 |

## 🚀 快速開始

### 前置要求

- **Java 17+** (下載：[Adoptium JDK 17](https://adoptium.net/))
- **Maven 3.8+** (下載：[Apache Maven](https://maven.apache.org/))
- **IDE** (推薦：[IntelliJ IDEA CE](https://www.jetbrains.com/idea/) 或 [VS Code](https://code.visualstudio.com/))
- **Postman** 或 **curl** (用於 API 測試)

### 安裝驗證

```bash
# 驗證 Java 版本
java -version

# 驗證 Maven 版本
mvn -version
```

### 第一次啟動

```bash
# 1. 編譯整個 Monorepo
mvn clean install

# 2. 啟動模組 01 (Spring Boot 基礎)
cd 01-basic-spring-boot
mvn spring-boot:run

# 3. 訪問應用
curl http://localhost:8080/hello
```

### 同時啟動多個服務

```bash
# 終端機 1 - Customers Service (Port 8081)
cd 04-customers-service
mvn spring-boot:run

# 終端機 2 - Pets Service (Port 8082)
cd 04-pets-service
mvn spring-boot:run

# 終端機 3 - Eureka Server (Port 8761)
cd 05-discovery-server
mvn spring-boot:run
```

## 📖 開發規範

### TDD (Test-Driven Development) 流程

```
🔴 Red      寫測試（測試失敗）
    ↓
🟢 Green    寫實作（測試通過）
    ↓
🔵 Refactor 重構代碼（保持測試通過）
```

### 程式碼組織

```
src/main/java/com/petlearning/<module>/
├── controller/          # REST API 控制器
├── service/             # 業務邏輯層
├── repository/          # 資料存取層
├── entity/              # JPA 實體
├── dto/                 # 資料轉移物件
├── exception/           # 異常處理
└── <Module>Application.java  # 啟動類

src/test/java/com/petlearning/<module>/
├── controller/          # Controller 單元測試
├── service/             # Service 層測試
├── repository/          # Repository 層測試
└── ...
```

### 命名規範

```java
// Controller
@RestController
public class OwnerController { }

// Service
@Service
public class OwnerService { }

// Repository
@Repository
public interface OwnerRepository extends JpaRepository { }

// Entity
@Entity
public class Owner { }

// DTO
public class OwnerDTO { }

// 測試
class OwnerRepositoryTest { }
class OwnerServiceTest { }
class OwnerControllerTest { }
```

### 測試命名規範

```java
// Given-When-Then 結構
@Test
void should_ReturnOwner_When_ValidIdProvided() {
    // Given
    Long ownerId = 1L;
    
    // When
    Owner owner = service.findById(ownerId);
    
    // Then
    assertThat(owner).isNotNull();
}
```

## 📋 30 天學習計劃詳細

### 第 1-3 天：Spring Boot 基礎
- **Day 1**: Spring Boot 簡介、環境設置、Hello World
- **Day 2**: REST 端點、HTTP 方法、@RestController
- **Day 3**: 配置檔案（application.yml）、Actuator

### 第 4-6 天：資料持久化
- **Day 4**: H2 資料庫、@Entity 實體、主鍵映射
- **Day 5**: Spring Data JPA、Repository、CRUD 操作
- **Day 6**: 實體關聯、@OneToMany、資料初始化

### 第 7-9 天：REST API 設計
- **Day 7**: REST 原則、GET 查詢、@PathVariable
- **Day 8**: POST 新增、PUT 更新、DELETE 刪除
- **Day 9**: 異常處理、統一回應格式、Bean Validation

### 第 10-12 天：微服務基礎
- **Day 10**: 微服務架構、獨立部署、Customers Service
- **Day 11**: 獨立資料庫、Pets Service、服務隔離
- **Day 12**: 多實例部署、健康檢查

### 第 13-15 天：服務發現
- **Day 13**: Eureka 概念、服務註冊
- **Day 14**: 服務發現、自動負載平衡
- **Day 15**: 健康檢查、服務下線

### 第 16-18 天：配置中心
- **Day 16**: Config Server 架構、Git 儲存庫
- **Day 17**: 動態配置更新、Profile 切換
- **Day 18**: 配置加密、生產環境配置

### 第 19-21 天：API Gateway
- **Day 19**: Gateway 功能、路由配置
- **Day 20**: 負載平衡、請求過濾
- **Day 21**: 速率限制、認證

### 第 22-24 天：斷路器
- **Day 22**: Resilience4j 基礎、@CircuitBreaker
- **Day 23**: 降級策略、重試機制
- **Day 24**: 實時監控、熔斷統計

### 第 25-27 天：分散式追蹤
- **Day 25**: Sleuth 原理、追蹤 ID
- **Day 26**: Zipkin 可視化、跨服務追蹤
- **Day 27**: 性能分析、優化建議

### 第 28-29 天：監控與告警
- **Day 28**: Prometheus 指標、Grafana 儀表板
- **Day 29**: 自訂指標、告警規則

### 第 30 天：綜合實踐
- 整合所有模組
- 完整的微服務系統運行
- 性能測試、壓力測試
- 文件總結

## 🏗️ 專案結構

```
spring-pet-learning/
├── pom.xml                          # 父 POM（依賴管理）
├── .github/
│   └── copilot-instructions.md      # 開發規範
├── README.md                        # 本文件
├── spec.md                          # 詳細學習計劃
│
├── 01-basic-spring-boot/            # 模組 1：Spring Boot 基礎
│   ├── README.md
│   ├── pom.xml
│   └── src/
│
├── 02-spring-data-jpa/              # 模組 2：資料持久化
│   ├── README.md
│   ├── pom.xml
│   └── src/
│
├── 03-rest-api/                     # 模組 3：RESTful API
│   ├── README.md
│   ├── pom.xml
│   └── src/
│
├── 04-customers-service/            # 模組 4a：Customers 微服務
│   ├── README.md
│   ├── pom.xml
│   └── src/
│
├── 04-pets-service/                 # 模組 4b：Pets 微服務
│   ├── README.md
│   ├── pom.xml
│   └── src/
│
├── 05-discovery-server/             # 模組 5：Eureka 服務發現
├── 06-config-server/                # 模組 6：配置中心
├── 07-api-gateway/                  # 模組 7：API 閘道
├── 08-circuit-breaker/              # 模組 8：斷路器
├── 09-distributed-tracing/          # 模組 9：分散式追蹤
└── 10-monitoring/                   # 模組 10：監控
```

## 📚 技術棧

| 技術 | 版本 | 用途 |
|------|------|------|
| Java | 17 | 開發語言 |
| Spring Boot | 3.2.x | Web 框架 |
| Spring Cloud | 2023.0.x | 微服務框架 |
| Spring Data JPA | 3.2.x | ORM |
| Hibernate | 6.x | JPA 實作 |
| H2 Database | 2.x | 開發資料庫 |
| Eureka | 2023.0.x | 服務發現 |
| Spring Cloud Config | 4.x | 配置中心 |
| Spring Cloud Gateway | 4.x | API 閘道 |
| Resilience4j | 2.x | 斷路器 |
| Spring Cloud Sleuth | 4.x | 分散式追蹤 |
| Zipkin | 2.x | 追蹤可視化 |
| Prometheus | 最新 | 監控指標 |
| Grafana | 最新 | 可視化儀表板 |
| JUnit 5 | 5.x | 單元測試 |
| Mockito | 5.x | Mock 框架 |
| AssertJ | 3.x | 流暢斷言 |

## 🎓 學習成果

完成本課程後，你將能夠：

### 技能目標

✅ **Spring Boot**
- 構建可執行的 Spring Boot 應用
- 理解自動配置機制
- 使用 Actuator 監控應用

✅ **資料持久化**
- 使用 JPA/Hibernate 操作資料庫
- 設計實體與關聯映射
- 編寫高效的查詢

✅ **Web API 設計**
- 遵循 REST 原則設計 API
- 實現完整的 CRUD 操作
- 處理異常和驗證

✅ **微服務架構**
- 將應用拆分為獨立服務
- 理解服務間通訊
- 實現服務發現與負載平衡

✅ **高可用性**
- 使用斷路器防止級聯故障
- 實現分散式追蹤
- 監控系統性能

✅ **專業開發實踐**
- 使用 TDD 開發
- 編寫充分的單元測試
- 遵循編碼規範

## 🤝 如何使用本課程

### 對於初學者
1. 按順序學習 01-03 模組
2. 每天完成該天的任務
3. 確保所有測試通過
4. 嘗試實現挑戰任務

### 對於中級開發者
1. 快速過一遍 01-03 模組
2. 重點學習 04-07 模組
3. 實現模組間的整合
4. 進行性能優化

### 對於進階開發者
1. 選擇感興趣的模組深入學習
2. 自訂擴展模組內容
3. 應用到實際項目
4. 分享學習成果

## 📝 評估標準

### 單元層級
- ✅ 所有單元測試通過
- ✅ 測試覆蓋率 > 80%
- ✅ 代碼無編譯錯誤

### 模組層級
- ✅ 所有 API 端點正常運作
- ✅ 數據庫查詢無誤
- ✅ 異常處理完善
- ✅ 文件清楚完整

### 課程層級
- ✅ 30 天學習計劃完成
- ✅ 能獨立構建微服務系統
- ✅ 理解架構原理和最佳實踐
- ✅ 代碼質量符合專業標準

## 🐛 常見問題

**Q: 需要多長時間完成？**
- 完整版本：30 天（每天 2-3 小時）
- 快速版本：15 天（重點模組）
- 進階應用：取決於個人項目

**Q: 沒有 Spring Boot 基礎可以學嗎？**
- 可以，本課程從零開始
- 建議先理解 Java 基本語法

**Q: 可以跳過某些模組嗎？**
- 可以，但建議按順序學習
- 後續模組依賴前序知識

**Q: 如何獲得幫助？**
- 查看各模組的 README
- 閱讀開發規範文件
- 查看示例代碼
- 參考延伸閱讀資源

## 📚 延伸資源

### 官方文件
- [Spring Boot 官方文件](https://spring.io/projects/spring-boot)
- [Spring Cloud 官方文件](https://spring.io/projects/spring-cloud)
- [Maven 官方文件](https://maven.apache.org/)

### 教學資源
- [Baeldung Spring Tutorials](https://www.baeldung.com/)
- [Spring 官方教學](https://spring.io/guides)
- [微服務架構設計模式](https://microservices.io/)

### 社群資源
- [Spring Community Forum](https://community.spring.io/)
- [Stack Overflow - Spring 標籤](https://stackoverflow.com/questions/tagged/spring)
- [Spring GitHub Repository](https://github.com/spring-projects)

## 📄 授權協議

本專案參考 [Spring PetClinic Microservices](https://github.com/spring-petclinic/spring-petclinic-microservices) 範例，採用 **Apache 2.0** 授權。

### 致謝
感謝 Spring 官方提供的 PetClinic 範例應用。本課程是在其基礎上針對初學者進行的簡化和擴展。

## 🎯 下一步

- [ ] 閱讀 [開發規範](./github/copilot-instructions.md)
- [ ] 開始 [模組 01](./01-basic-spring-boot/README.md)
- [ ] 完成 30 天學習計劃
- [ ] 構建自己的微服務應用

---

**開始日期**: 2025-01-09  
**最後更新**: 2025-01-10  
**維護者**: Spring Pet Learning Team

祝你學習愉快！🚀
