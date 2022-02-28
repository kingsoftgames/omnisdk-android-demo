package com.kingsoft.shiyou.omnisdk.api;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;

/**
 * ### OmniApplication
 *
 * @since 1.0
 */
public class OmniApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 现在应用方法数基本都超过64K，即使少部分不超过，也不影响
        MultiDex.install(this);
        OmniSDK.getInstance().onApplicationAttachBaseContext(base);
    }

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
        OmniSDK.getInstance().onApplicationTerminate();
    }
}
