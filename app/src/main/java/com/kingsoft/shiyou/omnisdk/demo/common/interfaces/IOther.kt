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

    fun showScoreDialog()
    fun webJsImpl()

    // Sunit茄子渠道功能方法接口
    fun invokeOnSunitEventMethod(context: Context, jsonStr: String)
    fun invokeShowRewardedBadgeViewMethod(jsonStr: String)

    // Transsion传音渠道功能方法接口
    fun invokeShowFloatAdMethod(activity: Activity)
    fun invokeCloseFloatAdMethod(activity: Activity)
}

interface IOtherCallback : ICallback {
    fun onSucceeded(resultJson: String)
    fun onFailed(responseCode: Pair<Int, String>)
}