# 模組 02: 資料持久化與 Spring Data JPA

## 學習目標

完成本模組後，你將能夠：

- ✅ 理解 ORM（物件關聯映射）概念
- ✅ 使用 H2 記憶體資料庫進行開發
- ✅ 掌握 JPA 實體（Entity）建立與配置
- ✅ 使用 Spring Data JPA Repository 進行 CRUD 操作
- ✅ 實作實體之間的關聯映射（@OneToMany）
- ✅ 遵循 TDD 開發流程

## 核心概念

### 1. ORM (Object-Relational Mapping)

**什麼是 ORM？**
- ORM 是一種程式技術，用於在物件導向程式語言中，將資料庫表格轉換為 Java 物件
- 讓開發者可以用 Java 程式碼操作資料庫，而不需要寫 SQL 語句

**為什麼需要 ORM？**
- 減少重複的 SQL 程式碼
- 提高開發效率
- 資料庫移植性更好（可輕易切換資料庫類型）
- 物件導向的資料操作方式

### 2. JPA (Java Persistence API)

- JPA 是 Java 官方的 ORM 規範
- Hibernate 是最流行的 JPA 實作
- Spring Data JPA 進一步簡化了 JPA 的使用

### 3. H2 記憶體資料庫

**特性**：
- 純 Java 撰寫的輕量級資料庫
- 支援記憶體模式（應用程式關閉後資料消失）
- 內建 Web Console 介面
- 適合開發與測試環境

**為什麼使用 H2？**
- 無需安裝額外資料庫軟體
- 快速啟動與測試
- 自動建立資料表結構
- 降低學習門檻

## 快速開始

### 前置需求
- Java 17+
- Maven 3.8+
- 完成模組 01

### 啟動步驟

1. **編譯專案**
   ```bash
   cd 02-spring-data-jpa
   mvn clean install
   ```

2. **啟動應用**
   ```bash
   mvn spring-boot:run
   ```

3. **訪問 H2 Console**
   - URL: http://localhost:8080/h2-console
   - JDBC URL: `jdbc:h2:mem:petdb`
   - Username: `sa`
   - Password: (空白)

4. **查看自動建立的表格**
   ```sql
   SHOW TABLES;
   SELECT * FROM OWNERS;
   ```

## 三天學習計劃

---

### Day 4: H2 資料庫與第一個實體（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**JPA 實體 (Entity) 是什麼？**
- Entity 是一個標註了 `@Entity` 的 Java 類別
- 對應資料庫中的一張表格
- 類別的屬性對應表格的欄位

**常用 JPA 註解**：
```java
@Entity              // 標記為 JPA 實體
@Table(name = "xxx") // 指定表格名稱（可選）
@Id                  // 標記主鍵
@GeneratedValue      // 自動產生主鍵值
@Column              // 指定欄位屬性
```

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫實體測試**

```java
// src/test/java/com/petlearning/jpa/entity/OwnerTest.java
package com.petlearning.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * 飼主實體測試
 * 先寫測試，再實作實體類別
 */
class OwnerTest {
    
    @Test
    void should_CreateOwner_With_RequiredFields() {
        // Given & When
        Owner owner = new Owner("小明", "王");
        owner.setCity("台北市");
        owner.setTelephone("0912345678");
        
        // Then
        assertThat(owner.getFirstName()).isEqualTo("小明");
        assertThat(owner.getLastName()).isEqualTo("王");
        assertThat(owner.getCity()).isEqualTo("台北市");
        assertThat(owner.getTelephone()).isEqualTo("0912345678");
    }
    
    @Test
    void should_ThrowException_When_FirstNameIsNull() {
        // When & Then
        assertThatThrownBy(() -> new Owner(null, "王"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("姓名不可為空");
    }
    
    @Test
    void should_ThrowException_When_LastNameIsNull() {
        // When & Then
        assertThatThrownBy(() -> new Owner("小明", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("姓名不可為空");
    }
}
```

**🟢 Green - 建立 Owner 實體**

```java
// src/main/java/com/petlearning/jpa/entity/Owner.java
package com.petlearning.jpa.entity;

import jakarta.persistence.*;

/**
 * 飼主實體
 * 對應資料庫中的 owners 表格
 */
@Entity
@Table(name = "owners")
public class Owner {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String firstName;
    
    @Column(nullable = false, length = 50)
    private String lastName;
    
    @Column(length = 100)
    private String city;
    
    @Column(length = 20)
    private String telephone;
    
    /**
     * 建構子：建立飼主時必須提供姓名
     */
    public Owner(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("姓名不可為空");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Protected 無參建構子（JPA 需要）
     */
    protected Owner() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
```

**🔵 Refactor - 加入 Bean Validation**

