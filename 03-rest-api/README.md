# 模組 03: RESTful API 設計

## 學習目標

完成本模組後，你將能夠：

- ✅ 理解 REST 架構風格及其六大原則
- ✅ 掌握 HTTP 方法與 CRUD 操作的對應關係
- ✅ 實作完整的 CRUD API（GET, POST, PUT, DELETE）
- ✅ 設計統一的 API 回應格式
- ✅ 實現全域異常處理機制
- ✅ 使用 TDD 開發 REST Controller

## 核心概念

### 1. REST（表現層狀態轉換）

**REST 六大原則**：
- **資源導向** (Resource-Oriented)：每個 URL 代表一個資源
- **統一介面** (Uniform Interface)：使用標準 HTTP 方法操作
- **無狀態** (Stateless)：每個請求獨立，不依賴伺服器狀態
- **可快取** (Cacheable)：服務端可標記回應為可快取
- **客戶端-伺服器結構** (Client-Server Architecture)：分離關注點
- **分層系統** (Layered System)：系統分層不影響客戶端

### 2. HTTP 方法與 CRUD 對應

| HTTP 方法 | 操作 | 目的 | 狀態碼 |
|----------|------|------|--------|
| GET | Read（查詢）| 取得資源 | 200, 404 |
| POST | Create（新增）| 建立新資源 | 201, 400 |
| PUT | Update（更新）| 完整更新資源 | 200, 404 |
| DELETE | Delete（刪除）| 刪除資源 | 204, 404 |

### 3. HTTP 狀態碼詳解

**2xx - 成功**：
- `200 OK`: 請求成功
- `201 Created`: 資源成功建立

**3xx - 重定向**：
- `301 Moved Permanently`: 永久重定向
- `302 Found`: 暫時重定向

**4xx - 客戶端錯誤**：
- `400 Bad Request`: 請求參數錯誤
- `404 Not Found`: 資源不存在
- `409 Conflict`: 請求衝突（如重複資源）

**5xx - 伺服器錯誤**：
- `500 Internal Server Error`: 伺服器內部錯誤

### 4. API 設計最佳實踐

**URL 設計原則**：
```
✅ /api/owners (資源複數形)
✅ /api/owners/1 (用 ID 表示具體資源)
✅ /api/owners/1/pets (資源嵌套表示關係)

❌ /api/getOwner (不包含動詞)
❌ /api/owner (不是複數形)
❌ /api/owners/create (HTTP 方法已代表操作)
```

**回應格式統一**：
```json
{
  "success": true,
  "message": "操作成功",
  "data": { /* 實際資料 */ }
}
```

## 快速開始

### 前置需求
- Java 17+
- Maven 3.8+
- 完成模組 02

### 啟動步驟

1. **編譯專案**
   ```bash
   cd 03-rest-api
   mvn clean install
   ```

2. **啟動應用**
   ```bash
   mvn spring-boot:run
   ```

3. **使用 Postman/curl 測試 API**
   ```bash
   # 查詢所有飼主
   curl http://localhost:8080/api/owners
   
   # 新增飼主
   curl -X POST http://localhost:8080/api/owners \
     -H "Content-Type: application/json" \
     -d '{"firstName":"小明","lastName":"王"}'
   ```

## 三天學習計劃

---

### Day 7: 第一個 REST API (GET)（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**REST 架構的核心理念**
- 資源是 REST 的中心，每個 URL 代表一個資源
- HTTP 方法表示對資源的操作
- 無狀態設計使得服務易於擴展

**Spring REST 常用註解**：
```java
@RestController           // = @Controller + @ResponseBody
@RequestMapping("/api")   // 基本路徑
@GetMapping               // = @RequestMapping(method = GET)
@PostMapping              // = @RequestMapping(method = POST)
@PathVariable             // 從 URL 路徑提取參數
@RequestParam             // 從查詢字串提取參數
```

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫 Controller 測試**

