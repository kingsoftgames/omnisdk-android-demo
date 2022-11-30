package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

import android.app.Activity
import android.content.Context

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/23 17:00
 *
 */
interface IOtherApi {

    fun doActionImpl()
    fun openBrowserActivityImpl(url: String, params: String)
    fun openBrowserActivityWithLocalImpl()
    fun checkEmulatorImpl()
    fun showScoreDialogImpl()
    fun openAppPermissionSettingsImpl()

    fun invokeMethodImpl()

    // Sunit茄子渠道功能方法接口
    fun invokeChannelSunitEventMethod(jsonStr: String)
    fun invokeChannelSunitShowRewardedBadgeViewMethod(jsonStr: String)

    // 官网渠道功能方法接口
    fun invokeQuerySkuDetailsList(skusList: List<String>, skuType: Int)
}

interface IOtherCallback : ICallback {
    fun onSucceeded(resultJson: String)
    fun onFailed(responseCode: Pair<Int, String>)
}