```java
import jakarta.validation.constraints.*;

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
    
    @Pattern(regexp = "^09\\d{8}$", message = "電話格式錯誤（應為 09 開頭的 10 碼）")
    private String telephone;
    
    // ... 其餘程式碼不變
}
```

#### 配置 H2 資料庫 (30 分鐘)

**application.yml 配置**

```yaml
spring:
  application:
    name: spring-data-jpa
  
  # H2 資料庫配置
  datasource:
    url: jdbc:h2:mem:petdb
    driverClassName: org.h2.Driver
    username: sa
    password:
  
  # H2 Console 配置
  h2:
    console:
      enabled: true
      path: /h2-console
  
  # JPA 配置
  jpa:
    show-sql: true                # 顯示 SQL 語句
    hibernate:
      ddl-auto: create-drop       # 自動建立/刪除表格
    properties:
      hibernate:
        format_sql: true          # 格式化 SQL

server:
  port: 8080
```

#### 實作練習

- [ ] 執行測試，確認全部通過
- [ ] 啟動應用，訪問 H2 Console
- [ ] 查看自動建立的 `owners` 表格結構
- [ ] 新增測試：驗證電話號碼格式（使用 @Pattern）
- [ ] 測試覆蓋率達到 90% 以上

---

### Day 5: Repository 與 CRUD 操作（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**Spring Data JPA Repository**
- Spring Data JPA 自動實作常用的資料庫操作方法
- 只需定義介面，無需撰寫實作類別

**JpaRepository 提供的方法**：
```java
save(entity)          // 新增或更新
findById(id)          // 依 ID 查詢
findAll()             // 查詢全部
deleteById(id)        // 依 ID 刪除
count()               // 計算數量
existsById(id)        // 檢查是否存在
```

**衍生查詢（Derived Query）**
- 根據方法名稱自動產生查詢語句
- 例如：`findByLastName` → `SELECT ... WHERE last_name = ?`

#### TDD 實作 (90 分鐘)

**🔴 Red - 寫 Repository 測試**

```java
// src/test/java/com/petlearning/jpa/repository/OwnerRepositoryTest.java
package com.petlearning.jpa.repository;

import com.petlearning.jpa.entity.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * OwnerRepository 測試
 * 使用 @DataJpaTest 進行資料層測試
 */
@DataJpaTest
class OwnerRepositoryTest {
    
    @Autowired
    private OwnerRepository repository;
    
    @Test
    void should_SaveAndRetrieveOwner() {
        // Given
        Owner owner = new Owner("小明", "王");
        owner.setCity("台北市");
        owner.setTelephone("0912345678");
        
        // When
        Owner saved = repository.save(owner);
        Owner found = repository.findById(saved.getId()).orElseThrow();
        
        // Then
        assertThat(saved.getId()).isNotNull();
        assertThat(found.getFirstName()).isEqualTo("小明");
        assertThat(found.getCity()).isEqualTo("台北市");
    }
    
    @Test
    void should_FindOwnersByLastName() {
        // Given
        repository.save(new Owner("小明", "王"));
        repository.save(new Owner("小華", "王"));
        repository.save(new Owner("小美", "李"));
        
        // When
        List<Owner> wangs = repository.findByLastName("王");
        
        // Then
        assertThat(wangs).hasSize(2);
        assertThat(wangs).extracting("lastName").containsOnly("王");
    }
    
    @Test
    void should_FindOwnersByCity() {
        // Given
        Owner owner1 = new Owner("小明", "王");
        owner1.setCity("台北市");
        Owner owner2 = new Owner("小華", "李");
        owner2.setCity("台北市");
        Owner owner3 = new Owner("小美", "陳");
        owner3.setCity("台中市");
        
        repository.save(owner1);
        repository.save(owner2);
        repository.save(owner3);
        
        // When
        List<Owner> taipei = repository.findByCity("台北市");
        
        // Then
        assertThat(taipei).hasSize(2);
        assertThat(taipei).extracting("city").containsOnly("台北市");
    }
    
    @Test
    void should_DeleteOwner() {
        // Given
        Owner owner = new Owner("小明", "王");
        Owner saved = repository.save(owner);
        Long id = saved.getId();
        
        // When
        repository.deleteById(id);
        
        // Then
        assertThat(repository.findById(id)).isEmpty();
    }
    
    @Test
    void should_UpdateOwner() {
        // Given
        Owner owner = new Owner("小明", "王");
        Owner saved = repository.save(owner);
        
        // When
        saved.setCity("台中市");
        Owner updated = repository.save(saved);
        
        // Then
        assertThat(updated.getCity()).isEqualTo("台中市");
    }
}
```

**🟢 Green - 建立 Repository**

