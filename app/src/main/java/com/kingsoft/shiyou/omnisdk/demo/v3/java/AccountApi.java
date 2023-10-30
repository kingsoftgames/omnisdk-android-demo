package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.AccountNotifier;
import com.kingsoft.shiyou.omnisdk.api.entity.OmniConstant;
import com.kingsoft.shiyou.omnisdk.api.entity.UserInfo;
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils;
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKErrorCode;
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKAuthMethod;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKIdentityProvider;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKLoginInfo;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKLinkAccountOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKLoginOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKSwitchAccountOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKDeleteAccountResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKKickedOutResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKLinkAccountResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKLoginResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKLogoutResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKRestoreAccountResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKResult;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKSwitchAccountResult;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAccountApi;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAccountCallback;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.view.AppView;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import kotlin.Pair;

/**
 * Description: OmniSDK账号相关业务API接口代码示例Demo
 *
 * @author: LuXing created on 2021/3/22 16:22
 */
@Keep
public class AccountApi implements IAccountApi {

    private static final String tag = "AccountApi# ";

    private final Activity appActivity;
    private static IAccountCallback callback;

    /* ******************************* OmniSDK账号功能接口示例如下 ******************************* */

    public AccountApi(Activity activity, IAccountCallback accountCallback) {
        appActivity = activity;
        callback = accountCallback;
    }

    public static void onLogin(OmniSDKResult<OmniSDKLoginResult> result) {
        if (result.isSuccess()) {
            OmniSDKLoginInfo info = result.get().getLoginInfo();
            DemoLogger.i(tag, "onLoginSuccess : " + info);

            callback.onLoginSucceeded(info);
        } else {
            OmniSDKError error = result.error();

            if (error.getCode() == OmniSDKErrorCode.USER_CANCELLED.getValue()) {
                callback.onCancelled();
            } else {
                callback.onFailed(error);
            }
        }
    }

    public static void onLogout(OmniSDKResult<OmniSDKLogoutResult> result) {
        if (result.isSuccess()) {
            OmniSDKLogoutResult logoutResult = result.get();
            DemoLogger.i(tag, "onLogoutSuccess : " + logoutResult);

            callback.onLogoutSucceeded();
        } else {
            OmniSDKError error = result.error();
            if (error.getCode() == OmniSDKErrorCode.USER_CANCELLED.getValue()) {
                callback.onCancelled();
            } else {
                callback.onFailed(error);
            }
        }
    }

    public static void OnLinkAccount(OmniSDKResult<OmniSDKLinkAccountResult> result) {
        if (result.isSuccess()) {
            OmniSDKLinkAccountResult linkAccountResult = result.get();
            DemoLogger.i(tag, "onBindSuccess : " + linkAccountResult);
            callback.onBindSucceeded(Objects.requireNonNull(OmniSDKv3.getInstance().getLoginInfo()));
        } else {
            OmniSDKError error = result.error();
            if (error.getCode() == OmniSDKErrorCode.USER_CANCELLED.getValue()) {
                callback.onCancelled();
            } else {
                callback.onFailed(error);
            }
        }
    }

    public static void OnSwitchAccount(OmniSDKResult<OmniSDKSwitchAccountResult> result) {
        if (result.isSuccess()) {
            OmniSDKSwitchAccountResult switchAccountResult = result.get();
            DemoLogger.i(tag, "onSwitchSuccess : " + switchAccountResult);
            callback.onSwitchSucceeded(Objects.requireNonNull(OmniSDKv3.getInstance().getLoginInfo()));
        } else {
            OmniSDKError error = result.error();
            if (error.getCode() == OmniSDKErrorCode.USER_CANCELLED.getValue()) {
                callback.onCancelled();
            } else {
                callback.onFailed(error);
            }
        }
    }

