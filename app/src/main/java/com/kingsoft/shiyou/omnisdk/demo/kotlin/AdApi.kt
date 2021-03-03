package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Activity
import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.Keep
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.AdLoadCallback
import com.kingsoft.shiyou.omnisdk.api.callback.AdShowCallback
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAdApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAdCallback

/**
 * Description:  OMNI SDK广告功能相关API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 15:47
 *
 */
@Keep
class AdApi(private val demoActivity: Activity, private val callback: IAdCallback) : IAdApi {

    /* ******************************** SDK 广告功能接口示例如下 ********************************** */

    /**
     * 广告加载事件回调，CP对接方需按照自身需求进行回调实现并处理相关事件
     */
    private val adLoadCallback = object : AdLoadCallback {
        override fun onLoadSucceed(adId: String, adSource: String) {
            // 广告加载成功
            callback.showResult("加载成功(${adId}:${adSource})")
        }

        override fun onLoadFailed(adId: String, responseCode: Pair<Int, String>) {
            // 广告加载失败
            callback.showResult("加载失败:${responseCode}")
        }
    }

    /**
     * 广告显示事件回调，CP对接方需按照自身需求进行回调实现并处理相关事件
     */
    private val adShowCallback = object : AdShowCallback {
        override fun onShownSucceed(adId: String, adSource: String) {
            // 广告显示成功
            callback.showResult("广告(${adId},${adSource}) 显示成功")
        }

        override fun onShowFailed(adId: String, responseCode: Pair<Int, String>) {
            // 广告显示失败
            callback.showResult("广告(${adId}) 显示失败:$responseCode")
        }

        override fun onClicked(adId: String, adSource: String) {
            // 广告被点击
            callback.showResult("广告(${adId},${adSource}) 被点击")
        }

        override fun onClosed(adId: String, adSource: String) {
            // 广告关闭
            callback.showResult("广告(${adId},${adSource}) 关闭")
        }

        override fun onHaveRewarded(adId: String, adSource: String, type: String) {
            // 广告显示成功并观看完毕,可发奖励
            callback.showResult("广告(${adId},${adSource},${type}) 显示成功并观看完毕,可发奖励")
        }

        override fun onLeaveActivity(adId: String, adSource: String) {
            callback.showResult("广告(${adId},${adSource}) onLeaveActivity")
        }

    }

    /**
     * 加载横幅广告接口示例
     *
     * @param adId 广告id
     */
    private fun loadBannerAd(adId: String) {
        OmniSDK.instance.loadBannerAd(demoActivity, adId, adLoadCallback)
    }

    /**
     * 展示横幅广告接口示例
     *
     * @param adId 广告id
     * @param gravity 支持 [Gravity.BOTTOM] or [Gravity.TOP]
     * @param container 广告容器，如果为null 时，默认使用 SDK 创建的容器
     */
    private fun showBannerAd(adId: String, gravity: Int, container: ViewGroup?) {
        OmniSDK.instance.showBannerAd(
            demoActivity,
            adId,
            gravity,
            container,
            adShowCallback
        )
    }

    /**
     * 隐藏横幅广告接口示例
     *
     * @param adId 广告id
     */
    private fun hideBannerAd(adId: String) {
        OmniSDK.instance.hideBannerAd(demoActivity, adId)
    }

    /**
     * 横幅广告是否准备好（即加载状态）接口示例
     *
     * @param objects 渠道规定的参数，例: ["adId", "home"]
     * @return isReady
     */
    private fun isBannerAdReady(vararg objects: Any): Boolean {
        return OmniSDK.instance.isBannerAdReady(objects)
    }

    /**
     * 加载插屏广告接口示例
     *
     * @param adId 广告id
     */
    private fun loadInterstitialAd(adId: String) {
        OmniSDK.instance.loadInterstitialAd(demoActivity, adId, adLoadCallback)
    }

    /**
     * 展示插屏广告接口示例
     *
     * @param adId 广告id
     */
    private fun showInterstitialAd(adId: String) {
        OmniSDK.instance.showInterstitialAd(demoActivity, adId, adShowCallback)
    }

    /**
     * 插屏广告是否准备好（即加载状态）接口示例
     *
     * 部分渠道，load#ed 无上层回调时使用
     *
     * @param objects 渠道规定的参数，例: ["adId", "home"]
     * @return isReady
     */
    private fun isInterstitialAdReady(vararg objects: Any): Boolean {
        return OmniSDK.instance.isInterstitialAdReady(objects)
    }

    /**
     * 加载激励视频广告接口示例
     *
     * @param adId 广告id
     */
    private fun loadRewardAd(adId: String) {
        OmniSDK.instance.loadRewardAd(demoActivity, adId, adLoadCallback)
    }

    /**
     * 展示激励视频广告接口示例
     *
     * @param adId 广告id
     */
    private fun showRewardAd(adId: String) {
        OmniSDK.instance.showRewardAd(demoActivity, adId, adShowCallback)
    }

    /**
     * 激励视频广告是否准备好（即加载状态）接口示例
     *
     * 部分渠道，load#ed 无上层回调时使用
     *
     * @param objects 渠道规定的参数，例: ["adId", "home"]
     * @return isReady
     */
    private fun isRewardAdReady(vararg objects: Any): Boolean {
        return OmniSDK.instance.isRewardAdReady(objects)
    }

    /* ****************************************************************************************** */

    override fun loadBannerAdImpl(adId: String) {
        loadBannerAd(adId)
    }

    override fun showBannerAdImpl(adId: String, gravity: Int, container: ViewGroup?) {
        showBannerAd(adId, gravity, container)
    }

    override fun hideBannerAdImpl(adId: String) {
        hideBannerAd(adId)
    }

    override fun isBannerAdReadyImpl(vararg objects: Any): Boolean {
        return isBannerAdReady(objects)
    }

    override fun loadInterstitialAdImpl(adId: String) {
        loadInterstitialAd(adId)
    }

    override fun showInterstitialAdImpl(adId: String) {
        showInterstitialAd(adId)
    }

    override fun isInterstitialAdReadyImpl(vararg objects: Any): Boolean {
        return isInterstitialAdReady(objects)
    }

    override fun loadRewardAdImpl(adId: String) {
        loadRewardAd(adId)
    }

    override fun showRewardAdImpl(adId: String) {
        showRewardAd(adId)
    }

    override fun isRewardAdReadyImpl(vararg objects: Any): Boolean {
        return isRewardAdReady(objects)
    }

}