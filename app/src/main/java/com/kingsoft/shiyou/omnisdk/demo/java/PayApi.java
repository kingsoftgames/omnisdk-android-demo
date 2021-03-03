package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.PayCallback;
import com.kingsoft.shiyou.omnisdk.api.entity.Order;
import com.kingsoft.shiyou.omnisdk.api.entity.Product;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayCallback;

import kotlin.Pair;

/**
 * Description: OMNI SDK支付API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 13:37
 */
@Keep
public class PayApi implements IPayApi {

    /* ******************************** SDK 支付API接口示例如下 *********************************** */

    public void pay() {

        String productId = mProductId;       // 商品ID（必传数据）
        String productName = mProductName;   // 商品名称（有则传值，无则保持和商品ID一致）
        String productDesc = mProductDesc;   // 商品描述（有则传值，无则保持和商品ID一致）
        double productPrice = mProductPrice; // 商品价格（必传数据，单位为元，比如9.99，0.99等等）
        double payAmount = mPayAmount;       // 实际支付总金额（单位为元）
        String currency = mCurrency;         // 价格金额对应的货币单位（必传数据，比如USD，HKD等等）
        String serverId = mServerId;         // 服务器ID（必传数据，对于没有服务器概念的游戏直接传字符串"0"）
        String roleId = mRoleId;             // 游戏角色唯一标示ID（必传数据）
        String gameTradeNo = mGameTradeNo;   // CP方自身的订单号（有则传值，没有则传空字符串""）
        /*
         * CP方服务器支付结果回调地址，有则传值，没有则传空字符串""
         * 若传空字符串""，则将使用SDK端后台配置的CP方服务器回调地址
         */
        String gameCallbackUrl = mGameCallbackUrl;
        /*
         * CP方可自定义的扩展数据，SDK服务器将在回调CP方服务器支付结果的时候原样返回
         * 有则传值，没有则传空字符串""
         */
        String extJson = mExtJson;

        // 创建支付商品数据实体
        Product product = new Product(
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
                extJson);

        // 调用SDK支付接口进行支付
        OmniSDK.getInstance().pay(demoActivity, product, new PayCallback() {
            @Override
            public void onSuccess(Order order, Pair<Pair<Integer, String>, Pair<Integer, String>> pair) {
                /*
                 * 支付成功，CP对接方必须以OMNI SDK服务器端通知CP服务器端的支付成功数据作为发货依据。
                 *
                 */
                // OMNI SDK支付唯一订单号，CP方可按照自身需要使用该值来标示本次支付购买
                String orderId = order.getOrderId();
                Log.i(tag, "pay successfully, orderId = " + orderId);
                callback.onSucceeded(order);
            }

            @Override
            public void onFailure(Order order, Pair<Pair<Integer, String>, Pair<Integer, String>> codeMsg) {
                // 支付失败, CP方可以按照自身需求进行业务处理，比如弹出"提示支付失败"提示等等
                callback.onFailed(codeMsg);
            }

            @Override
            public void onCancel() {
                // 支付取消, 无需特别处理
                callback.onCancelled();
            }
        });

    }

    /* ****************************************************************************************** */

    private final String tag = "SDK: " + this.getClass().getName();
    private final Activity demoActivity;
    private final IPayCallback callback;

    public PayApi(Activity activity, IPayCallback payCallback) {
        this.demoActivity = activity;
        this.callback = payCallback;
    }

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

    @Override
    public void payImpl(
            String productId,
            String productName,
            String productDesc,
            double productPrice,
            double payAmount,
            String currency,
            String serverId,
            String roleId,
            String gameTradeNo,
            String gameCallbackUrl,
            String extJson) {
        mProductId = productId;
        mProductName = productName;
        mProductDesc = productDesc;
        mProductPrice = productPrice;
        mPayAmount = payAmount;
        mCurrency = currency;
        mServerId = serverId;
        mRoleId = roleId;
        mGameTradeNo = (gameTradeNo == null) ? "" : gameTradeNo;
        mGameCallbackUrl = (gameCallbackUrl == null) ? "" : gameCallbackUrl;
        mExtJson = (extJson == null) ? "" : extJson;
        pay();
    }
}