    public static void OnKickedOut(Activity activity, OmniSDKResult<OmniSDKKickedOutResult> result) {
        if (result.isSuccess()) {
            OmniSDKKickedOutResult kickedOutResult = result.get();
            DemoLogger.e(tag, "onKickedOut(...) called, forceLogout = " + kickedOutResult.isForce());
            if (kickedOutResult.isForce()) {
                // 强制退出账号：一般指用户无意识地被迫强制退出账号，比如防沉迷机制导致用户被迫强制退出账号。
                new Thread(() -> {
                    activity.runOnUiThread(() -> {
                        Toast.makeText(activity, "被迫强制退出，应用3秒后关闭", Toast.LENGTH_SHORT).show();
                    });
                    try {
                        // 模拟账号被强制退出前的业务处理（游戏对接方应该有自身的实现逻辑处理）
                        Thread.sleep(3000);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                // 非强制退出账号：一般指用户有意识地主动触发SDK内部`退出账号功能入口`发起的账号退出。
                callback.onLogoutSucceeded();
            }

        } else {
            OmniSDKError error = result.error();
            DemoLogger.e(tag, "OnKickedOut Failed: " + error);
            callback.onFailed(error);
        }
    }

    public static void OnDeleteAccount(OmniSDKResult<OmniSDKDeleteAccountResult> result) {
        if (result.isSuccess()) {
            OmniSDKDeleteAccountResult deleteAccountResult = result.get();
            DemoLogger.i(tag, "onDeleteAccount Success : " + deleteAccountResult);
            callback.onDeleteSucceeded();
        } else {
            OmniSDKError error = result.error();
            DemoLogger.e(tag, "onDeleteAccount Failed: " + error);
            if (error.getCode() == OmniSDKErrorCode.USER_CANCELLED.getValue()){
                 callback.onCancelled();
            }else {
                callback.onFailed(error);
            }
        }
    }

    public static void OnRestoreAccount(OmniSDKResult<OmniSDKRestoreAccountResult> result) {
        if (result.isSuccess()) {
            OmniSDKRestoreAccountResult restoreAccountResult = result.get();
            DemoLogger.i(tag, "OnRestoreAccount Success : " + restoreAccountResult);
            callback.onRestoreSucceeded(Objects.requireNonNull(OmniSDKv3.getInstance().getLoginInfo()));
        } else {
            OmniSDKError error = result.error();
            DemoLogger.e(tag, "OnRestoreAccount Failed: " + error.getMessage());
            if (error.getCode() == OmniSDKErrorCode.USER_CANCELLED.getValue()){
                callback.onCancelled();
            }else {
                callback.onFailed(error);
            }
        }
    }

    /**
     * OmniSDK账号登录接口代码示例
     */

    /* ****************************************************************************************** */

    private OmniSDKAuthMethod convertToAutoMethod(int type) {
        switch (type) {
            case 0:
                return OmniSDKAuthMethod.NONE;
            case 1:
                return OmniSDKAuthMethod.GUEST;
            case 3:
                return OmniSDKAuthMethod.FACEBOOK;
            case 4:
                return OmniSDKAuthMethod.GOOGLE;
            case 23:
                return OmniSDKAuthMethod.LINE;
        }
        return OmniSDKAuthMethod.NONE;
    }

    private OmniSDKIdentityProvider convertToIdp(int type) {
        switch (type) {
            case 0:
                return OmniSDKIdentityProvider.NONE;
            case 3:
                return OmniSDKIdentityProvider.FACEBOOK;
            case 4:
                return OmniSDKIdentityProvider.GOOGLE;
            case 23:
                return OmniSDKIdentityProvider.LINE;
        }
        return OmniSDKIdentityProvider.NONE;
    }

    @Override
    public void loginImpl(int type) {
        OmniSDKLoginOptions opts = OmniSDKLoginOptions.builder()
                .authMethod(convertToAutoMethod(type))
                .build();

        OmniSDKv3.getInstance().login(appActivity, opts);
    }

    @Override
    public void switchAccountImpl(int type) {
        OmniSDKSwitchAccountOptions opts = OmniSDKSwitchAccountOptions.builder()
                .idp(convertToIdp(type))
                .build();

        OmniSDKv3.getInstance().switchAccount(appActivity, opts);
    }

    @Override
    public void bindAccountImpl(int type) {
        OmniSDKLinkAccountOptions opts = OmniSDKLinkAccountOptions.builder()
                .idp(convertToIdp(type))
                .build();

        OmniSDKv3.getInstance().linkAccount(appActivity, opts);
    }

    @Override
    public void logoutImpl() {
        OmniSDKv3.getInstance().logout(appActivity);
    }
}
