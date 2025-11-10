# 模組 06: Spring Cloud Config 配置中心

## 學習目標

完成本模組後，你將能夠：

- ✅ 理解集中配置管理的必要性
- ✅ 搭建 Config Server
- ✅ 使用 Git 儲存配置
- ✅ 配置不同的運行環境（開發、測試、生產）
- ✅ 實現動態配置更新

## 核心概念

### 1. 為什麼需要配置中心？

**問題**：
- 分散式配置難以管理
- 修改配置需要重新部署應用
- 不同環境配置不同
- 敏感信息（密碼、密鑰）難以保護

**解決方案**：
- Config Server：集中管理所有配置
- 支援 Git 儲存庫
- 支援實時更新
- 支援配置加密

### 2. 配置管理流程

```
開發者提交配置到 Git
        ↓
    Git 儲存庫
        ↓
  Config Server
    ↙        ↘
應用A        應用B
```

### 3. 環境隔離（Profile）

```
配置檔案命名規則：
application.yml              # 公用配置
application-dev.yml         # 開發環境
application-test.yml        # 測試環境
application-prod.yml        # 生產環境
```

## 快速開始

```bash
# 啟動 Config Server
cd 06-config-server
mvn spring-boot:run

# 訪問 Config Server
http://localhost:8888/customers-service/dev

# 返回內容：該服務在 dev 環境的配置
```

## 學習計劃

### Day 16: Config Server 架構與搭建

- Config Server 搭建
- Git 儲存庫配置
- 基本配置提取

### Day 17: 客戶端配置與動態更新

- 微服務接收配置
- Profile 切換
- 動態配置更新

### Day 18: 生產配置與安全

- 環境隔離
- 敏感信息加密
- 配置版本管理

---

**相關模組**: [模組 07 - API Gateway](../07-api-gateway/README.md)
