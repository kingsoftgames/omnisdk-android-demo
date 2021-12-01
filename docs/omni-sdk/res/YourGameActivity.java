package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.NativeActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.PayNotifier;
import com.kingsoft.shiyou.omnisdk.api.entity.Order;

import org.jetbrains.annotations.NotNull;

import kotlin.Pair;

public class YourGameActivity extends NativeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OmniSDK.getInstance().onCreate(this, savedInstanceState);

        // 注册支付监听
        OmniSDK.getInstance().setPayNotifier(new PayNotifier() {
            @Override
            public void onSuccess(@NotNull Pair<Integer, String> sdkCodeMsg, @NotNull Order order) {
            }

            @Override
            public void onFailure(@NotNull Pair<Integer, String> sdkCodeMsg, @Nullable Pair<Integer, String> channelCodeMsg, @Nullable Order order) {
            }

            @Override
            public void onCancel() {
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        OmniSDK.getInstance().onStart(this);

        // CP自己的代码
    }

    @Override
    public void onRestart() {
        super.onRestart();
        OmniSDK.getInstance().onRestart(this);

        // CP自己的代码
    }

    @Override
    public void onResume() {
        super.onResume();
        OmniSDK.getInstance().onResume(this);

        // CP自己的代码
    }

    @Override
    public void onPause() {
        super.onPause();
        OmniSDK.getInstance().onPause(this);

        // CP自己的代码
    }

    @Override
    public void onStop() {
        super.onStop();
        OmniSDK.getInstance().onStop(this);

        // CP自己的代码
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OmniSDK.getInstance().onDestroy(this);

        // CP自己的代码
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        OmniSDK.getInstance().onNewIntent(this, intent);

        // CP自己的代码
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        OmniSDK.getInstance().onSaveInstanceState(this, outState);

        // CP自己的代码
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        OmniSDK.getInstance().onRestoreInstanceState(this, savedInstanceState);

        // CP自己的代码
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        OmniSDK.getInstance().onConfigurationChanged(this, newConfig);

        // CP自己的代码
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

        // CP自己的代码
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

        // CP自己的代码
    }

    @Override
    public void onBackPressed() {
        OmniSDK.getInstance().onBackPressed(this);

        // CP自己的代码
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (OmniSDK.getInstance().onKeyDown(this, keyCode, event)) {
            return true;
        }

        // CP 自己的代码

        return super.onKeyDown(keyCode, event);
    }
}
