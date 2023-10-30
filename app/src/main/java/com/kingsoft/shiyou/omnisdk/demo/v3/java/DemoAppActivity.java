package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKDeleteAccountResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKKickedOutResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKLinkAccountResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKLoginResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKLogoutResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKPurchaseResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKRestoreAccountResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKStartResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKSwitchAccountResult;
import com.kingsoft.shiyou.omnisdk.api.v3.listeners.OmniSDKListener;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ConfigData;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.view.AppView;


/**
 * Description: 应用主Activity
 *
 * @author: LuXing created on 2021/3/15 9:30
 */
public class DemoAppActivity extends AppCompatActivity {

    private final String TAG = "DemoV3Activity# ";
    private AppView appView;

    /**
     * 游戏CP对接方在触发任何账号相关业务接口前必须先设置账号相关结果数据回调监听，
     * 用于监听账号登录，账号绑定，账号登出这些接口业务结果数据。
     */
    private final OmniSDKListener listener = new OmniSDKListener() {

        @Override
        public void onStart(@NonNull OmniSDKResult<OmniSDKStartResult> result) {
            if (result.isSuccess()) {
                DemoLogger.i(TAG, "Initialization Done Successfully");
                OmniSDKStartResult startResult = result.get();
                ConfigData.singletonInstance.setAppId(startResult.getAppId());
                ConfigData.singletonInstance.setChannelId(startResult.getChannelId());
                ConfigData.singletonInstance.setChannelName(startResult.getChannelName());
                ConfigData.singletonInstance.setSdkVersion(startResult.getSdkVersion());
                ConfigData.singletonInstance.setCpsName(startResult.getCpsName());
                runOnUiThread(() -> {
                    appView = new AppView();
                    appView.onInitializedDone(true);
                    appView.attachToActivity(DemoAppActivity.this);
                });
            } else {
                DemoLogger.e(TAG, "Initialization Failed, error : " + result.error().getMessage());
                runOnUiThread(() -> {
                    appView = new AppView();
                    appView.onInitializedDone(false);
                    appView.attachToActivity(DemoAppActivity.this);
                    appView.showMessageDialog("Initialization Failed, error : " + result.error().getMessage());
                });
            }
        }

        @Override
        public void onLogin(@NonNull OmniSDKResult<OmniSDKLoginResult> result) {
            AccountApi.onLogin(result);
        }

        @Override
        public void onLogout(@NonNull OmniSDKResult<OmniSDKLogoutResult> result) {
            AccountApi.onLogout(result);
        }

        @Override
        public void onSwitchAccount(@NonNull OmniSDKResult<OmniSDKSwitchAccountResult> result) {
            AccountApi.OnSwitchAccount(result);
        }

        @Override
        public void onLinkAccount(@NonNull OmniSDKResult<OmniSDKLinkAccountResult> result) {
            AccountApi.OnLinkAccount(result);
        }

        @Override
        public void onDeleteAccount(@NonNull OmniSDKResult<OmniSDKDeleteAccountResult> result) {
            AccountApi.OnDeleteAccount(result);
        }

        @Override
        public void onRestoreAccount(@NonNull OmniSDKResult<OmniSDKRestoreAccountResult> result) {
            AccountApi.OnRestoreAccount(result);
        }

        @Override
        public void onKickedOut(@NonNull OmniSDKResult<OmniSDKKickedOutResult> result) {
            AccountApi.OnKickedOut(appView.getAppActivity(), result);
        }

        @Override
        public void onPurchase(@NonNull OmniSDKResult<OmniSDKPurchaseResult> result) {
            PayApi.OnPurchase(result);
        }
    };

    /**
     * CP对接方必须在游戏Activity的onCreate生命周期方法内调用如下SDK API接口
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DemoLogger.i(TAG, "onCreate(...) called");

        OmniSDKOptions options = OmniSDKOptions.builder().listener(listener).build();
        OmniSDKv3.getInstance().start(options);


        OmniSDKv3.getInstance().onCreate(this, savedInstanceState);


        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onStart生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onStart() {
        super.onStart();

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onStart(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onRestart生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onRestart() {
        super.onRestart();

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onRestart(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onResume生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onResume() {
        super.onResume();

        Log.e("SDK", "------------onResume----------------");

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onResume(this);


        // CP自己的代码

    }

    /**
     * CP对接方必须在游戏Activity的onPause生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onPause() {
        super.onPause();

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onPause(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onStop生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onStop() {
        super.onStop();

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onStop(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onDestroy生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onDestroy(this);
        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onNewIntent生命周期方法内调用如下SDK API接口
     */
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onNewIntent(this, intent);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onSaveInstanceState方法并在其中调用如下SDK API接口
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onSaveInstanceState(this, outState);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onRestoreInstanceState方法并在其中调用如下SDK API接口
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onRestoreInstanceState(this, savedInstanceState);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onConfigurationChanged方法并在其中调用如下SDK API接口
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // SDK API(必须调用)
        OmniSDKv3.getInstance().onConfigurationChanged(this, newConfig);

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
        OmniSDKv3.getInstance().onActivityResult(
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
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        OmniSDKv3.getInstance().onRequestPermissionsResult(
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
        OmniSDKv3.getInstance().onBackPressed(this);

        // CP自己的代码

        super.onBackPressed();
    }

    /**
     * CP对接方必须在游戏Activity中重载onKeyDown方法并在其中调用如下SDK API接口
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // SDK API(必须调用)
        if (OmniSDKv3.getInstance().onKeyDown(this, keyCode, event)) {
            return true;
        }

        // CP 自己的代码

        return super.onKeyDown(keyCode, event);
    }
}
