package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.AdLoadCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.AdShowCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAdApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAdCallback;

import kotlin.Pair;

/**
 * Description: OMNI SDK广告功能相关API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 16:27
 */
@Keep
public class AdApi implements IAdApi {

    /* ******************************** SDK 广告功能接口示例如下 ********************************** */

    /**
     * 广告加载事件回调，CP对接方需按照自身需求进行回调实现并处理相关事件
     */
    AdLoadCallback adLoadCallback = new AdLoadCallback() {
        @Override
        public void onLoadSucceed(String adId, String adSource) {
            // 广告加载成功
            callback.showResult("加载成功: " + adId + ":" + adSource);
        }

        @Override
        public void onLoadFailed(String adId, Pair<Integer, String> responseCode) {
            // 广告加载失败
            callback.showResult("加载失败: " + responseCode.toString());
        }
    };

    /**
     * 广告显示事件回调，CP对接方需按照自身需求进行回调实现并处理相关事件
     */
    AdShowCallback adShowCallback = new AdShowCallback() {
        @Override
        public void onShownSucceed(String adId, String adSource) {
            // 广告显示成功
            callback.showResult("广告(" + adId + " , " + adSource + ") 显示成功");
        }

        @Override
        public void onShowFailed(String adId, Pair<Integer, String> responseCode) {
            // 广告显示失败
            callback.showResult("广告(" + adId + ") 显示失败:" + responseCode);
        }

        @Override
        public void onClicked(String adId, String adSource) {
            // 广告被点击
            callback.showResult("广告(" + adId + " , " + adSource + ") 被点击");
        }

        @Override
        public void onClosed(String adId, String adSource) {
            // 广告关闭
            callback.showResult("广告(" + adId + " , " + adSource + ") 关闭");
        }

        @Override
        public void onHaveRewarded(String adId, String adSource, String type) {
            // 广告显示成功并观看完毕,可发奖励
            callback.showResult("广告(" + adId + "," + adSource + "," + type + ") 显示成功并观看完毕,可发奖励");
        }

        @Override
        public void onLeaveActivity(String adId, String adSource) {
            callback.showResult("广告(" + adId + " , " + adSource + ") onLeaveActivity");
        }
    };

    /**
     * 加载横幅广告接口示例
     *
     * @param adId 广告id
     */
    public void loadBannerAd(String adId) {
        OmniSDK.getInstance().loadBannerAd(demoActivity, adId, adLoadCallback);
    }

    /**
     * 展示横幅广告接口示例
     *
     * @param adId      广告id
     * @param gravity   支持 [Gravity.BOTTOM] or [Gravity.TOP]
     * @param container 广告容器，如果为null 时，默认使用 SDK 创建的容器
     */
    public void showBannerAd(String adId, int gravity, ViewGroup container) {
        OmniSDK.getInstance().showBannerAd(
                demoActivity,
                adId,
                gravity,
                container,
                adShowCallback
        );
    }

    /**
     * 隐藏横幅广告接口示例
     *
     * @param adId 广告id
     */
    public void hideBannerAd(String adId) {
        OmniSDK.getInstance().hideBannerAd(demoActivity, adId);
    }

    /**
     * 横幅广告是否准备好（即加载状态）接口示例
     *
     * @param objects 渠道规定的参数，例: ["adId", "home"]
     * @return isReady
     */
    public boolean isBannerAdReady(Object... objects) {
        return OmniSDK.getInstance().isBannerAdReady(objects);
    }

    /**
     * 加载插屏广告接口示例
     *
     * @param adId 广告id
     */
    public void loadInterstitialAd(String adId) {
        OmniSDK.getInstance().loadInterstitialAd(demoActivity, adId, adLoadCallback);
    }

    /**
     * 展示插屏广告接口示例
     *
     * @param adId 广告id
     */
    public void showInterstitialAd(String adId) {
        OmniSDK.getInstance().showInterstitialAd(demoActivity, adId, adShowCallback);
    }

    /**
     * 插屏广告是否准备好（即加载状态）接口示例
     * <p>
     * 部分渠道，load#ed 无上层回调时使用
     *
     * @param objects 渠道规定的参数，例: ["adId", "home"]
     * @return isReady
     */
    public boolean isInterstitialAdReady(Object... objects) {
        return OmniSDK.getInstance().isInterstitialAdReady(objects);
    }

    /**
     * 加载激励视频广告接口示例
     *
     * @param adId 广告id
     */
    public void loadRewardAd(String adId) {
        OmniSDK.getInstance().loadRewardAd(demoActivity, adId, adLoadCallback);
    }

    /**
     * 展示激励视频广告接口示例
     *
     * @param adId 广告id
     */
    public void showRewardAd(String adId) {
        OmniSDK.getInstance().showRewardAd(demoActivity, adId, adShowCallback);
    }

    /**
     * 激励视频广告是否准备好（即加载状态）接口示例
     * <p>
     * 部分渠道，load#ed 无上层回调时使用
     *
     * @param objects 渠道规定的参数，例: ["adId", "home"]
     * @return isReady
     */
    public boolean isRewardAdReady(Object... objects) {
        return OmniSDK.getInstance().isRewardAdReady(objects);
    }

    /* ****************************************************************************************** */

    private final Activity demoActivity;
    private final IAdCallback callback;

    public AdApi(Activity activity, IAdCallback adCallback) {
        this.demoActivity = activity;
        this.callback = adCallback;
    }

    @Override
    public void loadBannerAdImpl(String adId) {
        loadBannerAd(adId);
    }

    @Override
    public void showBannerAdImpl(String adId, int gravity, ViewGroup container) {
        showBannerAd(adId, gravity, container);
    }

    @Override
    public void hideBannerAdImpl(String adId) {
        hideBannerAd(adId);
    }

    @Override
    public boolean isBannerAdReadyImpl(Object... objects) {
        return isBannerAdReady(objects);
    }

    @Override
    public void loadInterstitialAdImpl(String adId) {
        loadInterstitialAd(adId);
    }

    @Override
    public void showInterstitialAdImpl(String adId) {
        showInterstitialAd(adId);
    }

    @Override
    public boolean isInterstitialAdReadyImpl(Object... objects) {
        return isInterstitialAdReady(objects);
    }

    @Override
    public void loadRewardAdImpl(String adId) {
        loadRewardAd(adId);
    }

    @Override
    public void showRewardAdImpl(String adId) {
        showRewardAd(adId);
    }

    @Override
    public boolean isRewardAdReadyImpl(Object... objects) {
        return isRewardAdReady(objects);
    }
}