```java
// src/test/java/com/petlearning/restapi/controller/OwnerControllerTest.java
package com.petlearning.restapi.controller;

import com.petlearning.restapi.entity.Owner;
import com.petlearning.restapi.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * OwnerController 測試
 * 使用 @WebMvcTest 進行 Web 層測試
 */
@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OwnerService ownerService;
    
    @Test
    void should_ReturnAllOwners_When_GetRequest() throws Exception {
        // Given
        List<Owner> owners = Arrays.asList(
            new Owner("小明", "王"),
            new Owner("小華", "李")
        );
        when(ownerService.findAll()).thenReturn(owners);
        
        // When & Then
        mockMvc.perform(get("/api/owners"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data", hasSize(2)))
            .andExpect(jsonPath("$.data[0].firstName").value("小明"));
    }
    
    @Test
    void should_ReturnOwner_When_ValidId() throws Exception {
        // Given
        Owner owner = new Owner("小明", "王");
        owner.setId(1L);
        when(ownerService.findById(1L)).thenReturn(owner);
        
        // When & Then
        mockMvc.perform(get("/api/owners/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.firstName").value("小明"));
    }
    
    @Test
    void should_Return404_When_OwnerNotFound() throws Exception {
        // Given
        when(ownerService.findById(999L))
            .thenThrow(new com.petlearning.restapi.exception.ResourceNotFoundException("找不到飼主"));
        
        // When & Then
        mockMvc.perform(get("/api/owners/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false));
    }
}
```

**🟢 Green - 實作 Controller**

```java
// src/main/java/com/petlearning/restapi/controller/OwnerController.java
package com.petlearning.restapi.controller;

import com.petlearning.restapi.dto.ApiResponse;
import com.petlearning.restapi.entity.Owner;
import com.petlearning.restapi.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 飼主管理 API
 * 基本路徑：/api/owners
 */
@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    
    private final OwnerService ownerService;
    
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    
    /**
     * 查詢所有飼主
     * GET /api/owners
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Owner>>> getAllOwners() {
        List<Owner> owners = ownerService.findAll();
        return ResponseEntity.ok(ApiResponse.success(owners));
    }
    
    /**
     * 根據 ID 查詢單一飼主
     * GET /api/owners/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Owner>> getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(owner));
    }
}
```

**🔵 Refactor - 實作 Service 層**

```java
// src/main/java/com/petlearning/restapi/service/OwnerService.java
package com.petlearning.restapi.service;

import com.petlearning.restapi.entity.Owner;
import com.petlearning.restapi.exception.ResourceNotFoundException;
import com.petlearning.restapi.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 飼主業務邏輯服務
 */
@Service
public class OwnerService {
    
    private final OwnerRepository ownerRepository;
    
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    
    /**
     * 查詢所有飼主
     */
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }
    
    /**
     * 依 ID 查詢飼主
     */
    public Owner findById(Long id) {
        return ownerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("找不到 ID 為 " + id + " 的飼主"));
    }
}
```

#### 實作練習 (30 分鐘)

- [ ] 執行測試，確認全部通過
- [ ] 啟動應用並使用 Postman 測試 GET 端點
- [ ] 測試查詢不存在的 ID，確認回傳 404
- [ ] 測試覆蓋率達到 80% 以上

---

### Day 8: 完整 CRUD API（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**POST、PUT、DELETE 的設計原則**

| 操作 | 方法 | 路徑 | 請求體 | 回應 |
|------|------|------|--------|------|
| 新增 | POST | /api/owners | Owner 資料 | 201 + 新資源 |
| 更新 | PUT | /api/owners/{id} | 完整 Owner 資料 | 200 + 更新資源 |
| 刪除 | DELETE | /api/owners/{id} | 無 | 204 |

**@RequestBody 與參數驗證**：
```java
@PostMapping
public ResponseEntity<ApiResponse<Owner>> createOwner(
        @Valid @RequestBody Owner owner) {
    // @Valid 觸發 Bean Validation 驗證
    Owner saved = ownerService.create(owner);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(saved));
}
```

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫 POST/PUT/DELETE 測試**

