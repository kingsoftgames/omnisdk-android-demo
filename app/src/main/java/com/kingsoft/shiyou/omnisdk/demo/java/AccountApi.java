package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.AccountNotifier;
import com.kingsoft.shiyou.omnisdk.api.entity.OmniConstant;
import com.kingsoft.shiyou.omnisdk.api.entity.UserInfo;
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    private final String tag = "AccountApi# ";
    private final Activity appActivity;
    private final IAccountCallback callback;
    private int mLoginType = 0;
    private int mSwitchType = 0;
    private int mBindType = 0;

    /* ******************************* OmniSDK账号功能接口示例如下 ******************************* */

    public AccountApi(Activity activity, IAccountCallback accountCallback) {
        this.appActivity = activity;
        this.callback = accountCallback;
        this.setAccountNotifier();
    }

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

                // 账号Json数据解析方式1：将其转成`com.kingsoft.shiyou.omnisdk.api.entity.UserInfo`数据实体
                UserInfo userInfo = OmniUtils.parseUserInfo(userJson);
                // 账号唯一标示，CP对接方用来唯一标示游戏账号
                String uid = userInfo.getUid();
                // 登录账号类型标示
                int type = userInfo.getType();
                // 海外业务账号登录数据验证token值，国内业务忽略改值
                String tokenId = userInfo.getTokenId();
                // 国内业务登录账号数据的验证Sign值；海外业务无该数据
                String verifySign = userInfo.getVerifySign();
                // 国内业务验证Sign值产生的Unix时间戳，单位为秒；海外业务无该数据
                long verifyTimestamp = userInfo.getVerifyTimestamp();
                DemoLogger.i(tag, "verify : [ " +
                        "uid : " + uid + " , " +
                        "type : " + type + " , " +
                        "verifySign : " + verifySign + " , " +
                        "verifyTimestamp : " + verifyTimestamp + " ]");

                /*
                 * 账号Json数据解析方式2：可将其转成Map数据结构或者`org.json.JSONObject`，获取相关数据项的key值如下：
                 * `cpUid`: 账号唯一标示key值 废弃
                 * `type`: 登录账号类型标示key值
                 * `token`: 海外业务账号登录数据验证token数据对应的key值
                 * `verifySign`: 国内业务登录账号数据的验证Sign值对应的key值
                 * `verifyTimestamp`: 国内业务验证Sign值产生的Unix时间戳对应的key值
                 */
                Map<String, Object> userMap = OmniUtils.toMap(userJson);
                // 账号唯一标示，CP对接方用来唯一标示游戏账号
                String accountUid = userMap.get("uid").toString();
                // 登录账号类型标示
                int accountType = Integer.parseInt(userMap.get("type").toString());
                // 海外业务账号登录数据验证token值，国内业务忽略改值
                String accountTokenId = userMap.get("token").toString();
                String state = userMap.get("state").toString();
                // 国内业务登录账号数据的验证Sign值；海外业务无该数据
                String accountVerifySign = "";
                // 国内业务验证Sign值产生的Unix时间戳，单位为秒；海外业务无该数据
                long accountVerifyTimestamp = 0;
                if (userMap.get("verifySign") != null && userMap.get("verifySign").toString().length() > 0) {
                    accountVerifySign = userMap.get("verifySign").toString();
                    accountVerifyTimestamp = Long.parseLong(userMap.get("verifyTimestamp").toString());
                }
                DemoLogger.i(tag, "verify : [ " +
                        "uid : " + accountUid + " , " +
                        "type : " + accountType + " , " +
                        "verifySign : " + accountVerifySign + " , " +
                        "state : " + state + " , " +
                        "verifyTimestamp : " + accountVerifyTimestamp + " ]");

                // 国内账号登录业务，游戏方在客户端收到登录成功的账号数据后需回传至游戏服务器端进行账号数据验证
                sendToServerForVerification(accountUid, accountVerifyTimestamp, accountVerifySign);

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

    /* ****************************************************************************************** */

    /**
     * OmniSDK账号切换接口代码示例，该接口不再被使用。
     */
    @Deprecated
    public void switchAccount() {
        // 构建登录请求数据Map
        Map<String, String> loginParams = new HashMap<>();
        /*
         * 指定切换账号类型,其key值必须为`switchType`,value为int类型
         * `0`或者不设置表示不指定特定切换账号类型,直接使用OMNI SDK账号切换界面。
         * 非`0`表示CP使用自身的切换账号选项UI界面,在用户选择账号类型后传入对应的账号标示ID调用接口完成切换业务.
         * （比如 `2`为邮箱账号 `3`为Facebook账号, `4`为Google账号等等）
         */

        // 按照自身对接需求设置登录账号类型
        loginParams.put(OmniConstant.accountType, String.valueOf(mSwitchType));
        OmniSDK.getInstance().switchAccount(appActivity, loginParams);

    }

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

    /**
     * 验证登录账号数据是否合法，务必注意此验证过程必须在服务器端进行
     */
    private void sendToServerForVerification(String uid, Long verifyTimestamp, String receivedVerifySign) {
        // 验证算法为`HmacSHA1`
        String HmacSHA1_Algorithm = "HmacSHA1";
        // Omni方分配给游戏服务器方的共享密钥，此密钥禁止出现在客户端，必须在服务器端维护
        String serverSecretKey = "19fdfa2847784585bee966a3b6c84cee";
        // Omni方分配给游戏应用的AppId
        String appId = "91000184";
        try {
            // 1.组建待验证的数据：appId={Omni方分配给游戏应用的AppId}&uid={账号UID}&verifyTimestamp={验证数据产生的时间戳}
            String dataToVerified = "appId=" + appId + "&uid=" + uid + "&verifyTimestamp=" + verifyTimestamp;
            DemoLogger.i(tag, "dataToVerified : " + dataToVerified);
            // 2.进行`HmacSHA1`运算
            Mac mac = Mac.getInstance(HmacSHA1_Algorithm);
            mac.init(new SecretKeySpec(serverSecretKey.getBytes(StandardCharsets.UTF_8), HmacSHA1_Algorithm));
            byte[] resultBytes = mac.doFinal(dataToVerified.getBytes(StandardCharsets.UTF_8));
            // 3.将计算结果bytes转成Hex16进制编码的值
            StringBuilder result = new StringBuilder();
            for (int index = 0, len = resultBytes.length; index <= len - 1; index += 1) {
                result.append(Integer.toHexString((resultBytes[index] >> 4) & 0xF));
                result.append(Integer.toHexString(resultBytes[index] & 0xF));
            }
            String generatedSignInHex = result.toString();
            DemoLogger.i(tag, "generatedSignInHex : " + generatedSignInHex);
            // 4.验证是否一致
            boolean verificationResult = generatedSignInHex.equals(receivedVerifySign);
            if (verificationResult) {
                DemoLogger.i(tag, "verify succeeded");
            } else {
                DemoLogger.e(tag, "verify failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
