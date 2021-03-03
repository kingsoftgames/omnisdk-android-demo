package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.kingsoft.shiyou.omnisdk.api.OmniSDK

/**
 * Description: 若游戏应用已经有自身定义的Application并继承其他的android.app.Application子类，
 * 则需要在其自定义Application的相应方法中添加如下代码:
 *
 * @author: LuXing created on 2021/3/22 17:27
 *
 */
class DemoApplication : Application() {

    /**
     * CP对接方必须在游戏自定义的Application类的attachBaseContext生命周期方法内调用如下方法
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // 解决方法数超过64K问题
        MultiDex.install(this)

        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationAttachBaseContext(base)

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类的onCreate生命周期方法内调用如下SDK API接口
     */
    override fun onCreate() {
        super.onCreate()
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationCreate(this)

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类中重载onLowMemory方法并在其中调用如下SDK API接口
     */
    override fun onLowMemory() {
        super.onLowMemory()
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationLowMemory()

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类中重载onTrimMemory方法并在其中调用如下SDK API接口
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationTrimMemory()

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类的onTerminate生命周期方法内调用如下SDK API接口
     */
    override fun onTerminate() {
        super.onTerminate()
        // CP对接方必须调用该方法
        OmniSDK.instance.onApplicationTerminate()

        // CP对接方自己的代码
    }
}