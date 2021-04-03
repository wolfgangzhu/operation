## 项目介绍

该模块目前提供以下功能：

1. 将业务逻辑封装为一个操作，监听，回调该操作的结果
2. 将业务部分抽离，实现代码复用

## 配置（How To Configuration)

1. 配置数据库 执行sql/operation_schema.sql 配置mapper-location 为 `sqlmap/**/**.xml`

## 使用（How to Use)

1. OperationTemplate

```java

@Autowired
    AsyncCallTemplate asyncCallTemplate;

@Test public void operationExecuteTest(){
        asyncCallTemplate.asyncCall(CallType.JOIN.name(),ZenInstances.builder().instance("1").type("pvl").instance("3").build(),
        buildParameters(),new JoinPvlCaller(),PvlServerCallbackReceiver.class,null);
        }
```

