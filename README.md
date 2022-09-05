metacode-json-logic-java
---
Metacode-json-logic-java 实现了 <https://jsonlogic.com> 部分规范, 适用于低代码开发平台的条件规则和表达式功能.

## 快速开始

添加依赖

```xml

<dependency>
    <groupId>tech.wetech.metacode</groupId>
    <artifactId>meatacode-json-logic</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

开始使用

```java
    // Boolean Logic Rules
    Map<String, Object> data=new HashMap<>();
    data.put("b",10);
    data.put("c",Collections.singletonMap("cc",20));
    JsonLogicNode logicNode=JsonLogicParser.parse("{\"and\":[{\">\":[{\"var\":[\"a\",3]}, 2]},{\"<\":[1, {\"var\":\"b\"}]},{\"<\":[{\"var\":\"c.cc\"},21]}]}");

    assertTrue(logicNode.evaluator(BooleanLogicEvaluator::new).evaluate(data));

// Sql Logic Rules
//Todo...
```

## Boolean Logic Rules

Example 1

```shell
json: {"or":[{">":[1, 2]},{"<":[1, 2]}]} 
// (1 > 2 or 1 < 2)

data: null

result: false

```

Example 2

```shell
json: {"and":[{"and":[{">=":[3, 5]},{"<=":[1, 2]}]},{"or":[{">":[3, 2]},{"<":[1, 2]}]}]}
 // ((3 >= 5 and 1 <= 2) and (3 > 2) or (1 < 2))

data : null

result: true
```

Example 3

```shell
json: {"and":[{">":[{"var":["a",3]}, 2]},{"<":[1, {"var":"b"}]},{"<":[{"var":"c.cc"},21]}]}
// ((a > 2) and (1 < b)) and c.cc < 21

data: {"b":10,"c":{"cc":20}} //a默认值是3

result: true
```

## Sql Logic Rules

TODO
