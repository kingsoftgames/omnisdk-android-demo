package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.AccountNotifier;
import com.kingsoft.shiyou.omnisdk.api.callback.SwitchAccountCallback;
import com.kingsoft.shiyou.omnisdk.api.entity.OmniConstant;
import com.kingsoft.shiyou.omnisdk.api.entity.UserInfo;
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import kotlin.Pair;

/**
 * Description: OmniSDK账号相关业务API接口代码示例Demo
 *
 * @author: LuXing created on 2021/3/22 16:22
 */
public class AccountApi implements IAccountApi {

    private final String tag = "AccountApi# ";
    private final Activity appActivity;
    private final IAccountCallback callback;
    private int mLoginType = 0;
    private int mSwitchType = 0;
    private int mBindType = 0;

    public AccountApi(Activity activity, IAccountCallback accountCallback) {
        this.appActivity = activity;
        this.callback = accountCallback;
        this.setAccountNotifier();
    }

    /* ******************************* OmniSDK账号功能接口示例如下 ******************************* */

    /**
     * 游戏CP对接方在触发任何账号相关业务接口前必须先设置账号相关结果数据回调监听，
     * 用于监听账号登录，账号绑定，账号登出这些接口业务结果数据。
     */
    private void setAccountNotifier() {

        OmniSDK.getInstance().setAccountNotifier(new AccountNotifier() {

            @Override
            public void onLoginSuccess() {
                // 账号登录成功，获取账号Json数据
                String userJson = OmniSDK.getInstance().getUserInfo();
                DemoLogger.i(tag, "onLoginSuccess : " + userJson);

                // 账号Json数据解析方式1：可将其转成`com.kingsoft.shiyou.omnisdk.api.entity.UserInfo`数据实体
                // 然后按照需要提取数据实体中的各个数据项的值
                UserInfo userInfo = OmniUtils.fromJson(userJson, UserInfo.class);

                // 账号Json数据解析方式2：可将其转成Map数据结构或者`org.json.JSONObject`
                Map<String, Object> userMap = OmniUtils.toMap(userJson);
                // 账号唯一标示，CP对接方用来唯一标示游戏账号
                String accountCpUid = userMap.get("cpUid").toString();
                // 账号类型
                int accountType = Integer.parseInt(userMap.get("type").toString());
                // 账号Token，国内业务直接忽略其值，海外业务该值用于账号验证
                String accountToken = userMap.get("token").toString();
                DemoLogger.i(tag, "accountCpUid = " + accountCpUid);
                DemoLogger.i(tag, "accountType = " + accountType);
                DemoLogger.i(tag, "accountToken = " + accountToken);

                callback.onLoginSucceeded(userMap, accountType);
            }

            @Override
            public void onBindSuccess() {
                // 账号绑定成功，CP对接方可以按照自身需求参照登录成功回调(onLoginSuccess)进行绑定后的账号数据解析
                String userJson = OmniSDK.getInstance().getUserInfo();
                DemoLogger.i(tag, "onBindSuccess : " + userJson);
                callback.onBindSucceeded(OmniUtils.toMap(userJson));
            }

            @Override
            public void onLogoutSuccess() {
                // 账号登出成功，游戏可以开始自己的登出业务逻辑比如退出当前游戏角色账号等等
                DemoLogger.i(tag, "onLogoutSuccess");
                callback.onLogoutSucceeded();
            }

            @Override
            public void onFailure(
                    @NotNull Type type,
                    @NotNull Pair<Integer, String> sdkFailureResult,
                    @NonNull Pair<Integer, String> channelFailureResult
            ) {
                // 账号相关操作失败，如有需要可以通过回调字段`type`来区分具体是哪种账号业务操作失败

                DemoLogger.e(tag, "onFailure : [ " + type + " , " + sdkFailureResult + " , " + channelFailureResult + " ]");
                // 是否是用户主动取消操作导致的失败
                boolean isCancelled = (sdkFailureResult.getFirst() == OmniConstant.ResultCode.accountCancelled);

                // 根据特定的账号业务类型处理失败结果
                if (type == Type.LOGIN) {
                    // 账号登录失败，CP对接方需回调自身的登录UI界面等待用户再次触发登录操作

                    if (isCancelled) {
                        // 登录操作被主动取消
                        callback.onCancelled();
                    } else {
                        callback.onFailed(sdkFailureResult, channelFailureResult);
                    }
                } else if (type == Type.BIND) {
                    // 账号绑定失败，CP对接方可按自身需求处理

                    if (isCancelled) {
                        // 登录操作被主动取消
                        callback.onCancelled();
                    } else {
                        callback.onFailed(sdkFailureResult, channelFailureResult);
                    }
                } else if (type == Type.LOGOUT) {
                    // 账号登出失败，CP对接方需终止账号注销登出业务
                    callback.onLogoutFailed(sdkFailureResult);
                }
            }

            @Override
            public void onKickedOut(boolean forceLogout) {
                // 账号被动退出，该回调由OmniSDK主动发起调用。CP对接方必须处理该回调，立即退出当前游戏账号。
                // 注意：该回调不是CP对接方主动调用账号登出接口后的回调，主动调用账号登出接口后的回调请参照`onLogoutSuccess`和`onFailure`
                DemoLogger.e(tag, "onKickedOut(...) called, forceLogout = " + forceLogout);
                if (forceLogout) {
                    // 强制退出账号：一般指用户无意识地被迫强制退出账号，比如防沉迷机制导致用户被迫强制退出账号。
                    new Thread(() -> {
                        appActivity.runOnUiThread(() -> {
                            Toast.makeText(appActivity, "被迫强制退出，应用3秒后关闭", Toast.LENGTH_SHORT).show();
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
            }

        });
    }

    /**
     * OmniSDK账号登录接口代码示例
     */
    public void login() {
        // 构建登录请求数据Map
        Map<String, String> loginParams = new HashMap<>();
        // 按照自身对接需求设置登录账号类型
        loginParams.put(OmniConstant.accountType, String.valueOf(mLoginType));
        OmniSDK.getInstance().login(appActivity, loginParams);
    }

    /**
     * OmniSDK账号绑定接口代码示例
     */
    public void bindAccount() {
        // 构建绑定账号请求数据Map
        Map<String, String> bindParams = new HashMap<>();
        // 按照自身对接需求设置绑定账号类型
        bindParams.put(OmniConstant.accountType, String.valueOf(mBindType));
        OmniSDK.getInstance().bindAccount(appActivity, bindParams);
    }

    /**
     * OmniSDK账号登出接口代码示例
     */
    public void logout() {
        // 请当玩家在游戏内触发`登出`功能的时候先调用该接口，当收到成功登出回调后才可以执行CP方自己的登出业务
        OmniSDK.getInstance().logout(appActivity);
    }

    /**
     * OmniSDK账号切换接口代码示例，该接口不再被使用。
     */
    @Deprecated
    public void switchAccount() {
        Map<String, Object> switchParams = new HashMap<>();
        switchParams.put(OmniConstant.accountType, mSwitchType);
        OmniSDK.getInstance().switchAccount(appActivity, switchParams, new SwitchAccountCallback() {
            @Override
            public void onSuccess() {
                String userJson = OmniSDK.getInstance().getUserInfo();
                callback.onSwitchSucceeded(OmniUtils.toMap(userJson));
            }

            @Override
            public void onFailure(Pair<Integer, String> responseCode) {
                callback.onFailed(responseCode, responseCode);
            }

            @Override
            public void onCancel() {
                callback.onCancelled();
            }
        });
    }

    /* ****************************************************************************************** */

    @Override
    public void loginImpl(int type) {
        mLoginType = type;
        login();
    }

    @Override
    public void switchAccountImpl(int type) {
        mSwitchType = type;
        switchAccount();
    }

    @Override
    public void bindAccountImpl(int type) {
        mBindType = type;
        bindAccount();
    }

    @Override
    public void logoutImpl() {
        logout();
    }
}
