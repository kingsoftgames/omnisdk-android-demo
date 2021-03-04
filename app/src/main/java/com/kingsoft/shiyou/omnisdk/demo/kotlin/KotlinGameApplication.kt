package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.kingsoft.shiyou.omnisdk.api.OmniSDK

/**
 * 若游戏应用无法继承OMNI SDK提供的Application类 `com.kingsoft.shiyou.omnisdk.project.OmniApplication`
 * 则CP对接方必须在其自定义的Application类中（假设如下`KotlinGameApplication`类为游戏应用自定义的Application类）
 * 的相应方法内调用SDK API接口.
 */
class KotlinGameApplication : Application() {

    /**
     * CP对接方必须在游戏自定义的Application类的attachBaseContext生命周期方法内调用如下方法
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 解决方法数超过64K问题
        MultiDex.install(this)

        // CP方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类的onCreate生命周期方法内调用如下SDK API接口
     */
    override fun onCreate() {
        super.onCreate()
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationCreate(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类中重载onLowMemory方法并在其中调用如下SDK API接口
     */
    override fun onLowMemory() {
        super.onLowMemory()
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationLowMemory()

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类中重载onTrimMemory方法并在其中调用如下SDK API接口
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationTrimMemory()

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类的onTerminate生命周期方法内调用如下SDK API接口
     */
    override fun onTerminate() {
        super.onTerminate()
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationTerminate()

        // CP自己的代码
    }
}