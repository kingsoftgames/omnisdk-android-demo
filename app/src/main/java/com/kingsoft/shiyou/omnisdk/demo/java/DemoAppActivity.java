package com.kingsoft.shiyou.omnisdk.demo.java;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager;
import com.kingsoft.shiyou.omnisdk.demo.common.view.AppView;

/**
 * Description: 应用主Activity
 *
 * @author: LuXing created on 2021/3/15 9:30
 */
public class DemoAppActivity extends AppCompatActivity {

    private AppView appView;

    /**
     * CP对接方必须在游戏Activity的onCreate生命周期方法内调用如下SDK API接口
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiManager.getInstance().initialize(this, ApiManager.Language.JAVA);
        appView = new AppView(this);

        // SDK API接口(必须调用)
        OmniSDK.getInstance().onCreate(this, savedInstanceState);

        // CP自己的代码

    }

    /**
     * CP对接方必须在游戏Activity的onStart生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onStart() {
        super.onStart();

        // SDK API(必须调用)
        OmniSDK.getInstance().onStart(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onRestart生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onRestart() {
        super.onRestart();

        // SDK API(必须调用)
        OmniSDK.getInstance().onRestart(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onResume生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onResume() {
        super.onResume();

        // SDK API(必须调用)
        OmniSDK.getInstance().onResume(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onPause生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onPause() {
        super.onPause();

        // SDK API(必须调用)
        OmniSDK.getInstance().onPause(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onStop生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onStop() {
        super.onStop();

        // SDK API(必须调用)
        OmniSDK.getInstance().onStop(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onDestroy生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        // SDK API(必须调用)
        OmniSDK.getInstance().onDestroy(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onNewIntent生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // SDK API(必须调用)
        OmniSDK.getInstance().onNewIntent(this, intent);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onSaveInstanceState方法并在其中调用如下SDK API接口
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // SDK API(必须调用)
        OmniSDK.getInstance().onSaveInstanceState(this, outState);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onRestoreInstanceState方法并在其中调用如下SDK API接口
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // SDK API(必须调用)
        OmniSDK.getInstance().onRestoreInstanceState(this, savedInstanceState);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onConfigurationChanged方法并在其中调用如下SDK API接口
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // SDK API(必须调用)
        OmniSDK.getInstance().onConfigurationChanged(this, newConfig);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onActivityResult方法并在其中调用如下SDK API接口
     */
    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // SDK API(必须调用)
        OmniSDK.getInstance().onActivityResult(
                this,
                requestCode,
                resultCode,
                data);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onRequestPermissionsResult方法并在其中调用如下SDK API接口
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // SDK API(必须调用)
        OmniSDK.getInstance().onRequestPermissionsResult(
                this, requestCode,
                permissions,
                grantResults);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onBackPressed方法并在其中调用如下SDK API接口
     */
    @Override
    public void onBackPressed() {
        // SDK API(必须调用)
        OmniSDK.getInstance().onBackPressed(this);

        // CP自己的代码

        super.onBackPressed();
    }

    /**
     * CP对接方必须在游戏Activity中重载onKeyDown方法并在其中调用如下SDK API接口
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 拦截返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            OmniSDK.getInstance().onExit(this, () -> {
                finish(); // 杀掉UI
                System.exit(0); // 杀掉进程
            }, false);
            return true;
        }

        // SDK API(必须调用)
        if (OmniSDK.getInstance().onKeyDown(this, keyCode, event)) {
            return true;
        }

        // CP 自己的代码

        return super.onKeyDown(keyCode, event);
    }
}