```java
@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private OwnerService ownerService;
    
    @Test
    void should_CreateOwner_When_ValidData() throws Exception {
        // Given
        Owner owner = new Owner("小明", "王");
        owner.setId(1L);
        when(ownerService.create(any(Owner.class))).thenReturn(owner);
        
        // When & Then
        mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"小明\",\"lastName\":\"王\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(1));
    }
    
    @Test
    void should_Return400_When_InvalidData() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"\",\"lastName\":\"\"}"))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    void should_UpdateOwner_When_IdExists() throws Exception {
        // Given
        Owner updated = new Owner("小明更新", "王");
        updated.setId(1L);
        when(ownerService.update(eq(1L), any(Owner.class)))
            .thenReturn(updated);
        
        // When & Then
        mockMvc.perform(put("/api/owners/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"小明更新\",\"lastName\":\"王\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.firstName").value("小明更新"));
    }
    
    @Test
    void should_DeleteOwner_When_IdExists() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/owners/1"))
            .andExpect(status().isNoContent());
    }
}
```

**🟢 Green - 實作 POST/PUT/DELETE**

```java
@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    
    private final OwnerService ownerService;
    
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }
    
    /**
     * 新增飼主
     * POST /api/owners
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Owner>> createOwner(
            @Valid @RequestBody Owner owner) {
        Owner saved = ownerService.create(owner);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(saved));
    }
    
    /**
     * 更新飼主
     * PUT /api/owners/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Owner>> updateOwner(
            @PathVariable Long id,
            @Valid @RequestBody Owner ownerDetails) {
        Owner updated = ownerService.update(id, ownerDetails);
        return ResponseEntity.ok(ApiResponse.success(updated));
    }
    
    /**
     * 刪除飼主
     * DELETE /api/owners/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**🔵 Refactor - Service 層完整實作**

```java
@Service
public class OwnerService {
    
    private final OwnerRepository ownerRepository;
    
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    
    public Owner create(Owner owner) {
        return ownerRepository.save(owner);
    }
    
    public Owner update(Long id, Owner details) {
        Owner owner = findById(id);
        owner.setFirstName(details.getFirstName());
        owner.setLastName(details.getLastName());
        owner.setCity(details.getCity());
        owner.setTelephone(details.getTelephone());
        return ownerRepository.save(owner);
    }
    
    public void delete(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new ResourceNotFoundException("找不到 ID 為 " + id + " 的飼主");
        }
        ownerRepository.deleteById(id);
    }
}
```

#### 實作練習 (30 分鐘)

- [ ] 使用 Postman 測試 POST 新增飼主
- [ ] 使用 PUT 更新飼主資訊
- [ ] 使用 DELETE 刪除飼主
- [ ] 測試不存在資源的刪除（應回傳 404）
- [ ] 完整的 CRUD 操作流程
- [ ] 測試覆蓋率達到 85% 以上

---

### Day 9: 異常處理與回應統一（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**@RestControllerAdvice 全域異常處理**
- 集中管理異常，統一回應格式
- 減少重複的異常處理程式碼
- 提升 API 友善度

**Bean Validation 驗證**
- `@NotBlank`: 不允許空白
- `@Size(min, max)`: 大小範圍
- `@Pattern`: 正則運算式匹配
- `@Email`: 郵箱格式

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫異常處理測試**

```java
@WebMvcTest(OwnerController.class)
class OwnerControllerTest {
    
    @Test
    void should_Return404WithMessage_When_NotFound() throws Exception {
        when(ownerService.findById(999L))
            .thenThrow(new ResourceNotFoundException("找不到飼主"));
        
        mockMvc.perform(get("/api/owners/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.message").value("找不到飼主"));
    }
    
    @Test
    void should_Return400_When_NameIsBlank() throws Exception {
        mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"\",\"lastName\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false));
    }
}
```

**🟢 Green - 實作異常處理**

```java
// src/main/java/com/petlearning/restapi/exception/ResourceNotFoundException.java
package com.petlearning.restapi.exception;

