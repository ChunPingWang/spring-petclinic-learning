# 模組 07: Spring Cloud Gateway API 閘道

## 學習目標

完成本模組後，你將能夠：

- ✅ 理解 API Gateway 的功能
- ✅ 搭建 Spring Cloud Gateway
- ✅ 配置路由規則
- ✅ 實現負載平衡
- ✅ 實現請求過濾和認證

## 核心概念

### 1. API Gateway 的角色

```
┌─────────────────────────────────────────┐
│           客戶端（APP、Web）            │
└────────────────────┬────────────────────┘
                     │
          ┌──────────▼──────────┐
          │    API Gateway      │
          │   (Port 8080)       │
          │  ┌────────────────┐ │
          │  │  路由規則      │ │
          │  │  認證過濾      │ │
          │  │  限流          │ │
          │  │  負載平衡      │ │
          │  └────────────────┘ │
          └──────────┬──────────┘
                     │
        ┌────────────┼────────────┐
        │            │            │
   ┌────▼───┐  ┌────▼───┐  ┌────▼───┐
   │Service │  │Service │  │Service │
   │  A     │  │  B     │  │  C     │
   └────────┘  └────────┘  └────────┘
```

### 2. Gateway 核心功能

- **路由**：將請求轉發到對應服務
- **負載平衡**：分散請求到多個實例
- **限流**：保護後端服務
- **認證**：統一的安全檢查
- **監控**：請求監控和日誌

### 3. 路由配置

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: customers-route
          uri: lb://customers-service
          predicates:
            - Path=/api/customers/**
          filters:
            - StripPrefix=0
            
        - id: pets-route
          uri: lb://pets-service
          predicates:
            - Path=/api/pets/**
```

## 快速開始

```bash
# 啟動 Gateway
cd 07-api-gateway
mvn spring-boot:run

# 訪問統一入口
curl http://localhost:8080/api/customers
curl http://localhost:8080/api/pets
```

## 學習計劃

### Day 19: Gateway 基礎與路由

- Gateway 搭建
- 基本路由配置
- 路由匹配規則

### Day 20: 過濾和負載平衡

- 請求過濾
- 響應修改
- 集成 Eureka 實現自動負載平衡

### Day 21: 安全與限流

- 認證過濾
- 速率限制
- 監控和日誌

---

**相關模組**: [模組 08 - Circuit Breaker](../08-circuit-breaker/README.md)
