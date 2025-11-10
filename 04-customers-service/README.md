# 模組 04: 微服務架構基礎 - Customers Service & Pets Service

## 學習目標

完成本模組後，你將能夠：

- ✅ 理解微服務架構的核心原則
- ✅ 設計並實現獨立的微服務
- ✅ 配置多服務的不同埠號和資料庫
- ✅ 使用 TDD 開發微服務
- ✅ 理解服務間的獨立性和自主性

## 核心概念

### 1. 微服務 vs 單體式架構

**單體式架構限制**：
- 單一龐大的程式碼庫
- 技術棧固定（如全部用 Spring Boot + MySQL）
- 部署時必須部署整個應用
- 一個模組故障影響整體
- 擴展受限（無法針對性擴展）

**微服務架構優勢**：
- 每個服務獨立開發、測試、部署
- 各服務可用不同技術棧（Java, Node.js 等）
- 故障隔離（一個服務故障不影響其他）
- 獨立擴展（可針對高流量服務擴展）
- 團隊獨立性（各團隊可獨立運作）

### 2. 微服務設計原則

**1. 單一職責原則**：
- Customers Service：只負責客戶資訊管理
- Pets Service：只負責寵物資訊管理
- 每個服務專注一個業務域

**2. 獨立部署**：
- 各服務可獨立編譯、測試、部署
- 不依賴其他服務啟動

**3. 資料隔離**：
- 每個服務有自己的資料庫
- 不直接共享資料庫
- 通過 API 查詢其他服務資料

**4. 服務通訊**：
- 同步：REST API / gRPC
- 非同步：訊息佇列（RabbitMQ, Kafka）

### 3. 服務拆分策略

根據業務域拆分：
```
Customers Service (Port 8081)
├── API: /api/customers
├── DB: customers_db
└── Responsibility: 客戶管理

Pets Service (Port 8082)
├── API: /api/pets
├── DB: pets_db
└── Responsibility: 寵物管理
```

## 快速開始

### 前置需求
- Java 17+
- Maven 3.8+
- 完成模組 03

### 啟動步驟

1. **啟動 Customers Service**
   ```bash
   cd 04-customers-service
   mvn spring-boot:run
   ```
   訪問：`http://localhost:8081/api/customers`

2. **啟動 Pets Service**（新終端機）
   ```bash
   cd 04-pets-service
   mvn spring-boot:run
   ```
   訪問：`http://localhost:8082/api/pets`

3. **分別測試兩個服務**
   ```bash
   # 新增客戶
   curl -X POST http://localhost:8081/api/customers \
     -H "Content-Type: application/json" \
     -d '{"name":"小明","email":"xiaoming@example.com"}'
   
   # 新增寵物
   curl -X POST http://localhost:8082/api/pets \
     -H "Content-Type: application/json" \
     -d '{"name":"小白","type":"狗"}'
   ```

## 三天學習計劃

---

### Day 10: 理解微服務與建立獨立服務（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**微服務架構的演進**
- 單體式 → 微服務 → 服務網格
- 每個進化階段解決的問題
- 何時採用微服務

**獨立服務的特徵**：
1. **自主部署**：不依賴其他服務即可啟動
2. **獨立資料庫**：各自管理數據存儲
3. **API 通訊**：通過標準 HTTP/REST 通訊
4. **性能隔離**：一個服務的性能問題不影響其他

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫 Customers Service 測試**

```java
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CustomerService customerService;
    
    @Test
    void should_CreateCustomer_When_ValidData() throws Exception {
        // Given
        Customer customer = new Customer("小明", "xiaoming@example.com");
        customer.setId(1L);
        when(customerService.create(any(Customer.class))).thenReturn(customer);
        
        // When & Then
        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"小明\",\"email\":\"xiaoming@example.com\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true));
    }
}
```

**🟢 Green - 建立 Customers Service**

```java
// 建立獨立模組：04-customers-service
@SpringBootApplication
public class CustomersServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomersServiceApplication.class, args);
    }
}

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String name;
    
    @Email
    private String email;
    
    // Constructors, Getters, Setters
}

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }
}

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(
            @Valid @RequestBody Customer customer) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(customerService.create(customer)));
    }
}
```

**🔵 Refactor - 配置獨立端口與資料庫**

```yaml
# 04-customers-service/application.yml
spring:
  application:
    name: customers-service
  datasource:
    url: jdbc:h2:mem:customers_db
    username: sa
  h2:
    console:
      enabled: true
      path: /h2-console

server:
  port: 8081
```

#### 實作練習 (30 分鐘)

- [ ] 成功建立 Customers Service
- [ ] CRUD API 全部測試通過
- [ ] 獨立啟動服務在 port 8081
- [ ] 測試覆蓋率 > 80%

---

### Day 11: 建立 Pets Service 與服務獨立性（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**服務獨立性的實踐**
- 複製/參考 Customers Service 創建 Pets Service
- 不同的埠號、資料庫、實體
- 各自獨立的 Repository 和 Service

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫 Pets Service 測試**

```java
@WebMvcTest(PetController.class)
class PetControllerTest {
    
    @Test
    void should_CreatePet_When_ValidData() throws Exception {
        Pet pet = new Pet("小白", "狗");
        pet.setId(1L);
        when(petService.create(any(Pet.class))).thenReturn(pet);
        
        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"小白\",\"type\":\"狗\"}"))
            .andExpect(status().isCreated());
    }
}
```

**🟢 Green - 建立 Pets Service**

```java
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String type;
    
    // Constructors, Getters, Setters
}

@Service
public class PetService {
    private final PetRepository petRepository;
    
    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }
}

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Pet>> createPet(
            @Valid @RequestBody Pet pet) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(petService.create(pet)));
    }
}
```

