package com.kingsoft.shiyou.omnisdk.demo.java;

import android.content.Context;

import com.kingsoft.shiyou.omnisdk.api.OmniApplication;
import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.entity.InitParams;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;

/**
 * Description: 游戏应用自身`Application`类定义。
 * <p>
 * 请游戏对接方创建定义自身应用的`Application`类，并使其继承：`com.kingsoft.shiyou.omnisdk.api.OmniApplication`。
 * 如自身有需要重写`Application`中相应的生命周期方法，并保证调用其对应的父类周期方法。
 * 同时务必在AndroidManifest.xml文件中的<application> tag标签中申明name值为该类（按自身需要加上包名前缀）。示例如下：
 * <application
 * android:name=".java.DemoApplication"
 * ...
 * >
 * ...
 * </application>
 */
public class DemoApplication extends OmniApplication {

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        DemoLogger.d("DemoApplication.attachBaseContext(...) called");

        // 构建OmniSDK初始化数据配置
        InitParams initParams = new InitParams();
        // 正式版本必须设置为`false`
        initParams.setDebug(true);
        // 正式版本必须设置为`Production`
        initParams.setEnvironment("Production");
        // OmniSDK初始化数据配置接口调用
        OmniSDK.getInstance().initialize(initParams);

        // 游戏对接方自己的代码
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DemoLogger.d("DemoApplication.onCreate(...) called");

        // 游戏对接方自己的代码
    }

}