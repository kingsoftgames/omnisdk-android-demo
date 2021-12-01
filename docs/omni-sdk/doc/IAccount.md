[<<返回上一页](/docs/omni-sdk/OmniSDK接入指南.md#331-账号)         [返回首页>>](/sdk-docs)

OmniSDK 账号 API
=====

<!-- TOC -->

- [描述](#描述)
    - [账号接入](#账号接入)
    - [自定义UI界面](#自定义ui界面)
    - [静默登录](#静默登录)
- [功能与API接口文档](#功能与api接口文档)

<!-- /TOC -->

# 描述

## 账号接入
- OmniSDK 提供默认账号UI界面，包含完整的账号处理逻辑，可以减少接入成本。目前只提供一套定制UI风格。
- 游戏项目也可以通过【账号API】自定义账号UI界面，以便统一UI风格。
  - 接入的账号类型包含游客类型时，**账号绑定** 逻辑最好实现，防止用户卸载重装后数据丢失。
  - 如有游客账号类型，游戏最好引导玩家主动绑定账号，防止数据丢失。

## 自定义UI界面
- 账号类型使用 [AccountType][AccountType] 传递。
- 渠道账号体系的模式：分为无账号、有账号（禁止其他账号、允许其他账号）。
  - 比如游戏项目需要分发：官方渠道、渠道a（无账号）、渠道b（有账号-禁止其他）、渠道c（有账号-允许其他），默认要有Facebook、Google账号类型。最终UI效果：
    - 【官方渠道】显示Facebook、Google；
    - 【渠道a】显示Facebook、Google；
    - 【渠道b】只显示渠道账号；
    - 【渠道c】显示渠道账号、Facebook、Google。
- 游戏项目自定义账号UI界面时，为一次编写适配不同渠道的账号显示要求，需要通过 [getAccountMode][getAccountMode] 处理不同渠道的账号体系模式。在此过程不需要判断或知道具体是哪个渠道。

## 静默登录
- 用户已登录并且未登出，下次登录时，如果是同一账号类型，将进行静默登录（token有效期内）。
- 与上次登录的账号类型不同时，将是一次新登录。
- 自定义账号UI界面时，建议根据上次用户信息返回的账号类型（可保存），在下次进行登录时显示“上次登录账号”或直接静默登录，提供更好的用户体验。


# 功能与API接口文档 
[Account API Javadoc][IAccount]



[IAccount]:../api/html/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-account/index.html
[getAccountMode]:../api/html/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-account/get-account-mode.html
[AccountType]:../api/html/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.entity/-account-type/index.html