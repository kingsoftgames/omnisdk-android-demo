[<<返回首页](/sdk-docs)

# 服务端接入

## 1. 发放商品（必接）

### 1.1 接口说明

​	SDK 服务器在验证**订单支付成功**后，会通过 **CP 提供的 Http 接口**通知游戏发放商品道具，通知请求中会有订单的详细信息， **CP 方在验证无误后即可发放商品，并回复 ‘success’**。SDK 服务端收到回复后，会将订单状态由**支付成功更新为交易成功**。

* 接口不做平台区分，google、ios 或者其他平台共用一个接口。


* 请在确认订单信息无误后再发放商品。
* 收到 CP 方返回为 ‘**fail**’  或者**未收到返回**时，SDK 服务器会在接下来的 24 小时内每分钟通知 CP 方一次，CP 方请保证单一订单勿重复发放商品。
* CP 无论是以同步还是异步方式处理通知请求，务必保证对于回复了 ‘success’ 的订单，玩家尽量快的收到相应商品。

### 1.2 接口要求
Http Method : **Post**

Request Params :


|    参数名称     | 参数类型 |                           参数描述                           | 是否必传 |
| :-------------: | :------: | :----------------------------------------------------------: | :------: |
|    order_id     |   Int   |              平台订单id，例 : 872282619197394944              |    Yes    |
|     app_id      |   Int   |                    平台 应用 id，例 : 1001                    |   Yes    |
|    app_channel    |  String  |                         平台渠道 id                         |   Yes    |
|       uid       |   Int   |       平台提供给 CP 方 uid ，一般是 8 位数值型，例 : 18734638       |   Yes    |
|   amt   |   Int   |               订单金额，**美分**为单位，例 : 0.99               |   Yes    |
|    goods_id     |  String  |        商品 id ，例 : com.kingsoftgame.xsjtest.iap.tier60        |   Yes    |
| third_order_id | String | CP 订单号 | Yes |
| pay_item |  String  |      CP 传递给 SDK 的透传字段，一般用于 CP 方做订单验证      |   Yes    |
|     zone_id     |  String  |                          游戏提供的服务器 id + 角色 id，范例 : 1_10001                          |   Yes    |
|   order_type    |   Int   | 设备类型<br/>1 : android<br>2 : ios<br/>3 : h5Mobile<br/>999 : 暂未未识别的设备类型 |   Yes    |
|  pay_time  |  String  |                  支付时间，格式 : UNIX 时间戳                  |   Yes    |
|      sign       |  String  |          参数签名，建议 CP 方做签名验证保证接口安全          |   Yes    |

Response Example : 

````java
//成功返回
success
//失败
fail
````

额外说明：

关于 zone_id，pay_item 字段的数据流： CP 游戏客户端 -> 平台SDK -> 平台服务器 -> CP服务器。平台对这 2 个数据只用于存储订单信息，不做额外处理。CP 方可通过这 2 个透传字段做订单信息验证，比如 pay_item 存放游戏验证字符串或者其他信息；

zone_id  ，CP 方请传入正确的角色 id 和 服务器 id，便于后续的数据分析。

------

## 2. 平台登录身份信息验证 （必接）

### 2.1 接口说明：

CP 端某些重要操作需要对用户的平台登录身份状态进行验证，可通过此接口进行验证。

### 2.2 接口要求：

URL : /v2/uout/cp/token/check

Method : **Post**

Request Param :


|  参数名称  | 参数类型 |                          描述                           | 是否必须 |
| :--------: | :------: | :-----------------------------------------------------: | :------: |
|   app_id   |   Int    |                  平台 应用 id，例:1001                  |   Yes    |
|    uid     |  Int  | 平台提供给CP方 uid ，一般是 8 位字符串型，例 : 18734638 |   Yes    |
| channel_id |   Int    |                     渠道 ID                     |   Yes    |
|   token    |  String  | 平台用户 token ，在 SDK 客户端登录后会回传给游戏客户端  |   Yes    |
|    sign    |  String  |     参数签名，签名规则详见“附录规则”     |   Yes    |

