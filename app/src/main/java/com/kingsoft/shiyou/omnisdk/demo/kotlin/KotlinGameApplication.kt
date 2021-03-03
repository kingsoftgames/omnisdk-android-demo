package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Application
import com.kingsoft.shiyou.omnisdk.api.OmniSDK

/**
 * @author tangxiyong
 */
class KotlinGameApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        OmniSDK.instance.onApplicationCreate(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        OmniSDK.instance.onApplicationLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        OmniSDK.instance.onApplicationTrimMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
        OmniSDK.instance.onApplicationTrimMemory()
    }
}