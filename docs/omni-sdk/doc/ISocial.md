[<<返回上一页](/sdk-docs/docs/omni-sdk/OmniSDK接入指南.md#333-社交)         [返回首页>>](/sdk-docs)

OmniSDK 社交 API
=====
- [x] **可选功能**

<!-- TOC -->

- [描述](#描述)
    - [分享](#分享)
    - [社交信息](#社交信息)
    - [功能与权限申请](#功能与权限申请)
- [功能与API接口文档](#功能与api接口文档)

<!-- /TOC -->

# 描述

## 分享
- OmniSDK 不申请权限，有图片分享时需要游戏提前申请存储权限，否则无法分享。
- 分享APP（比如Facebook）需要申请存储权限、相册权限（有些品牌厂商），游戏测试时请提前确认。
- 部分平台需要申请参数，或对应权限。

## 社交信息
- 社交信息获取需要使用对应账号类型登录；
- 比如要获取 Facebook 好友信息，则需要用 Facebook 进行登录后才能获取，并向 Facebook 申请相关参数、对应权限。

## 功能与权限申请
[OmniSDK 社交 API 平台功能与权限申请](https://d7n9vj8ces.feishu.cn/docs/doccnpgXVFBrTQ2D9BZNIOqCpvd)

# 功能与API接口文档
[Social API Javadoc][ISocial]



[ISocial]:./api/html/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-social/index.html