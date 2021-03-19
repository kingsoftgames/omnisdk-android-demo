package com.kingsoft.shiyou.omnisdk.demo.java;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.BindAccountCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.LoginCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.PayCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.SwitchAccountCallback;
import com.kingsoft.shiyou.omnisdk.core.callback.ResponseCallback;
import com.kingsoft.shiyou.omnisdk.core.callback.ResultCallback;
import com.kingsoft.shiyou.omnisdk.core.entity.FriendInfo;
import com.kingsoft.shiyou.omnisdk.core.entity.Order;
import com.kingsoft.shiyou.omnisdk.core.entity.Product;
import com.kingsoft.shiyou.omnisdk.core.entity.ShareImageType;
import com.kingsoft.shiyou.omnisdk.core.entity.ShareParam;
import com.kingsoft.shiyou.omnisdk.core.entity.ShareType;
import com.kingsoft.shiyou.omnisdk.core.entity.SocialInfo;
import com.kingsoft.shiyou.omnisdk.core.entity.User;
import com.kingsoft.shiyou.omnisdk.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.Pair;

/**
 * OMNI SDK功能接口调用示例，假定`JavaActivity`为游戏自身的主Activity
 */
public class JavaActivity extends AppCompatActivity {

    /**
     * CP对接方必须在游戏Activity的onCreate生命周期方法内调用如下SDK API接口
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

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
        super.onBackPressed();

        // SDK API(必须调用)
        OmniSDK.getInstance().onBackPressed(this);

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onKeyDown方法并在其中调用如下SDK API接口
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // SDK API(必须调用)
        if (OmniSDK.getInstance().onKeyDown(this, keyCode, event)) {
            return true;
        }

        // CP 自己的代码

        return super.onKeyDown(keyCode, event);
    }


    /* ***************************** SDK 账号功能接口示例如下 ********************************** */

