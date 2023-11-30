package com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces

import android.app.Activity
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKAdOptions
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKShowAdStatus

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/23 14:23
 *
 */
interface IAdsApi {

    fun preloadAd(
        activity: Activity,
        adsOptions: OmniSDKAdOptions
    )

    fun showAd(activity: Activity, adsOptions: OmniSDKAdOptions)
}

interface IAdsCallback : ICallback {

    fun onPreloadAdSuccess(adId:String)

    fun onShowAdSuccess(status: OmniSDKShowAdStatus,token:String)

    fun onError(error: OmniSDKError)

}