# Spring Boot / Spring Cloud 微服務開發規範

## 專案概述
本專案採用 Monorepo 架構，基於 Spring PetClinic 微服務範例，將複雜的微服務系統拆解為多個獨立可執行的學習模組。每個子模組都是一個獨立的 Spring Boot 應用程式，專注於教授特定的微服務概念。

## 專案結構規範

### Monorepo 架構
```
spring-pet-learning/
├── README.md                              # 專案總覽與使用指南
├── pom.xml                                # 父級 POM（依賴管理）
├── .github/
│   └── copilot-instructions.md           # 本開發規範文件
├── spec.md                                # 學習計劃與工作清單
├── 01-basic-spring-boot/                 # 模組 1：Spring Boot 基礎
│   ├── README.md                         # 模組說明文件
│   ├── pom.xml
│   └── src/
├── 02-spring-data-jpa/                   # 模組 2：資料持久化
│   ├── README.md
│   ├── pom.xml
│   └── src/
├── 03-rest-api/                          # 模組 3：RESTful API
│   ├── README.md
│   ├── pom.xml
│   └── src/
├── 04-service-discovery/                 # 模組 4：服務發現（Eureka）
│   ├── README.md
│   ├── pom.xml
│   └── src/
├── 05-config-server/                     # 模組 5：集中配置管理
│   ├── README.md
│   ├── pom.xml
│   └── src/
├── 06-api-gateway/                       # 模組 6：API 閘道
│   ├── README.md
│   ├── pom.xml
│   └── src/
├── 07-circuit-breaker/                   # 模組 7：斷路器模式
│   ├── README.md
│   ├── pom.xml
│   └── src/
├── 08-distributed-tracing/               # 模組 8：分散式追蹤
│   ├── README.md
│   ├── pom.xml
│   └── src/
└── 09-monitoring/                        # 模組 9：監控與指標
    ├── README.md
    ├── pom.xml
    └── src/
```

## 編碼規範

### Java 版本
- **使用 Java 17** 作為開發與執行環境
- 充分利用 Java 17 的新特性（Records, Pattern Matching, Sealed Classes 等）

### Spring Boot 版本
- **Spring Boot 3.x** 最新穩定版
- **Spring Cloud 2023.x** (對應 Spring Boot 3.x)

### 命名規範

#### 套件命名
```
com.petlearning.<module-name>
例如：
- com.petlearning.basic
- com.petlearning.discovery
- com.petlearning.gateway
```

#### 類別命名
- **Controller**: `*Controller` (例：`OwnerController`)
- **Service**: `*Service` (例：`OwnerService`)
- **Repository**: `*Repository` (例：`OwnerRepository`)
- **Entity**: 實體名稱 (例：`Owner`, `Pet`, `Visit`)
- **DTO**: `*DTO` 或 `*Request`/`*Response` (例：`OwnerDTO`, `CreateOwnerRequest`)
- **Configuration**: `*Config` (例：`DatabaseConfig`, `SecurityConfig`)

#### 方法命名
- 使用動詞開頭，遵循駝峰命名法
- RESTful API 映射：
  - `GET` → `getXxx()` 或 `findXxx()`
  - `POST` → `createXxx()`
  - `PUT` → `updateXxx()`
  - `DELETE` → `deleteXxx()`

### 程式碼風格

#### 註解規範
```java
/**
 * 每個公開類別和方法都應有完整的 Javadoc 註解
 * 特別說明：概念、用途、參數、返回值
 */
@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    
    /**
     * 取得所有飼主資料
     * 
     * @return 飼主清單
     */
    @GetMapping
    public List<OwnerDTO> getAllOwners() {
        // 實作內容
    }
}
```

#### 配置檔案
- 優先使用 `application.yml` 而非 `application.properties`
- 每個模組的配置應清晰分層：
```yaml
spring:
  application:
    name: owner-service
  datasource:
    url: jdbc:h2:mem:testdb
    
server:
  port: 8081
  
# 自定義配置
petlearning:
  module:
    name: "飼主服務"
    description: "管理寵物飼主的基本資訊"
```

### 依賴管理

#### 父 POM 管理
- 所有共用依賴版本在根目錄 `pom.xml` 的 `<dependencyManagement>` 統一管理
- 子模組僅需聲明 `groupId` 和 `artifactId`，不需指定版本

```xml
<!-- 父 POM -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

```xml
<!-- 子模組 POM -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 資料庫規範

