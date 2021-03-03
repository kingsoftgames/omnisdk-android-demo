package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;

/**
 * Description: 若应用已经有自身定义的Application并继承其他的android.app.Application子类，
 * 则需要在其自定义Application的相应方法中添加如下代码:
 *
 * @author: LuXing created on 2021/3/22 17:25
 */
public class DemoApplication extends Application {

    /**
     * CP对接方必须在游戏自定义的Application类的attachBaseContext生命周期方法内调用如下方法
     */
    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 解决方法数超过64K问题
        MultiDex.install(this);

        // CP对接方必须调用该方法
        OmniSDK.getInstance().onApplicationAttachBaseContext(base);

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类的onCreate生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // CP对接方必须调用该方法
        OmniSDK.getInstance().onApplicationCreate(this);

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类中重载onLowMemory方法并在其中调用如下SDK API接口
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // CP对接方必须调用该方法
        OmniSDK.getInstance().onApplicationLowMemory();

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类中重载onTrimMemory方法并在其中调用如下SDK API接口
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        // CP对接方必须调用该方法
        OmniSDK.getInstance().onApplicationTrimMemory();

        // CP对接方自己的代码
    }

    /**
     * CP对接方必须在游戏自定义的Application类的onTerminate生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        // CP对接方必须调用该方法
        OmniSDK.getInstance().onApplicationTerminate();

        // CP对接方自己的代码
    }

}