http response example :

````json
//通过验证,code = 0 时通过验证
{"code":0,"reason":"success"} 
//未通过验证
{"code":xxx,"reason":"xxxx"} 
````

------


## 附

### 1. 签名实现

#### 1.1 签名计算规则

假设现在某 API 需要 3 个业务参数，分别是 “k1”,"k2","k3",它们的值按顺序对应分别是“v1”,"v2","v3"，接口提供方和调用方协商的密钥为 secretKey 。那么此接口的签名参数 sign 计算方式为：

1. 将请求中所有业务参数 (不包括 sign 和 value 为空的参数 ) 按**字典序升序排列**， "k1=v1&k2=v2&k3=v3"  形式拼接 得到 unsignedWithoutKey。
2. 在第 1 步的得出结果 unsignedWithoutKey 尾部，追加双方约定的 secretKey ,得到 unsigned 。
3. 将第 2 步得到的结果 unsigned 进行 MD5 加密( 32 位小写形式)，得到 sign。

**注意：**

- 第一步中参数拼接时，**去除 value 为空的参数！！**
- 第一步中参数拼接时，使用**实际参数值**【**先进行 urlDecode **】。
- **参数排序，按字典序升序排列**。
- **secretKey **为 SGSDK 平台相关人员提供的**产品集成反馈**中【应用密钥】项。

### 1.2 Java 代码示例 

**建议 sign 方法编写完成后，用此 Demo 进行测试。**

假设某接口双方约定的 `secretKey=480ednmfzssqs8jz `，请求参数 中业务参数包括 caller= 'kingsoftgame'，time='1489460391'，extra=''，msg='test space' , **其中包含 value 为空的参数 extra ，value 带空格的参数 msg**。

该接口的 sign 签名计算方式为：

```java
//第一步
// 参数 extra 的 value 为空，拼接时去除。
// 按 key 字段排序后顺序，[calller,msg,time],
unsignedWithoutKey = "caller=kingsoftgame&msg=test space&time=1489460391";

//第二步
unsigned = unsignedWithoutKey + secretKey ;   
// unsigned = "caller=kingsoftgame&msg=test space&time=1489460391480ednmfzssqs8jz"

//第三步
sign = Md5Utils.encode(unsigned);
//sign="857db83778e1c67172ca2c2e9cca1e55";
```

### 1.3 测试用例 

java 端验 sign 测试用例:

```java
package com.sgxsj.game.docs;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.security.MessageDigest;

public class JavaSignDemo {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String secretKey = "480ednmfzssqs8jz";

    public static void getSign(String sign) {
        Map<String, Object> apiParams = new HashMap<>();
        apiParams.put("caller", "kingsoftgame");
        apiParams.put("msg", "test space");
        apiParams.put("extra", "");
        apiParams.put("time", "1489460391");
        String unSignData = getSignData(apiParams);
        System.out.println("unSignData:" + unSignData);
        System.out.println("MD5String:" + unSignData + secretKey);
        String md5Sign = MD5Encode(unSignData + secretKey);
        System.out.println("MD5Encode:" + md5Sign);
        boolean result = checkSign(md5Sign, sign);
        System.out.println("check sign result:" + result);
    }

    public static String getSignData(Map<String, Object> params) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("sign".equals(key) || "Sign".equals(key) || "sign_type".equals(key)) {
                continue;
            }
            String value = String.valueOf(params.get(key));
            if (Objects.isNull(value) || value.isEmpty()) {
                continue;
            }
            if (i != 0) {
                content.append("&");
            }
            content.append(key).append("=").append(value);
        }
        return content.toString();
    }

    public static boolean checkSign(String md5Sign, String requestSign) {
        if(!requestSign.equals(md5Sign)){
            return false;
        } else {
            return true;
        }
    }

    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception ex) {

        }
        return resultString;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static void main(String[] args) {
        String sign = "857db83778e1c67172ca2c2e9cca1e55";
        getSign(sign);
    }
}
```

[<<返回首页](/sdk-docs)