#### 開發環境
- 預設使用 **H2 記憶體資料庫**，便於快速啟動和測試
- 提供 H2 Console 訪問路徑：`/h2-console`

#### 生產環境（進階學習）
- 支援切換至 **MySQL 8.4+**
- 通過 Spring Profile 切換：`--spring.profiles.active=mysql`

#### Schema 初始化
- SQL 腳本放置於 `src/main/resources/db/`
  - `schema.sql`: 資料表結構
  - `data.sql`: 初始資料

### REST API 規範

#### 端點設計
```
GET    /api/owners          # 查詢所有飼主
GET    /api/owners/{id}     # 查詢單一飼主
POST   /api/owners          # 新增飼主
PUT    /api/owners/{id}     # 更新飼主
DELETE /api/owners/{id}     # 刪除飼主
```

#### HTTP 狀態碼
- `200 OK`: 成功查詢
- `201 Created`: 成功新增
- `204 No Content`: 成功刪除
- `400 Bad Request`: 請求參數錯誤
- `404 Not Found`: 資源不存在
- `500 Internal Server Error`: 伺服器錯誤

#### 回應格式
```json
{
  "success": true,
  "data": {
    "id": 1,
    "firstName": "George",
    "lastName": "Franklin"
  },
  "message": "操作成功"
}
```

### 測試規範

#### TDD 開發流程（必須遵循）

**所有功能開發必須採用 TDD（測試驅動開發）方式**:

```
TDD 三步驟循環：

1. 🔴 Red（寫測試）
   - 先寫測試案例，描述預期行為
   - 執行測試，確認測試失敗（因為功能還沒實作）

2. 🟢 Green（寫實作）
   - 寫最簡單的程式碼讓測試通過
   - 執行測試，確認測試通過

3. 🔵 Refactor（重構）
   - 優化程式碼，但保持測試通過
   - 確保程式碼品質與可讀性
```

**開發順序範例**:
```
假設要開發「新增飼主」功能：

1. 先寫測試（Red）：
   - should_CreateOwner_When_ValidDataProvided()
   - should_ThrowException_When_NameIsEmpty()
   
2. 執行測試 → 失敗（因為還沒實作）

3. 寫實作程式碼（Green）：
   - 實作 createOwner() 方法
   
4. 執行測試 → 成功

5. 重構（Refactor）：
   - 提取重複程式碼
   - 改善命名
   - 加入註解

6. 執行測試 → 仍然成功
```

#### 測試結構
```
src/test/java/
└── com/petlearning/<module>/
    ├── controller/       # Controller 測試（@WebMvcTest）
    ├── service/          # Service 測試（@MockBean）
    └── repository/       # Repository 測試（@DataJpaTest）
```

#### 測試命名規範
- 測試類別：`*Test` (例：`OwnerControllerTest`)
- 測試方法：`should_ExpectedBehavior_When_Condition()`
  - 例：`should_ReturnOwner_When_ValidIdProvided()`
  - 例：`should_ThrowException_When_IdNotFound()`
  - 例：`should_SaveOwner_When_DataIsValid()`

#### 測試類型與範例

**1. Repository 測試**（使用 @DataJpaTest）:
```java
@DataJpaTest
class OwnerRepositoryTest {
    
    @Autowired
    private OwnerRepository ownerRepository;
    
    @Test
    void should_SaveOwner_When_DataIsValid() {
        // Given (準備測試資料)
        Owner owner = new Owner("小明", "王");
        owner.setCity("台北市");
        
        // When (執行要測試的方法)
        Owner saved = ownerRepository.save(owner);
        
        // Then (驗證結果)
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFirstName()).isEqualTo("小明");
    }
    
    @Test
    void should_ReturnOwners_When_SearchByCity() {
        // Given
        ownerRepository.save(new Owner("小明", "王", "台北市"));
        ownerRepository.save(new Owner("小華", "李", "台北市"));
        ownerRepository.save(new Owner("小美", "陳", "台中市"));
        
        // When
        List<Owner> owners = ownerRepository.findByCity("台北市");
        
        // Then
        assertThat(owners).hasSize(2);
        assertThat(owners).extracting("city")
            .containsOnly("台北市");
    }
}
```