```java
// src/main/java/com/petlearning/jpa/repository/OwnerRepository.java
package com.petlearning.jpa.repository;

import com.petlearning.jpa.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 飼主資料存取介面
 * 繼承 JpaRepository 即可擁有常用的 CRUD 方法
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    
    /**
     * 依姓氏查詢飼主
     * Spring Data JPA 會自動產生查詢語句
     */
    List<Owner> findByLastName(String lastName);
    
    /**
     * 依城市查詢飼主
     */
    List<Owner> findByCity(String city);
}
```

**🔵 Refactor - 優化測試**

```java
@DataJpaTest
class OwnerRepositoryTest {
    
    @Autowired
    private OwnerRepository repository;
    
    /**
     * 每個測試前清空資料
     */
    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }
    
    /**
     * 建立測試用飼主資料
     */
    private Owner createOwner(String firstName, String lastName, String city) {
        Owner owner = new Owner(firstName, lastName);
        owner.setCity(city);
        return owner;
    }
    
    @Test
    void should_FindOwnersByCity() {
        // Given
        repository.save(createOwner("小明", "王", "台北市"));
        repository.save(createOwner("小華", "李", "台北市"));
        repository.save(createOwner("小美", "陳", "台中市"));
        
        // When
        List<Owner> taipei = repository.findByCity("台北市");
        
        // Then
        assertThat(taipei).hasSize(2);
    }
    
    // ... 其他測試
}
```

#### 實作練習 (30 分鐘)

- [ ] 測試：查詢名字包含特定字串的飼主
- [ ] 實作：`List<Owner> findByFirstNameContaining(String name);`
- [ ] 測試：查詢所有飼主並按姓氏排序
- [ ] 實作：`List<Owner> findAllByOrderByLastNameAsc();`
- [ ] 測試覆蓋率達到 90% 以上

---

### Day 6: 資料初始化與關聯映射（TDD 實作）

**學習時長**: 2.5 小時

#### 理論講解 (30 分鐘)

**資料初始化方式**
1. `data.sql`: SQL 腳本自動執行
2. `@PostConstruct`: 應用啟動後執行
3. `CommandLineRunner`: Spring Boot 提供的介面

**JPA 關聯映射**
- `@OneToOne`: 一對一（例：人與身分證）
- `@OneToMany`: 一對多（例：飼主與寵物）
- `@ManyToOne`: 多對一
- `@ManyToMany`: 多對多

**級聯操作（Cascade）**
- `CascadeType.ALL`: 所有操作都級聯
- `CascadeType.PERSIST`: 只有新增時級聯
- `CascadeType.REMOVE`: 只有刪除時級聯

#### TDD 實作 (90 分鐘)

**🔴 Red - 測試寵物實體**

```java
// src/test/java/com/petlearning/jpa/entity/PetTest.java
package com.petlearning.jpa.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class PetTest {
    
    @Test
    void should_CreatePet_With_RequiredFields() {
        // Given & When
        Pet pet = new Pet("小白", "狗");
        
        // Then
        assertThat(pet.getName()).isEqualTo("小白");
        assertThat(pet.getType()).isEqualTo("狗");
    }
    
    @Test
    void should_ThrowException_When_NameIsNull() {
        // When & Then
        assertThatThrownBy(() -> new Pet(null, "狗"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
```

**🟢 Green - 建立 Pet 實體**

```java
// src/main/java/com/petlearning/jpa/entity/Pet.java
package com.petlearning.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pets")
public class Pet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 30)
    private String type;
    
    public Pet(String name, String type) {
        if (name == null || type == null) {
            throw new IllegalArgumentException("寵物名稱和類型不可為空");
        }
        this.name = name;
        this.type = type;
    }
    
    protected Pet() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
```

**🔴 Red - 測試飼主與寵物關聯**

```java
// src/test/java/com/petlearning/jpa/repository/OwnerPetRelationTest.java
package com.petlearning.jpa.repository;

import com.petlearning.jpa.entity.Owner;
import com.petlearning.jpa.entity.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class OwnerPetRelationTest {
    
    @Autowired
    private OwnerRepository ownerRepository;
    
    @Test
    void should_SaveOwnerWithPets() {
        // Given
        Owner owner = new Owner("小明", "王");
        owner.addPet(new Pet("小白", "狗"));
        owner.addPet(new Pet("小黑", "貓"));
        
        // When
        Owner saved = ownerRepository.save(owner);
        
        // Then
        assertThat(saved.getPets()).hasSize(2);
        assertThat(saved.getPets())
            .extracting("name")
            .contains("小白", "小黑");
    }
    
    @Test
    void should_CascadeDeletePets_When_OwnerDeleted() {
        // Given
        Owner owner = new Owner("小明", "王");
        owner.addPet(new Pet("小白", "狗"));
        Owner saved = ownerRepository.save(owner);
        
        // When
        ownerRepository.deleteById(saved.getId());
        
        // Then
        assertThat(ownerRepository.findById(saved.getId())).isEmpty();
    }
}
```

