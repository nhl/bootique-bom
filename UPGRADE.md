# UPGRADE INSTRUCTIONS FOR MODULES

## 0.12.2

* [bootique-cayenne #9](https://github.com/nhl/bootique-cayenne/issues/9) : CayenneModule ```noConfig``` and ```configName``` methods are moved into a builder, and constructor is made private. Now to set cayenne-...xml via API use something like this:

```java
CayenneModule.builder()./* configure module */.build();
```
* [bootique-jdbc #4](https://github.com/nhl/bootique-jdbc/issues/4) : Instrumentation is removed from ```bootique-jdbc``` and into a separate ```bootique-jdbc-instrumented``` module.If you relied on the DataSource metrics, change your import to the new Module.