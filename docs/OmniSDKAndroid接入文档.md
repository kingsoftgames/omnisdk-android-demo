OmniSDK Android 接入指南
====

<!-- TOC -->

- [简介](#简介)
- [版本历史](#版本历史)
- [SDK获取](#sdk获取)
- [对接须知](#对接须知)
- [集成开发配置](#集成开发配置)
    - [1. 拷贝Gradle文件和集成参数配置文件](#1-拷贝gradle文件和集成参数配置文件)
    - [2. 配置Gradle脚本](#2-配置gradle脚本)
    - [3. 初始化](#3-初始化)
        - [1. Application（必接）](#1-application必接)
        - [2. Activity生命周期方法接入 (必接)](#2-activity生命周期方法接入-必接)
    - [5. API接口说明（可选功能）](#5-api接口说明可选功能)
        - [1. 账号](#1-账号)
        - [2. 支付](#2-支付)
        - [3. 社交](#3-社交)
        - [4. 特定功能](#4-特定功能)
        - [5. 通用方法](#5-通用方法)

<!-- /TOC -->

# 简介
金山世游 OmniSDK Android 是一个聚合类型SDK，提供了账号，支付，广告，事件统计等功能模块。游戏CP方完成一次对接后即可在各个应用商店平台和第三方发布渠道进行游戏发布。

# 版本历史
- **V1.0.0** (2021/03)
1. OmniSDK首个版本。
2. 功能：登录，支付，社交功能。
3. 渠道：海外GoogePlay商店。


# SDK获取
1. 请CP对接方联系我方游戏项目负责人获取最新版本SDK
2. [OmniSDK-Demo](https://github.com/kingsoftgames/omnisdk-android-demo)
# 对接须知
- 推荐使用Android Studio对接SDK
- OmniSDK最低兼容版本为Android 4.4(API Level 19)

# 集成开发配置

## 1. 拷贝Gradle文件和集成参数配置文件
* 将SDK ZIP解压后的 **kssyOmni.gradle** 和 **kssyOmniRoot.gradle** 文件拷贝到游戏自身应用模块根目录下。
* 将SDK ZIP解压后的 **project_config.json** 拷贝到游戏应用模块(app-level or libs-level)的 **/src/main/assets/shiyou/** 目录下
   
## 2. 配置Gradle脚本
* 在游戏项目工程(root-level)根目录下的 ***build.gradle*** ，添加如下配置:
    ```groovy
   apply from: ("${rootProject.rootDir}/kssyOmniRoot.gradle")
    ```
* 在游戏应用模块(app-level or libs-level)根目录下的 ***build.gradle*** ，添加如下配置:
    ```groovy
    apply from: ("${rootProject.rootDir}/kssyOmni.gradle")
    ```
## 3. 初始化
#### 1. Application（必接）
* 若游戏应用无自定义的Application，则在游戏应用工程 **AndroidManifest.xml** 配置文件的  **&lt;application&gt;  tag** 中声明如下 ***android:name*** 的值:
    ```xml
    <application
        android:name="com.kingsoft.shiyou.omnisdk.project.OmniApplication"
        ...
    />
    ```

* 若游戏应用已经有自身定义的Application并继承自android.app.Application,则请将继承类改为 **com.kingsoft.shiyou.omnisdk.project.OmniApplication** 即可,如下所示:
    ```java
    import com.kingsoft.shiyou.omnisdk.project.OmniApplication;

    public class GameApplication extends OmniApplication {
        //...
    }   
    ```
   
* 若游戏应用已经有自身定义的Application并继承其他的android.app.Application子类，则需要在其自定义Application的相应方法中添加如下代码:
    ```java
    @Override
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(context); // 64k方法数
        // your code goes here
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OmniSDK.getInstance().onApplicationCreate(this);
        // your code goes here
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        OmniSDK.getInstance().onApplicationLowMemory();
        // your code goes here
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        OmniSDK.getInstance().onApplicationTrimMemory();
        // your code goes here
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        OmniSDK.getInstance().onApplicationTerminate();
        // your code goes here
    }
    ```
#### 2. Activity生命周期方法接入 (必接)
* 请在游戏自身Activity的相应生命周期方法中添加如下代码:
    ```java

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OmniSDK.getInstance().onCreate(this, savedInstanceState);
        // your code goes here
    }

    @Override
    public void onStart() {
        super.onStart();
        OmniSDK.getInstance().onStart(this);
        // your code goes here
    }

    @Override
    public void onRestart() {
        super.onRestart();
        OmniSDK.getInstance().onRestart(this);
        // your code goes here
    }

    @Override
    public void onResume() {
        super.onResume();
        OmniSDK.getInstance().onResume(this);
        // your code goes here
    }

    @Override
    public void onPause() {
        super.onPause();
        OmniSDK.getInstance().onPause(this);
        // your code goes here
    }

    @Override
    public void onStop() {
        super.onStop();
        OmniSDK.getInstance().onStop(this);
        // your code goes here
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OmniSDK.getInstance().onDestroy(this);
        // your code goes here
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        OmniSDK.getInstance().onNewIntent(this, intent);
        // your code goes here
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        OmniSDK.getInstance().onSaveInstanceState(this, outState);
        // your code goes here
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        OmniSDK.getInstance().onRestoreInstanceState(this, savedInstanceState);
        // your code goes here
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        OmniSDK.getInstance().onConfigurationChanged(this, newConfig);
        // your code goes here
    }

    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OmniSDK.getInstance().onActivityResult(
                this,
                requestCode,
                resultCode,
                data);
        // your code goes here
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OmniSDK.getInstance().onRequestPermissionsResult(
                this, requestCode,
                permissions,
                grantResults);
        // your code goes here
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OmniSDK.getInstance().onBackPressed(this);
        // your code goes here
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (OmniSDK.getInstance().onKeyDown(this, keyCode, event)) {
            return true;
        }
        // your code goes here
        return super.onKeyDown(keyCode, event);
    }
    ```

## 5. API接口说明（可选功能）
**注意:** 由于各个对接游戏需求不同，下面所有接口并不是都必须接入.请CP对接方务必先确定游戏对接需求然后集成所需接口API。  

#### 1. 账号
详情阅读API接口文档-[`IAccount`](./api/javadoc/com/kingsoft/shiyou/omnisdk/api/interfaces/IAccount.html)
#### 2. 支付
详情阅读API接口文档-[`IPay`](./api/html-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-pay/index.html)
#### 3. 社交
详情阅读API接口文档-[`ISocial`](./api/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-social/index.html)
#### 4. 特定功能
详情阅读API接口文档-[`IAction`](./api/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-action/index.html)
#### 5. 通用方法
详情阅读API接口文档-[`IMethod`](./api/-omni-s-d-k/com.kingsoft.shiyou.omnisdk.api.interfaces/-i-method/index.html)