**🟢 Green - 實作關聯映射**

```java
// 修改 Owner.java
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {
    // ... 其他欄位
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_id")
    private List<Pet> pets = new ArrayList<>();
    
    /**
     * 新增寵物
     */
    public void addPet(Pet pet) {
        pets.add(pet);
    }
    
    /**
     * 移除寵物
     */
    public void removePet(Pet pet) {
        pets.remove(pet);
    }
    
    public List<Pet> getPets() {
        return pets;
    }
}
```

**🔵 Refactor - 資料初始化**

```sql
-- src/main/resources/data.sql
INSERT INTO owners (first_name, last_name, city, telephone) VALUES 
  ('小明', '王', '台北市', '0912345678'),
  ('小華', '李', '台中市', '0923456789'),
  ('小美', '陳', '高雄市', '0934567890');

INSERT INTO pets (name, type, owner_id) VALUES
  ('小白', '狗', 1),
  ('小黑', '貓', 1),
  ('咪咪', '貓', 2);
```

#### 實作練習 (30 分鐘)

- [ ] 測試：查詢擁有特定類型寵物的飼主
- [ ] 實作：自定義 JPQL 查詢
- [ ] 驗證級聯刪除功能正常
- [ ] 使用 H2 Console 查看資料
- [ ] 測試覆蓋率達到 90% 以上

## 重點程式碼解析

### 1. Entity 生命週期

```java
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 主鍵，自動生成
    
    // JPA 會自動追蹤實體狀態：
    // - Transient（暫態）：new Owner() 尚未持久化
    // - Persistent（持久態）：repository.save() 後
    // - Detached（分離態）：Session 關閉後
    // - Removed（移除態）：repository.delete() 後
}
```

### 2. 衍生查詢命名規則

| 方法名稱 | 對應 SQL |
|---------|---------|
| `findByLastName` | `WHERE last_name = ?` |
| `findByLastNameAndCity` | `WHERE last_name = ? AND city = ?` |
| `findByLastNameOrCity` | `WHERE last_name = ? OR city = ?` |
| `findByFirstNameContaining` | `WHERE first_name LIKE %?%` |
| `findByIdLessThan` | `WHERE id < ?` |
| `findAllByOrderByLastNameAsc` | `ORDER BY last_name ASC` |

### 3. @DataJpaTest 測試註解

```java
@DataJpaTest  // 只載入 JPA 相關元件
class OwnerRepositoryTest {
    @Autowired
    private OwnerRepository repository;
    
    // 每個測試結束後自動 Rollback
    // 使用記憶體資料庫（H2）
}
```

## 常見問題

**Q1: 為什麼 Entity 需要無參建構子？**
- JPA 使用反射機制建立實體物件時需要
- 必須是 `protected` 或 `public`

**Q2: `ddl-auto: create-drop` 是什麼意思？**
- `create`: 啟動時建立表格
- `drop`: 關閉時刪除表格
- 適合開發環境，生產環境應使用 `validate` 或 `none`

**Q3: orphanRemoval 的作用？**
- 當子實體（Pet）與父實體（Owner）脫離關聯時，自動刪除子實體
- 適用於強依賴關係

**Q4: 如何切換到 MySQL？**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/petdb
    username: root
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update  # 不要使用 create-drop
```

## 評量檢核表

完成以下檢核項目，確認學習成效：

### Day 4
- [ ] 理解 ORM 與 JPA 的概念
- [ ] 成功配置 H2 資料庫
- [ ] 可訪問 H2 Console
- [ ] 建立 Owner 實體並通過測試
- [ ] 理解 @Entity, @Id, @GeneratedValue 的作用
- [ ] 測試覆蓋率 > 90%

### Day 5
- [ ] 建立 OwnerRepository 介面
- [ ] 實作並測試 CRUD 操作
- [ ] 理解衍生查詢命名規則
- [ ] 成功使用 @DataJpaTest 測試
- [ ] 測試覆蓋率 > 90%

### Day 6
- [ ] 建立 Pet 實體
- [ ] 實作 @OneToMany 關聯映射
- [ ] 測試級聯操作
- [ ] 成功載入初始資料（data.sql）
- [ ] 理解 CascadeType 與 orphanRemoval
- [ ] 測試覆蓋率 > 90%

## 延伸閱讀

- [Spring Data JPA 官方文件](https://spring.io/projects/spring-data-jpa)
- [Hibernate 官方文件](https://hibernate.org/orm/documentation/)
- [H2 Database 官方網站](https://www.h2database.com/)
- [JPA 規範文件](https://jakarta.ee/specifications/persistence/)

---

**下一步**: 完成本模組後，前往 [模組 03: RESTful API 設計](../03-rest-api/README.md)