    /**
     * OMNI SDK 账号登录接口示例（若无特殊说明,SDK登录接口为必须接入接口）
     *
     * @param type 登录账号类型
     * @see User
     */
    public void login(int type) {
        // 构建登录请求数据Map
        Map<String, Object> loginParams = new HashMap<>();
        /*
         * 指定登录类型,其key值必须为loginType。
         * `0`表示不指定特定登录类型,直接使用OMNI SDK登录界面。
         * 非`0`表示CP使用自身的登录选项UI界面,在用户选择账号类型后传入对应的账号标示ID调用接口完成登录业务.
         * （比如 `2`为邮箱账号 `3`为Facebook账号, `4`为Google账号等等）
         */
        loginParams.put("loginType", type);
        // 调用SDK登录接口
        OmniSDK.getInstance().login(this, loginParams, new LoginCallback() {
            @Override
            public void onSuccess() {
                // 登录成功, 获取登录用户数据(Json字符串格式)
                String userJsonInfo = OmniSDK.getInstance().getUserInfo();

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)
                Map<String, Object> userMap =
                        new Gson().fromJson(userJsonInfo, new TypeToken<Map<String, Object>>() {
                        }.getType());

                // SDK账号唯一标示ID
                String uid = userMap.get("uid").toString();

                // SDK账号Token值 (CP对接方需验证其有效性)
                String token = userMap.get("token").toString();

                // CP方需使用该cpUid作为账号唯一标示ID
                String cpUid = userMap.get("cpUid").toString();

                // 按照需求解析更多账号数据信息,比如是否为游客账号，账号昵称等等
                int accountType = (int) Double.parseDouble(userMap.get("type").toString());

                if (1 == accountType) {
                    // 当前账号类型为纯游客账号
                    Log.i("SDK", "Guest Account");
                }
                String showName = userMap.get("showName").toString();

                Log.i("SDK", "showName = " + showName);

                // CP自己的代码，比如进入游戏业务
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                // 登录失败,请CP方按照自身逻辑进行业务处理
            }

            @Override
            public void onCancel() {
                // 玩家取消登录
            }
        });
    }

    /**
     * OMNI SDK 账号切换接口示例（按需求接入）
     *
     * @see User
     */
    public void switchAccount() {

        // 构建切换账号请求数据Map, 目前保留字段传空Map数据即可
        Map<String, Object> switchAccountParams = new HashMap<>();

        // 调用SDK账号切换接口
        OmniSDK.getInstance().switchAccount(this, switchAccountParams, new SwitchAccountCallback() {
            @Override
            public void onSuccess() {

                // 账号切换成功, 获取新切换的用户数据(Json字符串格式)
                String userJsonInfo = OmniSDK.getInstance().getUserInfo();

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)
                Map<String, Object> userMap =
                        new Gson().fromJson(userJsonInfo, new TypeToken<Map<String, Object>>() {
                        }.getType());

                // SDK账号唯一标示ID
                String uid = userMap.get("uid").toString();

                // SDK账号Token值 (CP对接方需验证其有效性)
                String token = userMap.get("token").toString();

                // CP方需使用该cpUid作为账号唯一标示ID
                String cpUid = userMap.get("cpUid").toString();

                // CP自己的代码，比如退出当前游戏账号，使用新切换的账号重新开始游戏
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                Log.e("SDK", "err = " + pair.toString());
                // 切换失败,无需特别处理,回到游戏界面即可
            }

            @Override
            public void onCancel() {
                // 切换取消,无需特别处理,回到游戏界面即可
            }
        });
    }

    /**
     * OMNI SDK 账号绑定接口示例（按需求接入）
     *
     * @see User
     */
    public void bindAccount() {

        // 构建绑定账号请求数据Map, 目前保留字段传空Map数据即可
        Map<String, Object> bindAccountParams = new HashMap<>();

        // 调用SDK账号绑定接口
        OmniSDK.getInstance().bindAccount(this, bindAccountParams, new BindAccountCallback() {
            @Override
            public void onSuccess() {

                // 账号绑定成功,获取新绑定的用户数据(Json字符串格式)
                String userJsonInfo = OmniSDK.getInstance().getUserInfo();

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)
                Map<String, Object> userMap =
                        new Gson().fromJson(userJsonInfo, new TypeToken<Map<String, Object>>() {
                        }.getType());

                // SDK账号唯一标示ID
                String uid = userMap.get("uid").toString();

                // SDK账号Token值 (CP对接方需验证其有效性)
                String token = userMap.get("token").toString();

                // CP方需使用该cpUid作为账号唯一标示ID
                String cpUid = userMap.get("cpUid").toString();

                // CP自己的代码，比如回到游戏界面给玩家奖励
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                Log.e("SDK", "err = " + pair.toString());
                // 绑定失败
            }

            @Override
            public void onCancel() {
                // 绑定取消,无需特别处理
            }
        });
    }

    /**
     * OMNI SDK 账号注销登出接口示例（必须接入）.
     * 请在玩家在游戏内触发`登出`功能的时候先调用OMNI SDK 的登出接口.在收到成功回调后才可以执行CP自己的登出业务
     */
    public void logout() {

        OmniSDK.getInstance().logout(this, new ResponseCallback() {
            @Override
            public void onSuccess() {
                // Omni SDK已登出当前账号用户，游戏可以开始自己的登出业务逻辑

                // CP自己的代码，比如注销游戏自身的账号回到游戏开始登录的界面等等
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                // 登出失败,无需特别处理,回到游戏界面即可
            }
        });
    }

    /* ***************************** SDK 支付功能接口示例如下 ********************************** */

    /**
     * OMNI SDK 支付接口示例（若无特殊说明,SDK支付接口为必须接入接口）
     *
     * @see Product
     */
    public void pay() {

        String productId = "10010";          // 商品 ID (必传数据)
        String productName = "750 Diamonds"; // 商品名称，有则传值，无则保持和商品ID一致
        String productDesc = "750 Diamonds"; // 商品描述，有则传值，无则保持和商品ID一致
        double productPrice = 9.99;          // 商品价格(单位为元,必传数据),比如9.99，0.99等等
        double payAmount = 9.99;             // 实际支付总额(单位为元)
        String currency = "USD";             // 商品价格对应的货币单位（必传数据）, 比如 USD HKD 等等
        String serverId = "s01";             // 服务器 ID（必传数据），对于没有区服概念的游戏请直接传空字符串""
        String roleId = "r15996112_122";     // 游戏角色唯一标示ID（必传数据）

        // 游戏订单号ID.有则传值，没有则传空字符串""
        String gameTradeNo = "gamecno123456789xxx";

        // CP方服务器支付回调地址，有则传值，没有则传空字符串"".若传空字符串""则SDK将使用后台配置的回调地址
        String gameCallbackUrl = "https://game.sdk.server/android/payment";

        // CP方自定义扩展数据,会在回调CP方服务器支付数据的时候原样返回.有则传值，没有则传空字符串""
        String extJson = "{.}";

        // 创建待购买产品的数据实体
        Product purchaseProduct = new Product(
                productId,
                productName,
                productDesc,
                productPrice,
                payAmount,
                currency,
                serverId,
                roleId,
                (gameTradeNo == null) ? "" : gameTradeNo,
                (gameCallbackUrl == null) ? "" : gameCallbackUrl,
                (extJson == null) ? "" : extJson);

        // 调用SDK支付接口
        OmniSDK.getInstance().pay(this, purchaseProduct, new PayCallback() {
            @Override
            public void onSuccess(Order order, Pair<Pair<Integer, String>, Pair<Integer, String>> pair) {
                // 支付成功，对于非单机游戏，CP方必须以OMNI SDK通知游戏服务器端的支付成功数据作为发货依据

                // OMNI SDK支付唯一订单号,CP方可按照自身需要使用该值来标示本次支付购买
                String orderId = order.getOrderId();
            }

            @Override
            public void onFailure(Order order, Pair<Pair<Integer, String>, Pair<Integer, String>> pair) {
                Log.e("SDK", "err = " + pair.toString());
                // 支付失败,CP方可以按照自身需求进行业务处理，比如弹出"提示支付失败"提示等等
            }

            @Override
            public void onCancel() {
                // 支付取消,无需特别处理
            }
        });

    }

    /* ***************************** SDK 社交功能接口示例如下 ********************************** */

    /**
     * OMNI SDK 社交分享(链接，图片等等)接口示例
     */
    public void share() {

        //1. 创建分享参数
        ShareType shareType = ShareType.IMAGE;         // 分享类型，目前支持LINK, GROUP_LINK, IMAGE
        String title = "Join Us";                      // 分享标题文本
        String description = "Come to play with me";   // 分享具体描述文本
        String link = "https://play.google.com/games"; // 分享内容URL链接地址,可选

        // 在分享图片的时候需要指定该值，目前支持类型为NETWORK（网络图片),LOCAL(本地图片uri),SCREENSHOT(截屏)
        ShareImageType imageType = ShareImageType.LOCAL;
        // 在分享图片的时候需要指定该值, 给出分享图片本地Uri或网络图片Uri地址
        String imageUri = "https://www.game/com/123.png";

        ShareParam shareParam = new ShareParam(shareType, title, description, link, imageType, imageUri);

        //2. 指定分享的社交平台，比如facebook,twitter
        String platform = "facebook";

        //3. 调用SDK社交分享接口
        OmniSDK.getInstance().share(this, platform, shareParam, new ResponseCallback() {
            @Override
            public void onSuccess() {
                // 分享成功，CP方按自身需求执行之后业务比如给与奖励等等
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                // 分享失败
                Log.e("SDK", "err = " + pair.toString());
            }
        });
    }

    /**
     * OMNI SDK 游戏邀请接口示例
     */
    public void invite() {

        //指定社交平台，比如facebook,twitter
        String platform = "facebook";

        /*
         * 邀请额外JSON数据,比如:
         * {
         *   "message":"invite-message",
         *   "channelName":"facebook",
         *   "appLinkURL":"appLinkURL",
         *   "previewImageURL":"previewImageURL",
         *   "extJsonParams":"extJsonParams"
         * }
         * 若无特殊说明,只需指定message即可
         * */
        String inviteJson = "{\"message\":\"invite-message\"}";

        //3. 调用SDK社交分享接口
        OmniSDK.getInstance().invite(this, platform, inviteJson, new ResultCallback() {
            @Override
            public void onSuccess(String resultJson) {
                // 邀请享成功，CP方按自身需求执行之后业务比如给与奖励等等
                Log.i("SDK", "resultJson = " + resultJson);
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                // 邀请失败
                Log.e("SDK", "err = " + pair.toString());
            }
        });
    }

    /**
     * OMNI SDK 获取当前玩家的社交账号信息接口示例
     */
    public void getSocialInfo() {

        //社交平台，比如facebook,twitter
        String platform = "facebook";

        OmniSDK.getInstance().getSocialInfo(this, platform, new ResultCallback() {
            @Override
            public void onSuccess(String resultJson) {
                try {
                    // 解析玩家社交账号信息数据
                    SocialInfo socialInfo = new Gson().fromJson(resultJson, SocialInfo.class);
                    String nickName = socialInfo.getNickName(); //昵称
                    String imageUrl = socialInfo.getImageUrl(); //头像ICON地址
                    int width = socialInfo.getWidth();          //头像宽度像素
                    int height = socialInfo.getHeight();        //头像高度像素
                    String gender = socialInfo.getGender();     //性别 "1"为男性 "0"为女性
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                // 获取失败
                Log.e("SDK", "err = " + pair.toString());
            }
        });
    }

    /**
     * OMNI SDK 获取玩家游戏好友的社交账号信息接口示例
     */
    public void getFriendInfo() {

        //社交平台，比如facebook,twitter
        String platform = "facebook";

        OmniSDK.getInstance().getFriendInfo(this, platform, new ResultCallback() {
            @Override
            public void onSuccess(String resultJson) {
                try {
                    // 解析玩家游戏好友的社交账号信息数据
                    ArrayList<FriendInfo> friendsInList = new Gson().fromJson(
                            resultJson,
                            new TypeToken<ArrayList<FriendInfo>>() {
                            }.getType());
                    for (int i = 0; i < friendsInList.size(); i++) {
                        FriendInfo friendInfo = friendsInList.get(i);
                        String id = friendInfo.getId();             // 好友社交平台账号ID
                        String nickName = friendInfo.getNickName(); // 昵称
                        String imageUrl = friendInfo.getImageUrl(); // 头像ICON地址
                        int width = friendInfo.getWidth();          // 头像宽度像素
                        int height = friendInfo.getHeight();        // 头像高度像素
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Pair<Integer, String> pair) {
                // 获取失败
                Log.e("SDK", "err = " + pair.toString());
            }
        });
    }

}