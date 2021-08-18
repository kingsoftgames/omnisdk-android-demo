[<<返回首页](/sdk-docs)

Change log
=====

<!-- TOC -->

    - [链接迁移](#链接迁移)
- [备份记录](#备份记录)
    - [Version 1.0.8](#version-108)
    - [Version 1.0.6](#version-106)
    - [Version 1.0.5](#version-105)
    - [Version 1.0.3](#version-103)
    - [Version 1.0.2](#version-102)
    - [Version 1.0.0](#version-100)

<!-- /TOC -->

## 链接迁移

文档已迁移到：[OmniSDK 升级日志](https://d7n9vj8ces.feishu.cn/docs/doccn9IsNVuJKFHQb1BqbxYHcdd#)

# 备份记录
## Version 1.0.8
_2021-07_02_
- :pushpin: 参数名 loginType 变更为 OmniConstant.accountType 引用；必须替换，否则无法登录。
- 登录类型支持全渠道动态组合。
- 海外渠道上线增至11个。
- :100: 发布 `KSSYOmniPlugin 3`，支持多渠道编译，支持CPS包编译。
- 增加模拟器判断方法
- 优化性能、修复Bug。

## Version 1.0.6
_2021-04_20_
- 登录界面UI可配置是否使用默认提供UI或完全自定义。
- 修复部分Bug。

## Version 1.0.5
_2021-04-05_
- **重要接口**：调整接口的包名结构，接口名不变，请CP升级时注意。
  包名结构调整后，后续CP升级版本无需变动。
- 功能：
  1. 优化社交接口
  2. 增加账号动态配置功能，CP必须更新替换新配置文件
  3. 增加日志模块，用于排查问题
- 渠道：上线其他海外渠道
- :pushpin: 从之前版本升级上来，检查游戏引用的 `Omni-API `包名路径***全部*** 为<font color=red> `com.kingsoft.shiyou.omnisdk.api.*` </font>。

## Version 1.0.3
_2021-03-19_
- 功能：增加参数获取接口

## Version 1.0.2
_2021-03-15_
- 功能：变更用户信息接口为getUserInfo()
- bug：修复登录回调可能报空指针异常

## Version 1.0.0
_2021-03-01_
- 功能：登录，支付，社交功能。
- 渠道：海外GooglePlay商店。