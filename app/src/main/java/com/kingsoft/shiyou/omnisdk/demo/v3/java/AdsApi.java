package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.app.Activity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKAdOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKPreloadAdResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKShowAdResult;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAdsApi;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAdsCallback;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger;

/**
 * Description: OmniSDK账号相关业务API接口代码示例Demo
 *
 * @author: LuXing created on 2021/3/22 16:22
 */
@Keep
public class AdsApi implements IAdsApi {

    private static final String tag = "AdsApi# ";

    private final Activity demoActivity;
    private final IAdsCallback iAdsCallback;

    public AdsApi(Activity activity, IAdsCallback iAdsCallback) {
        this.demoActivity = activity;
        this.iAdsCallback = iAdsCallback;
    }


    @Override
    public void preloadAd(@NonNull Activity activity, @NonNull OmniSDKAdOptions adsOptions) {
        DemoLogger.d(tag, "preloadAd(...) called, adsOptions = " + adsOptions);
        OmniSDKv3.getInstance().preloadAd(activity, adsOptions, result -> {
            if (result.isSuccess()) {
                OmniSDKPreloadAdResult omniSDKPreloadAdResult = result.get();
                iAdsCallback.onPreloadAdSuccess(omniSDKPreloadAdResult.getPlacementId());
            } else {
                iAdsCallback.onError(result.error());
            }
        });
    }

    @Override
    public void showAd(@NonNull Activity activity, @NonNull OmniSDKAdOptions adsOptions) {
        DemoLogger.d(tag, "showAds(...) called, adsOptions = " + adsOptions);
        OmniSDKv3.getInstance().showAd(activity, adsOptions, result -> {
            if (result.isSuccess()) {
                OmniSDKShowAdResult omniSDKShowAdResult = result.get();
                iAdsCallback.onShowAdSuccess(omniSDKShowAdResult.getStatus(),omniSDKShowAdResult.getToken());
            } else {
                iAdsCallback.onError(result.error());
            }
        });
    }

}
