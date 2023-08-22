package com.kingsoft.shiyou.omnisdk.demo.java;

import static com.kingsoft.shiyou.omnisdk.demo.common.ExtensionsKt.convert;

import android.app.Activity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.PayNotifier;
import com.kingsoft.shiyou.omnisdk.api.entity.Order;
import com.kingsoft.shiyou.omnisdk.api.entity.Product;
import com.kingsoft.shiyou.omnisdk.api.entity.SkuType;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.manager.PreOrderManager;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;

import kotlin.Pair;
import kotlin.jvm.Volatile;

/**
 * Description: OmniSDK支付API接口代码示例Demo
 *
 * @author: LuXing created on 2021/3/23 13:37
 */
@Keep
public class PayApi implements IPayApi {

    private final String tag = "PayApi# ";

    private final Activity appActivity;
    private final IPayCallback callback;

    private SkuType mSkuType = SkuType.INAPP;
    private String mProductId = "";
    private String mProductName = "";
    private String mProductDesc = "";
    private double mProductPrice = 0.0;
    private double mPayAmount = 0.0;
    private String mCurrency = "";
    private String mServerId = "";
    private String mRoleId = "";
    private String mGameTradeNo = "";
    private String mGameCallbackUrl = "";
    private String mExtJson = "";
    private String mZoneId = "";
    private String mRoleName = "";
    private String mRoleLevel = "";
    private String mRoleVipLevel = "";

    @Volatile
    private Product productInPayProcess;

    public PayApi(Activity activity, IPayCallback payCallback) {
        this.appActivity = activity;
        this.callback = payCallback;
        setPayNotifier();
    }

    /* ********************************** OmniSDK支付接口示例如下 ********************************* */

    /**
     * 游戏对接方在调用支付接口前必须先设置支付结果数据回调监听
     */
    private void setPayNotifier() {

        OmniSDK.getInstance().setPayNotifier(new PayNotifier() {

            @Override
            public void onSuccess(@NonNull Pair<Integer, String> sdkCodeMsg, @NonNull Order order) {
                // 支付成功，游戏对接方必须以OmniSDK服务器端通知游戏服务器端的支付成功数据作为发货依据
                String orderId = order.getOrderId(); // OmniSDK支付唯一订单号
                DemoLogger.i(tag, "pay successfully, orderId = " + orderId);
                callback.onSucceeded(order);

                //（必接）支付完成：跟踪玩家的支付信息，支付成功后，游戏成功发放道具后调用
                if (productInPayProcess != null) {
                    OmniSDK.getInstance().onPayFinish(convert(productInPayProcess, order));
                }
            }

            @Override
            public void onFailure(
                    @NonNull Pair<Integer, String> sdkCodeMsg,
                    @Nullable Pair<Integer, String> channelCodeMsg,
                    @NonNull Order order) {
                // 支付失败，解析数据的时候请注意`channelCodeMsg`有可能为null
                DemoLogger.e(tag, "pay failed: [ " + sdkCodeMsg + " , " + channelCodeMsg + " ]");
                callback.onFailed(new Pair(sdkCodeMsg, channelCodeMsg));
            }

            @Override
            public void onCancel() {
                // 支付取消
                DemoLogger.e(tag, "pay cancelled");
                callback.onCancelled();
            }

        });
    }

    /**
     * OmniSDK支付接口代码示例
     */
    public void pay() {
        SkuType skuType = mSkuType;          // 商品类型（必传数据），普通消耗类型传值`SkuType.INAPP`，订阅类型传值`SkuType.SUBS`
        String productId = mProductId;       // 商品ID（必传数据）
        String productName = mProductName;   // 商品名称（有则传值，无则保持和商品ID一致）
        String productDesc = mProductDesc;   // 商品描述（有则传值，无则保持和商品名称一致）
        double productPrice = mProductPrice; // 商品价格（必传数据，单位为元，比如9.99，0.99等等）
        double payAmount = mPayAmount;       // 支付金额（必传数据，单位为元，比如9.99，0.99等等）
        String currency = mCurrency;         // 价格金额对应的货币单位（必传数据，比如USD,CNY,HKD等等）
        String serverId = mServerId;         // 游戏服务器ID（必传数据，对于没有服务器概念的游戏直接传字符串"0"）
        String roleId = mRoleId;             // 游戏角色账号唯一标示ID（必传数据）
        String gameTradeNo = mGameTradeNo;   // 游戏对接方自身的订单号（有则传值，没有则自己创造一个不可重复的字符串）

        // 游戏对接方接收支付结果的服务器回调地址，有则传值，没有则传空字符串""；
        // 若传空字符串""，则将使用OmniSDK端后台配置的游戏服务器回调地址
        // 正式环境和测试环境 mock 地址不同，需要区分正式环境和测试环境
        String gameCallbackUrl = mGameCallbackUrl;
        // 游戏对接方可自定义的扩展数据（Json字符串数据），SDK服务器将在回调游戏服务器支付结果的时候原样返回；
        // 有则传值，没有则传空字符串""
        String extJson = mExtJson;

        String zoneId = mZoneId;              // 区服Zone ID标示（有则传值，没有则传空字符串""）
        String roleName = mRoleName;          // 游戏角色名称（有则传值，无则保持和角色账号唯一标示ID一致）
        String roleLevel = mRoleLevel;        // 游戏角色等级（有则传值，没有则传空字符串""）
        String roleVipLevel = mRoleVipLevel;  // 游戏角色VIP等级（有则传值，没有则传空字符串""）

        // 创建支付商品数据实体，务必注意所有字符串类型的数据项禁止传`null`值，若无值统一传入空字符串""
        productInPayProcess = new Product(
                skuType,
                productId,
                productName,
                productDesc,
                productPrice,
                payAmount,
                currency,
                serverId,
                roleId,
                gameTradeNo,
                gameCallbackUrl,
                extJson,
                zoneId,
                roleName,
                roleLevel,
                roleVipLevel);

        // 调用支付接口进行支付
        OmniSDK.getInstance().pay(appActivity, productInPayProcess);
    }

    /* ****************************************************************************************** */

    @Override
    public void payImpl(
            @NonNull SkuType skuType,
            @NonNull String productId,
            @NonNull String productName,
            @NonNull String productDesc,
            double productPrice,
            double payAmount,
            @NonNull String currency,
            @NonNull String serverId,
            @NonNull String roleId,
            @NonNull String gameTradeNo,
            @NonNull String gameCallbackUrl,
            @NonNull String extJson,
            @NonNull String zoneId,
            @NonNull String roleName,
            @NonNull String roleLevel,
            @NonNull String roleVipLevel) {
        mSkuType = skuType;
        mProductId = productId;
        mProductName = productName;
        mProductDesc = productDesc;
        mProductPrice = productPrice;
        mPayAmount = payAmount;
        mCurrency = currency;
        mServerId = serverId;
        mRoleId = roleId;
        mGameTradeNo = gameTradeNo;
        mGameCallbackUrl = gameCallbackUrl;
        mExtJson = extJson;
        mZoneId = zoneId;
        mRoleName = roleName;
        mRoleLevel = roleLevel;
        mRoleVipLevel = roleVipLevel;
        pay();
    }

    @Override
    public void preOrderImpl(String serverKey, @NonNull String uid,
                             @NonNull String roleId,
                             @NonNull String productId,
                             int totalAmount,
                             @NonNull String gameTradeNo) {
        PreOrderManager.Companion.getInstance().preOrder(serverKey, uid, roleId, productId, totalAmount, gameTradeNo, callback);
    }
}
