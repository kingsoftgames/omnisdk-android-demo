package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.ui.OmniSdkActivity;
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
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ApiManager;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ConfigData;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.view.AppView;


/**
 * 继承OmniSDK提供的Activity，注意顺序
 *
 * @author OmniSDK
 */
public class DemoIfExtendsOmniSdkActivity extends OmniSdkActivity {

    private final String TAG = "DemoAppActivity# ";
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
                    appView.attachToActivity(DemoIfExtendsOmniSdkActivity.this);
                });
            } else {
                DemoLogger.e(TAG, "Initialization Failed, error : " + result.error().getMessage());
                runOnUiThread(() -> {
                    appView = new AppView();
                    appView.onInitializedDone(false);
                    appView.attachToActivity(DemoIfExtendsOmniSdkActivity.this);
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
        ApiManager.getInstance().initialize(ApiManager.Language.JAVA);
        appView = new AppView();
        appView.attachToActivity(this);
        OmniSDKOptions options = OmniSDKOptions.builder().listener(listener).build();
        OmniSDKv3.getInstance().start(options);
        // 注意必须先调用 start()方法
        super.onCreate(savedInstanceState);


        DemoLogger.i(TAG, "onCreate(...) called");

        // CP自己的代码
    }

}