**2. Service 測試**（使用 @MockBean）:
```java
@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {
    
    @Mock
    private OwnerRepository ownerRepository;
    
    @InjectMocks
    private OwnerService ownerService;
    
    @Test
    void should_ReturnOwner_When_IdExists() {
        // Given
        Long ownerId = 1L;
        Owner owner = new Owner("小明", "王");
        when(ownerRepository.findById(ownerId))
            .thenReturn(Optional.of(owner));
        
        // When
        Owner result = ownerService.getById(ownerId);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("小明");
        verify(ownerRepository).findById(ownerId);
    }
    
    @Test
    void should_ThrowException_When_IdNotFound() {
        // Given
        Long ownerId = 999L;
        when(ownerRepository.findById(ownerId))
            .thenReturn(Optional.empty());
        
        // When & Then
        assertThatThrownBy(() -> ownerService.getById(ownerId))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessage("找不到 ID 為 999 的飼主");
    }
}
```

**3. Controller 測試**（使用 @WebMvcTest）:
```java
@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OwnerService ownerService;
    
    @Test
    void should_ReturnOwner_When_GetById() throws Exception {
        // Given
        Long ownerId = 1L;
        Owner owner = new Owner("小明", "王");
        owner.setId(ownerId);
        when(ownerService.getById(ownerId)).thenReturn(owner);
        
        // When & Then
        mockMvc.perform(get("/api/owners/{id}", ownerId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("小明"))
            .andExpect(jsonPath("$.lastName").value("王"));
    }
    
    @Test
    void should_Return404_When_OwnerNotFound() throws Exception {
        // Given
        Long ownerId = 999L;
        when(ownerService.getById(ownerId))
            .thenThrow(new ResourceNotFoundException("找不到飼主"));
        
        // When & Then
        mockMvc.perform(get("/api/owners/{id}", ownerId))
            .andExpect(status().isNotFound());
    }
    
    @Test
    void should_CreateOwner_When_ValidData() throws Exception {
        // Given
        Owner owner = new Owner("小明", "王");
        when(ownerService.create(any(Owner.class))).thenReturn(owner);
        
        // When & Then
        mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"小明\",\"lastName\":\"王\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName").value("小明"));
    }
}
```

#### TDD 實踐原則

1. **測試先行**: 永遠先寫測試，再寫實作
2. **小步前進**: 每次只測試一個小功能
3. **快速反饋**: 頻繁執行測試，立即發現問題
4. **重構無懼**: 有測試保護，可以放心重構
5. **測試即文件**: 測試案例就是最好的使用說明

#### 測試覆蓋率目標
- Repository 層：至少 90%
- Service 層：至少 85%
- Controller 層：至少 80%

#### 測試工具與依賴
```xml
<!-- JUnit 5 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- AssertJ (流暢的斷言庫) -->
<!-- 已包含在 spring-boot-starter-test 中 -->

<!-- Mockito (Mock 框架) -->
<!-- 已包含在 spring-boot-starter-test 中 -->
```

#### 常用測試註解
- `@SpringBootTest`: 完整的 Spring 容器測試
- `@WebMvcTest`: Controller 層測試
- `@DataJpaTest`: Repository 層測試
- `@MockBean`: 注入 Mock 物件
- `@Mock`: Mockito 的 Mock 物件
- `@InjectMocks`: 自動注入 Mock 到測試類別

### 模組 README 規範

每個子模組的 `README.md` 應包含以下章節：

```markdown
# 模組名稱

## 學習目標
- 列出本模組要教授的核心概念（3-5 個）

## 核心概念說明
### 概念 1
- 什麼是這個概念？
- 為什麼需要它？
- 它解決了什麼問題？

### 概念 2
...

## 快速開始

### 前置需求
- Java 17+
- Maven 3.8+

### 啟動步驟
1. 編譯專案
   ```bash
   mvn clean install
   ```

2. 啟動應用
   ```bash
   mvn spring-boot:run
   ```

3. 訪問應用
   - 主要端點：http://localhost:8080
   - API 文件：http://localhost:8080/swagger-ui.html

## 程式碼導覽
- `src/main/java/.../controller/`: REST API 控制器
- `src/main/java/.../service/`: 業務邏輯層
- `src/main/java/.../repository/`: 資料訪問層
- `src/main/resources/application.yml`: 配置檔案

## 重點程式碼解析
### 檔案 1: XxxController.java
解釋關鍵程式碼片段，說明設計思路

### 檔案 2: XxxService.java
...

## 實作練習
1. 練習 1：修改 XXX 功能
2. 練習 2：新增 YYY 功能
3. 練習 3：整合 ZZZ 元件

## 常見問題
Q: 問題 1？
A: 解答...

## 延伸閱讀
- [官方文件連結]
- [相關教學資源]
```

