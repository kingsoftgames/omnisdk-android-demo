OmniSDK Android
====

<!-- TOC -->

- [描述](#描述)
- [接入指南文档](#接入指南文档)
- [Demo 说明](#demo-说明)
    - [语言指导](#语言指导)
    - [如何运行](#如何运行)

<!-- /TOC -->

# 描述

金山世游 OmniSDK Android 是一个聚合类型SDK，提供了账号，支付，广告，事件统计等功能模块。游戏CP方完成一次对接后即可在各个应用商店平台和第三方发布渠道进行游戏发布。

本 Demo 源码不做为测试用途，主要方便游戏CP方做接入时的示例。

# 接入指南文档
- [OmniSDK 接入手册](https://kingsoftgames.github.io/sdk-docs/)

# Demo 说明

## 语言指导

- 使用Java语言对接, SDK接口示例代码都在 __com.kingsoft.shiyou.omnisdk.demo.java.*__ 包下，其他代码对接方无需关注。
- 使用Kotlin语言对接, SDK接口示例代码都在 __com.kingsoft.shiyou.omnisdk.demo.kotlin.*__ 包下，其他代码对接方无需关注。

## 如何运行
1. 通过 [Android Studio](https://developer.android.com/studio/intro?hl=zh-cn) 打开 Demo 工程；
2. 请将从 `OmniSDK` 申请到 `shiyou` 文件夹替换 Demo 根目录下 `shiyou` 文件夹；
3. 命令行执行 `gradlew omniPreChannels`，生成两个文件 ***kssyOmniChannels.gradle*** 和 ***kssyOmniChannelsRoot.gradle***；
4. 配置 `app/build.gradle` 下的签名配置节点，将其修改成自己签名信息。
5. 点击IDE **Sync Project with Gradle Files** 按钮，进行依赖同步与初始化。
    - 或 `File > Sync Project with Gradle Files`。
    - 或 `Tools > Android > Sync Project with Gradle Files`。
6. 运行 Demo 。
