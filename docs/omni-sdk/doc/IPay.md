[<<返回上一页](/sdk-docs/docs/omni-sdk/OmniSDK接入指南.md#332-支付)         [返回首页>>](/sdk-docs)

OmniSDK 支付 API
=====

<!-- TOC -->

- [支付接入](#支付接入)
- [商品信息本地化](#商品信息本地化)
- [功能与API接口文档](#功能与api接口文档)

<!-- /TOC -->


# 支付接入
- 调用支付接口前，用户需要先登录账号。
    - **游戏项目无账号功能时**，游戏需要提前调用登录接口，通过游客类型账号进行静默注册登录后，再进行后续的支付操作。
- 支付接口的调用，OmniSDK 做了快速点击间隔处理（800ms）；但还是建议游戏增加不可点击的遮罩层，确保一次流程结束再启动下次流程。  
- 商品参数传递完整（商品参数是 OmniSDK 根据所有渠道要求综合定义的）。

# 商品信息本地化
- GooglePlay 及部分渠道提供商品信息本地化功能（通常包含货币本地化，具体以渠道返回数据为主）。
- 游戏可以通过 [querySkuDetailsList][querySkuDetailsList] 接口查询，**渠道不支持时无响应**。建议调用前判断当前渠道是否支持此功能。
  ```java
  OmniSDK.getInstance().invokeMethod(OmniConstant.invokeQuerySkuDetailsList, skusList, SkuType.INAPP, new ResultCallback() {
      @Override
      public void onSuccess(@NonNull String resultJson) {
          // 游戏处理回调结果
      }

      @Override
      public void onFailure(@NonNull Pair<Integer, String> responseCode) {
          // 游戏处理回调结果
      }
  });
  ```

# 功能与API接口文档 
[Pay API Javadoc][IPay]



[IPay]:../api/html/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-pay/index.html
[querySkuDetailsList]:../api/html/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/[android-jvm]-i-channel-invoke-method/query-sku-details-list.html
