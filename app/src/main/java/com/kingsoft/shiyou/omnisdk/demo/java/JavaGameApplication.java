package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Application;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;

/**
 * @author tangxiyong
 */
public class JavaGameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OmniSDK.getInstance().onApplicationCreate(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        OmniSDK.getInstance().onApplicationLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        OmniSDK.getInstance().onApplicationTrimMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        OmniSDK.getInstance().onApplicationTrimMemory();
    }
}
