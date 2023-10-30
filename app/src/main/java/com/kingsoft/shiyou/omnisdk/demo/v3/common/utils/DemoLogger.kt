package com.kingsoft.shiyou.omnisdk.demo.v3.common.utils

import android.util.Log

/**
 * Description:
 *
 * @author: LuXing created on 2022/1/18 15:04
 *
 */
object DemoLogger {

    private const val TAG = "OmniSDKDemo"

    @JvmStatic
    fun i(message: String) {
        Log.i(TAG, message)
    }

    @JvmStatic
    fun i(tag: String, message: String) {
        Log.i("${TAG}:$tag", message)
    }

    @JvmStatic
    fun d(message: String) {
        Log.d(TAG, message)
    }

    @JvmStatic
    fun d(tag: String, message: String) {
        Log.d("${TAG}:$tag", message)
    }

    @JvmStatic
    fun e(message: String) {
        Log.e(TAG, message)
    }

    @JvmStatic
    fun e(tag: String, message: String) {
        Log.e("${TAG}:$tag", message)
    }
}