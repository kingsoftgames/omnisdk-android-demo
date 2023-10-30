package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.app.Activity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKErrorCode;
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKLoginInfo;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKProductType;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.events.OmniSDKPurchaseEvent;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKPurchaseOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKPurchaseResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKResult;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IPayApi;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IPayCallback;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger;



/**
 * Description: OmniSDK支付API接口代码示例Demo
 *
 * @author: LuXing created on 2021/3/23 13:37
 */
@Keep
public class PayApi implements IPayApi {

    private static final String tag = "PayApi# ";

    private final Activity appActivity;
    private static IPayCallback callback;

    private static OmniSDKPurchaseOptions mPurchaseOptions;

    public PayApi(Activity activity, IPayCallback payCallback) {
        this.appActivity = activity;
        callback = payCallback;
    }

    public static void OnPurchase(OmniSDKResult<OmniSDKPurchaseResult> result) {
        if (callback == null) {
            return;
        }
        if (result.isSuccess()) {
            String orderId = result.get().getOrderId();
            DemoLogger.i(tag, "pay successfully, orderId = " + orderId);
            if (orderId == null) {
                orderId = "";
            }
            callback.onSucceeded(orderId);
            trackPurchase(orderId);
        } else {
            OmniSDKError error = result.error();
            mPurchaseOptions = null;
            if (error.getCode() == OmniSDKErrorCode.USER_CANCELLED.getValue()) {
                DemoLogger.e(tag, "pay cancelled");
                callback.onCancelled();
            } else {
                callback.onFailed(error);
            }
        }
    }

    /**
     * 上报游戏发货完成事件
     * @param orderId  sdk订单号
     */
    private static void trackPurchase(String orderId) {
        OmniSDKLoginInfo loginInfo = OmniSDKv3.getInstance().getLoginInfo();
        if (mPurchaseOptions!=null && loginInfo!=null){
            OmniSDKPurchaseEvent event = OmniSDKPurchaseEvent.builder()
                    .userId(loginInfo.getUserId())
                    .orderId(orderId)
                    .productId(mPurchaseOptions.getProductId())
                    .productDesc(mPurchaseOptions.getProductDesc())
                    .productUnitPrice(mPurchaseOptions.getProductUnitPrice())
                    .productName(mPurchaseOptions.getProductName())
                    .currency(mPurchaseOptions.getCurrency())
                    .gameServerId(mPurchaseOptions.getGameServerId())
                    .gameRoleVipLevel(mPurchaseOptions.getGameRoleVipLevel())
                    .gameRoleLevel(mPurchaseOptions.getGameRoleLevel())
                    .gameRoleName(mPurchaseOptions.getGameRoleName())
                    .gameRoleId(mPurchaseOptions.getGameRoleId())
                    .gameOrderId(mPurchaseOptions.getGameOrderId())
                    .extJson(mPurchaseOptions.getExtJson())
                    .purchaseQuantity(mPurchaseOptions.getPurchaseQuantity())
                    .purchaseAmount(mPurchaseOptions.getPurchaseAmount())
                    .build();
            OmniSDKv3.getInstance().track(event);
        }
    }

    /* ****************************************************************************************** */

    @Override
    public void payImpl(
            @NonNull OmniSDKProductType skuType,
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
        OmniSDKPurchaseOptions opts = OmniSDKPurchaseOptions.builder()
                .productId(productId)
                .productType(skuType)
                .productName(productName)
                .productUnitPrice(productPrice)
                .purchaseAmount(payAmount)
                .purchaseQuantity(1)
                .purchaseCallbackUrl(gameCallbackUrl)
                .currency(currency)
                .gameOrderId(gameTradeNo)
                .gameRoleId(roleId)
                .gameRoleName(roleName)
                .gameRoleLevel(roleLevel)
                .gameRoleVipLevel(roleVipLevel)
                .gameServerId(serverId)
                .productDesc(productDesc)
                .build();
        OmniSDKv3.getInstance().purchase(appActivity, opts);

        mPurchaseOptions = opts;
    }
}