## 微服務架構規範

### 服務拆分原則
1. **單一職責**: 每個服務專注於單一業務領域
2. **獨立部署**: 服務可獨立編譯、測試、部署
3. **資料隔離**: 每個服務擁有自己的資料庫（或 Schema）
4. **通訊機制**: 服務間通過 REST API 或訊息佇列通訊

### 核心元件

#### 1. 服務發現 (Eureka Server)
- 所有微服務向 Eureka 註冊
- 配置端點：`http://localhost:8761`

#### 2. 配置中心 (Config Server)
- 集中管理所有服務配置
- 支援 Git 儲存庫作為配置來源

#### 3. API 閘道 (Spring Cloud Gateway)
- 統一入口點
- 路由、負載平衡、認證

#### 4. 斷路器 (Resilience4j)
- 防止級聯失敗
- 提供降級機制

### Port 分配規範
```
8761  - Eureka Server (服務發現)
8888  - Config Server (配置中心)
8080  - API Gateway (API 閘道)
8081  - Customers Service (客戶服務)
8082  - Vets Service (獸醫服務)
8083  - Visits Service (訪問服務)
9090  - Admin Server (管理介面)
9091  - Prometheus (監控)
3000  - Grafana (視覺化)
9411  - Zipkin (追蹤)
```

## 文件規範

### README.md 層級
1. **根目錄 README.md**: 專案總覽、整體架構、快速開始
2. **模組 README.md**: 模組專屬的詳細說明

### 文件語言
- **中文**為主要教學語言
- 專有名詞保留英文，首次出現時附上中文說明
  - 例：Service Discovery（服務發現）

### 圖表使用
- 優先使用 Mermaid 語法繪製架構圖
- 複雜圖表可使用外部工具後嵌入圖片

## Git 工作流程

### 分支策略
```
main              # 穩定版本
├── develop       # 開發主分支
│   ├── feature/module-01-basic
│   ├── feature/module-02-jpa
│   └── feature/module-03-rest-api
```

### Commit 訊息規範
```
<type>(<scope>): <subject>

範例：
feat(module-01): 新增 Spring Boot 基礎模組
fix(module-03): 修正 REST API 回應格式
docs(README): 更新專案說明文件
```

**Type 類型**:
- `feat`: 新功能
- `fix`: 修復問題
- `docs`: 文件更新
- `refactor`: 重構程式碼
- `test`: 測試相關
- `chore`: 建置或輔助工具變動

## 教學原則

### 漸進式學習
1. 從簡單的 Spring Boot 單體應用開始
2. 逐步引入微服務概念
3. 每個模組都是獨立可執行的完整應用

### 實作導向
- 每個概念都配有可執行的範例程式碼
- 提供實作練習題
- 鼓勵修改程式碼以加深理解

### 降低學習門檻
- 避免過度複雜的設計模式
- 提供充分的中文註解
- 使用生活化的範例說明抽象概念

## 工具與環境

### 開發工具建議
- **IDE**: IntelliJ IDEA Community Edition / VS Code + Java Extension Pack
- **API 測試**: Postman / Insomnia
- **資料庫工具**: DBeaver / DataGrip
- **容器化**: Docker Desktop（選用）

### Maven Wrapper
- 使用 Maven Wrapper (`mvnw`) 確保版本一致
- Windows: `mvnw.cmd`
- Linux/Mac: `./mvnw`

## 參考原始專案

本學習專案參考自：
- [Spring PetClinic Microservices](https://github.com/spring-petclinic/spring-petclinic-microservices)
- 原始專案使用 Apache 2.0 授權

### 主要調整
1. 簡化架構以適合初學者
2. 拆分為獨立學習模組
3. 增加中文教學文件
4. 每個模組可獨立執行
5. 降低技術複雜度（如：初期不引入容器化）

## 版本控制

- 每個模組完成後打上版本標籤：`module-01-v1.0.0`
- 遵循語義化版本：`MAJOR.MINOR.PATCH`

## AI 輔助開發指引

使用 GitHub Copilot 時：
1. 確保遵循本規範的命名慣例
2. 為生成的程式碼添加充分的中文註解
3. 驗證生成的程式碼符合 Java 17 與 Spring Boot 3 規範
4. 測試所有 AI 生成的程式碼

---

**最後更新**: 2025-01-09
**維護者**: Spring Pet Learning 團隊
