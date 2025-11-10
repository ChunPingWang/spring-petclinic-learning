# 模組 04: 微服務架構基礎 - Pets Service

## 學習目標

完成本模組後，你將能夠：

- ✅ 構建獨立的寵物管理微服務
- ✅ 配置獨立的埠和資料庫
- ✅ 實現完整的 CRUD API
- ✅ 理解服務間的獨立性

## 快速開始

### 啟動 Pets Service

```bash
cd 04-pets-service
mvn spring-boot:run
```

服務將在 **Port 8082** 啟動，訪問：`http://localhost:8082/api/pets`

### 測試 API

```bash
# 查詢所有寵物
curl http://localhost:8082/api/pets

# 新增寵物
curl -X POST http://localhost:8082/api/pets \
  -H "Content-Type: application/json" \
  -d '{"name":"小白","type":"狗"}'
```

## 服務架構

此服務與 Customers Service 平行運行，完全獨立：

```
Customers Service     Pets Service
(Port 8081)          (Port 8082)
├── DB: customers_db  ├── DB: pets_db
└── API: /api/customers
└── API: /api/pets
```

## 核心實體

### Pet 實體

```
pets 表
├── id (BIGINT, PK)
├── name (VARCHAR(50), NOT NULL)
└── type (VARCHAR(50), NOT NULL)
```

## API 端點

| 方法 | 端點 | 說明 |
|------|------|------|
| GET | /api/pets | 查詢所有寵物 |
| GET | /api/pets/{id} | 查詢單一寵物 |
| POST | /api/pets | 新增寵物 |
| PUT | /api/pets/{id} | 更新寵物 |
| DELETE | /api/pets/{id} | 刪除寵物 |

## 同時啟動兩個服務

使用多個終端機分別啟動：

**終端機 1 - Customers Service**
```bash
cd 04-customers-service
mvn spring-boot:run
```

**終端機 2 - Pets Service**
```bash
cd 04-pets-service
mvn spring-boot:run
```

## 學習重點

- 兩個服務完全獨立
- 各有不同的埠、資料庫、程式碼庫
- 通過 REST API 通訊（進階課程）
- 展示水平擴展的可能性

---

**相關模組**: [04-customers-service](../04-customers-service/README.md)