**🔵 Refactor - 配置與測試**

```yaml
# 04-pets-service/application.yml
spring:
  application:
    name: pets-service
  datasource:
    url: jdbc:h2:mem:pets_db

server:
  port: 8082
```

#### 實作練習 (30 分鐘)

- [ ] 建立 Pets Service（參考 Customers Service）
- [ ] 兩個服務獨立運行
- [ ] 各自有獨立的埠和資料庫
- [ ] CRUD 功能完整
- [ ] 測試覆蓋率 > 80%

---

### Day 12: 服務多實例與負載平衡初探（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**微服務擴展策略**
- 垂直擴展：增加單個實例的資源
- 水平擴展：運行多個實例（微服務的優勢）
- 負載平衡：分散請求到多個實例

**多實例運行場景**：
```
Client Request
    ↓
Load Balancer (8080)
    ↙           ↘
Instance 1    Instance 2
(8081)        (8082)
```

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫多實例測試**

```java
// 測試多個客戶服務實例可獨立運行
@Test
void should_SupportMultipleInstances() {
    // 驗證服務元數據（實例 ID、版本等）
    String instance1 = "customers-service-instance-1";
    String instance2 = "customers-service-instance-2";
    
    assertThat(instance1).isNotEqualTo(instance2);
}
```

**🟢 Green - 支援多實例部署**

```yaml
# application.yml 中加入實例識別資訊
spring:
  application:
    name: customers-service
  jpa:
    show-sql: true

server:
  port: 8081
  servlet:
    context-path: /
    
# 自訂配置：識別實例
app:
  instance-id: customers-service-1
  version: 1.0.0
```

```java
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Value("${app.instance-id}")
    private String instanceId;
    
    /**
     * 健康檢查端點（用於負載平衡器）
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        Map<String, String> info = Map.of(
            "status", "UP",
            "instanceId", instanceId
        );
        return ResponseEntity.ok(ApiResponse.success(info));
    }
}
```

**🔵 Refactor - 整理多服務部署配置**

```yaml
# 通用配置模板
spring:
  application:
    name: service-name
  datasource:
    url: jdbc:h2:mem:service_db
    driverClassName: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop

server:
  port: 8081  # 各服務不同的埠

app:
  instance-id: service-1
```

#### 實作練習 (30 分鐘)

- [ ] 在不同終端機運行 Customers Service 和 Pets Service
- [ ] 確認兩個服務都能正常運行
- [ ] 各自有獨立的資料庫
- [ ] 添加健康檢查端點
- [ ] 文件說明如何同時啟動多個服務

## 重點程式碼解析

### 1. 獨立服務結構

```java
/**
 * 標準微服務結構
 */
src/main/java/com/petlearning/customers/
├── CustomersServiceApplication.java  // 啟動類
├── controller/
│   └── CustomerController.java       // REST API
├── service/
│   └── CustomerService.java          // 業務邏輯
├── repository/
│   └── CustomerRepository.java       // 資料存取
├── entity/
│   └── Customer.java                 // 資料模型
└── exception/
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java
```

### 2. 服務獨立性檢查清單

```
☑️ 獨立的 pom.xml（可獨立編譯）
☑️ 獨立的 application.yml（獨立配置）
☑️ 獨立的資料庫（H2 mem 或 MySQL）
☑️ 獨立的埠號（8081, 8082, ...）
☑️ 完整的 CRUD API
☑️ 健康檢查端點
☑️ 異常處理器
☑️ 單元測試覆蓋 > 80%
```

### 3. 多服務啟動腳本

```bash
#!/bin/bash
# 同時啟動多個服務（Linux/Mac）

echo "啟動 Customers Service..."
cd 04-customers-service
mvn spring-boot:run -Dspring.profiles.active=customer &

echo "啟動 Pets Service..."
cd 04-pets-service
mvn spring-boot:run -Dspring.profiles.active=pet &

wait
```

## 常見問題

**Q1: 為什麼每個服務要有自己的資料庫？**
- 高內聚，低耦合的設計原則
- 一個服務的資料庫變更不影響其他服務
- 各服務可選擇最適合的資料庫技術

**Q2: 微服務之間如何共享資料？**
- 通過 REST API 查詢其他服務
- 非同步訊息（進階主題）
- 資料複製（CQRS 模式）

**Q3: 同時運行多個 Spring Boot 服務會佔用很多記憶體？**
- 是的，開發環境下建議用 H2 記憶體資料庫
- 生產環境使用共享資料庫或微服務編排工具（Docker, Kubernetes）

**Q4: 如何測試微服務之間的通訊？**
- 整合測試：啟動多個服務後測試
- Mock 測試：Mock 外部服務呼叫
- 契約測試：定義服務間的 API 契約

## 評量檢核表

### Day 10
- [ ] 理解微服務的核心概念
- [ ] Customers Service 獨立運行
- [ ] 完整的 CRUD API
- [ ] 所有測試通過
- [ ] 測試覆蓋率 > 80%

### Day 11
- [ ] Pets Service 成功建立
- [ ] 兩個服務獨立部署在不同埠
- [ ] 各有獨立的資料庫
- [ ] 所有測試通過
- [ ] 測試覆蓋率 > 80%

### Day 12
- [ ] 理解多實例部署
- [ ] 添加健康檢查端點
- [ ] 編寫多服務啟動指南
- [ ] 理解負載平衡的概念
- [ ] 檔案完整清晰

## 延伸閱讀

- [微服務架構模式](https://microservices.io/)
- [Sam Newman - 微服務構建](https://samnewman.io/books/building_microservices/)
- [Spring Cloud 官方文件](https://spring.io/cloud/)

---

**下一步**: 完成本模組後，前往 [模組 05: Eureka 服務發現](../05-discovery-server/README.md)
