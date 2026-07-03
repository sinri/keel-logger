# 5.0.3 变更说明

5.0.3 版本面向 keel-logger-api 5.0.3，并补充 SLF4J 桥接实现的异常保留行为。

## 构建与依赖

- 项目版本推进到 `5.0.3-SNAPSHOT`，发布正式版前应切换为 `5.0.3`。
- `keel-logger-api` 依赖更新到 `5.0.3`。
- 测试依赖 Vert.x 更新到 `5.1.3`。

## 修复与改进

- 修复 SLF4J 格式化日志调用中最后一个 `Throwable` 参数未写入 Keel `Log.exception()` 的问题。
- 更新 README 与文档首页中的当前版本信息，避免发布说明继续指向旧版本。

## 发布前检查

- 执行 `./gradlew test`。
- 执行 `./gradlew build`，确认编译、测试、Javadoc、sourcesJar 与 javadocJar 均通过。
