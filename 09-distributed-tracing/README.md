# 模組 09: 分散式追蹤與 Zipkin

## 學習目標

完成本模組後，你將能夠：

- ✅ 理解分散式追蹤的概念
- ✅ 使用 Sleuth 生成追蹤 ID
- ✅ 搭建 Zipkin 伺服器
- ✅ 可視化跨服務請求路徑
- ✅ 分析性能瓶頸

## 核心概念

### 1. 分散式追蹤的必要性

**問題**：
- 單一請求跨越多個服務
- 難以追蹤完整的請求流程
- 難以診斷性能問題

**解決方案**：
- Sleuth：自動為請求分配追蹤 ID
- Zipkin：可視化請求路徑

### 2. 追蹤信息

```
Trace ID: b7ad6b7169e9d6f1
  ├── Span ID: b7ad6b7169e9d6f1 (API Gateway)
  │   ├── Span ID: 27ae868e8cc7aa23 (Customers Service)
  │   └── Span ID: 3ce0d3da69d5dd60 (Pets Service)
```

### 3. 性能分析

- Trace：完整的請求鏈路
- Span：單個服務的執行
- 耗時統計
- 瓶頸識別

## 快速開始

```bash
# 啟動 Zipkin
docker run -d -p 9411:9411 openzipkin/zipkin

# 或使用本模組提供的 Zipkin
cd 09-distributed-tracing
mvn spring-boot:run

# 訪問 Zipkin UI
http://localhost:9411
```

## 學習計劃

### Day 25: Sleuth 和追蹤 ID

- Sleuth 集成
- 追蹤 ID 生成
- 日誌集成

### Day 26: Zipkin 可視化

- Zipkin 伺服器搭建
- 請求可視化
- 路徑分析

### Day 27: 性能分析

- 耗時統計
- 瓶頸識別
- 優化建議

---

**相關模組**: [模組 10 - Monitoring](../10-monitoring/README.md)