/**
 * 資源找不到異常
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

```java
// src/main/java/com/petlearning/restapi/exception/GlobalExceptionHandler.java
package com.petlearning.restapi.exception;

import com.petlearning.restapi.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全域異常處理器
 * 捕獲所有 Controller 拋出的異常並統一處理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 處理資源找不到異常
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(
            ResourceNotFoundException ex) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error(ex.getMessage()));
    }
    
    /**
     * 處理驗證失敗異常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationError(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .findFirst()
            .orElse("驗證失敗");
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.error(message));
    }
    
    /**
     * 處理所有其他異常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(
            Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.error("系統錯誤：" + ex.getMessage()));
    }
}
```

**🔵 Refactor - 加入驗證註解**

```java
// src/main/java/com/petlearning/restapi/entity/Owner.java
@Entity
@Table(name = "owners")
public class Owner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "名字不可為空")
    @Size(min = 1, max = 50, message = "名字長度必須在 1-50 之間")
    private String firstName;
    
    @NotBlank(message = "姓氏不可為空")
    @Size(min = 1, max = 50, message = "姓氏長度必須在 1-50 之間")
    private String lastName;
    
    @Size(max = 100, message = "城市名稱不可超過 100 字元")
    private String city;
    
    @Pattern(regexp = "^(09\\d{8})?$", message = "電話格式錯誤")
    private String telephone;
    
    // ... 其餘程式碼
}
```

#### 實作練習 (30 分鐘)

- [ ] 測試各種驗證失敗情況
- [ ] 測試資源不存在的異常
- [ ] 測試 500 內部錯誤
- [ ] 驗證所有異常都有適當的回應
- [ ] 測試覆蓋率達到 85% 以上

## 重點程式碼解析

### 1. @WebMvcTest 單元測試

```java
@WebMvcTest(OwnerController.class)  // 只載入 Controller 相關元件
class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;  // 虛擬 HTTP 請求
    
    @MockBean
    private OwnerService ownerService;  // Mock 依賴
    
    // 測試...
}
```

### 2. 統一回應 DTO

```java
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    
    public static <T> ApiResponse<T> success(T data) {
        // 成功回應
    }
    
    public static <T> ApiResponse<T> error(String message) {
        // 錯誤回應
    }
}
```

### 3. @RestControllerAdvice 異常處理

```java
@RestControllerAdvice  // 作用於所有 @RestController
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            ResourceNotFoundException ex) {
        // 統一處理異常，回傳統一格式
    }
}
```

## 常見問題

**Q1: GET 和 HEAD 有什麼區別？**
- 都是查詢操作，但 HEAD 不回傳響應體，只回傳 headers
- 用於檢查資源是否存在或更新時間

**Q2: POST 和 PUT 的區別？**
- POST: 用於建立新資源，多次請求會建立多個資源
- PUT: 用於更新或完全替換資源，多次請求結果相同（冪等）

**Q3: 什麼時候用 PATCH？**
- PATCH 用於部分更新（只更新部分欄位）
- 本課程簡化為使用 PUT 完整替換

**Q4: API 版本控制怎麼做？**
- 方式 1：`/api/v1/owners` (路徑版本)
- 方式 2：Headers 中指定版本
- 方式 3：查詢參數 `?version=1`

## 評量檢核表

完成以下檢核項目，確認學習成效：

### Day 7
- [ ] 理解 REST 六大原則
- [ ] 實作並測試 GET 查詢端點
- [ ] 理解 @PathVariable 與 @RequestParam
- [ ] Controller 層使用 @WebMvcTest 測試
- [ ] 測試覆蓋率 > 80%

### Day 8
- [ ] 實作並測試 POST 新增端點
- [ ] 實作並測試 PUT 更新端點
- [ ] 實作並測試 DELETE 刪除端點
- [ ] 完整 CRUD 流程正常運作
- [ ] 測試覆蓋率 > 85%

### Day 9
- [ ] 實現全域異常處理（@RestControllerAdvice）
- [ ] 統一 API 回應格式
- [ ] Bean Validation 驗證規則生效
- [ ] 異常訊息清楚易懂
- [ ] 測試覆蓋率 > 85%

## 延伸閱讀

- [Spring Web MVC 官方文件](https://spring.io/guides/gs/rest-service/)
- [REST API 設計最佳實踐](https://restfulapi.net/)
- [Spring Validation 官方指南](https://spring.io/guides/gs/validating-form-input/)
- [OpenAPI/Swagger 規範](https://swagger.io/specification/)

---

**下一步**: 完成本模組後，前往 [模組 04: 微服務架構基礎](../04-customers-service/README.md)
