package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

import android.view.ViewGroup

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/23 15:42
 *
 */
interface IAdApi {

    fun loadBannerAdImpl(adId: String)

    fun showBannerAdImpl(
        adId: String,
        gravity: Int,
        container: ViewGroup?
    )

    fun hideBannerAdImpl(adId: String)

    fun isBannerAdReadyImpl(vararg objects: Any): Boolean

    fun loadInterstitialAdImpl(adId: String)

    fun showInterstitialAdImpl(adId: String)

    fun isInterstitialAdReadyImpl(vararg objects: Any): Boolean

    fun loadRewardAdImpl(adId: String)

    fun showRewardAdImpl(adId: String)

    fun isRewardAdReadyImpl(vararg objects: Any): Boolean

}

interface IAdCallback : ICallback {
    fun showResult(result: String)
}