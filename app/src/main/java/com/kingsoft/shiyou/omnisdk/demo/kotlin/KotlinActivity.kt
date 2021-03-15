package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.BindAccountCallback
import com.kingsoft.shiyou.omnisdk.api.callback.LoginCallback
import com.kingsoft.shiyou.omnisdk.api.callback.PayCallback
import com.kingsoft.shiyou.omnisdk.api.callback.SwitchAccountCallback
import com.kingsoft.shiyou.omnisdk.core.callback.ResponseCallback
import com.kingsoft.shiyou.omnisdk.core.callback.ResultCallback
import com.kingsoft.shiyou.omnisdk.core.entity.*
import com.kingsoft.shiyou.omnisdk.demo.R
import java.util.*

class KotlinActivity : AppCompatActivity() {

    /**
     * CP对接方必须在游戏Activity的onCreate生命周期方法内调用如下SDK API接口
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java)

        // SDK API接口(必须调用)
        OmniSDK.instance.onCreate(this, savedInstanceState)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onStart生命周期方法内调用如下SDK API接口
     */
    override fun onStart() {
        super.onStart()

        // SDK API(必须调用)
        OmniSDK.instance.onStart(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onRestart生命周期方法内调用如下SDK API接口
     */
    override fun onRestart() {
        super.onRestart()

        // SDK API(必须调用)
        OmniSDK.instance.onRestart(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onResume生命周期方法内调用如下SDK API接口
     */
    override fun onResume() {
        super.onResume()

        // SDK API(必须调用)
        OmniSDK.instance.onResume(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onPause生命周期方法内调用如下SDK API接口
     */
    override fun onPause() {
        super.onPause()

        // SDK API(必须调用)
        OmniSDK.instance.onPause(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onStop生命周期方法内调用如下SDK API接口
     */
    override fun onStop() {
        super.onStop()

        // SDK API(必须调用)
        OmniSDK.instance.onStop(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onDestroy生命周期方法内调用如下SDK API接口
     */
    override fun onDestroy() {
        super.onDestroy()

        // SDK API(必须调用)
        OmniSDK.instance.onDestroy(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onNewIntent生命周期方法内调用如下SDK API接口
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // SDK API(必须调用)
        OmniSDK.instance.onNewIntent(this, intent)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onSaveInstanceState方法并在其中调用如下SDK API接口
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // SDK API(必须调用)
        OmniSDK.instance.onSaveInstanceState(this, outState)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onRestoreInstanceState方法并在其中调用如下SDK API接口
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // SDK API(必须调用)
        OmniSDK.instance.onRestoreInstanceState(this, savedInstanceState)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onConfigurationChanged方法并在其中调用如下SDK API接口
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // SDK API(必须调用)
        OmniSDK.instance.onConfigurationChanged(this, newConfig)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onActivityResult方法并在其中调用如下SDK API接口
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // SDK API(必须调用)
        OmniSDK.instance.onActivityResult(this, requestCode, resultCode, data)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onRequestPermissionsResult方法并在其中调用如下SDK API接口
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // SDK API(必须调用)
        OmniSDK.instance.onRequestPermissionsResult(
            this,
            requestCode,
            permissions,
            grantResults
        )

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onBackPressed方法并在其中调用如下SDK API接口
     */
    override fun onBackPressed() {
        super.onBackPressed()

        // SDK API(必须调用)
        OmniSDK.instance.onBackPressed(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onKeyDown方法并在其中调用如下SDK API接口
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        // SDK API(必须调用)
        if (OmniSDK.instance.onKeyDown(this, keyCode, event)) {
            return true
        }
        // CP 自己的代码

        return super.onKeyDown(keyCode, event)
    }

    /* ***************************** SDK 账号功能接口示例如下 ********************************** */

    /**
     * OMNI SDK 账号登录接口示例（若无特殊说明,SDK登录接口为必须接入接口）
     *
     * @param type 登录账号类型
     * @see User
     */
    fun login(type: Int) {

        // 构建登录请求数据Map
        val loginParams: MutableMap<String, Any> = mutableMapOf()

        /*
         * 指定登录类型,其key值必须为loginType。
         * `0`表示不指定特定登录类型,直接使用OMNI SDK登录界面。
         * 非`0`表示CP使用自身的登录选项UI界面,在用户选择账号类型后传入对应的账号标示ID调用接口完成登录业务.
         * （比如 `2`为邮箱账号 `3`为Facebook账号, `4`为Google账号等等）
         */
        loginParams["loginType"] = type

        // 调用SDK登录接口
        OmniSDK.instance.login(this, loginParams, object : LoginCallback {
            override fun onSuccess() {

                // 登录成功, 获取登录用户数据(Json字符串格式)
                val userJsonInfo: String = OmniSDK.instance.getUserInfo()!!

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)
                val userMap = Gson().fromJson<Map<String, Any>>(
                    userJsonInfo,
                    object : TypeToken<Map<String, Any>>() {}.type
                )

                // SDK账号唯一标示ID
                val uid = userMap["uid"].toString()

                // SDK账号Token值 (CP对接方需验证其有效性)
                val token = userMap["token"].toString()

                // CP方需使用该cpUid作为账号唯一标示ID
                val cpUid = userMap["cpUid"].toString()

                // 按照需求解析更多账号数据信息,比如是否为游客账号，账号昵称等等
                val accountType = userMap["type"].toString().toInt()
                if (1 == accountType) {
                    // 当前账号类型为纯游客账号
                    Log.i("SDK", "Guest Account")
                }
                val showName = userMap["showName"].toString()

                Log.e("SDK", "showName = $showName")

                // CP自己的代码，比如进入游戏业务
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 登录失败,请CP方按照自身逻辑进行业务处理
            }

            override fun onCancel() {
                // 玩家取消登录
            }
        })
    }

    /**
     * OMNI SDK 账号切换接口示例（按需求接入）
     *
     * @see User
     */
    fun switchAccount() {

        // 构建切换账号请求数据Map, 目前保留字段传空Map数据即可
        val switchAccountParams: Map<String, Any> = mutableMapOf()

        // 调用SDK账号切换接口
        OmniSDK.instance.switchAccount(this, switchAccountParams, object : SwitchAccountCallback {
            override fun onSuccess() {

                // 账号切换成功, 获取新切换的用户数据(Json字符串格式)
                val userJsonInfo: String = OmniSDK.instance.getUserInfo()!!

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)
                val userMap = Gson().fromJson<Map<String, Any>>(
                    userJsonInfo,
                    object : TypeToken<Map<String, Any>>() {}.type
                )

                // SDK账号唯一标示ID
                val uid = userMap["uid"].toString()

                // SDK账号Token值 (CP对接方需验证其有效性)
                val token = userMap["token"].toString()

                // CP方需使用该cpUid作为账号唯一标示ID
                val cpUid = userMap["cpUid"].toString()

                // CP自己的代码，比如退出当前游戏账号，使用新切换的账号重新开始游戏
            }

            override fun onFailure(pair: Pair<Int, String>) {
                Log.e("SDK", "err = $pair")
                // 切换失败,无需特别处理,回到游戏界面即可
            }

            override fun onCancel() {
                // 切换取消,无需特别处理,回到游戏界面即可
            }
        })
    }

    /**
     * OMNI SDK 账号绑定接口示例（按需求接入）
     *
     * @see User
     */
    fun bindAccount() {

        // 构建绑定账号请求数据Map, 目前保留字段传空Map数据即可
        val bindAccountParams: Map<String, Any> = mutableMapOf()

        // 调用SDK账号绑定接口
        OmniSDK.instance.bindAccount(this, bindAccountParams, object : BindAccountCallback {
            override fun onSuccess() {

                // 账号绑定成功,获取新绑定的用户数据(Json字符串格式)
                val userJsonInfo: String = OmniSDK.instance.getUserInfo()!!

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)

                // 进行账号Json数据解析(比如可以使用Gson将其转成Map数据结构来进行解析,或者其他方式)
                val userMap = Gson().fromJson<Map<String, Any>>(
                    userJsonInfo,
                    object : TypeToken<Map<String, Any>>() {}.type
                )

                // SDK账号唯一标示ID
                val uid = userMap["uid"].toString()

                // SDK账号Token值 (CP对接方需验证其有效性)
                val token = userMap["token"].toString()

                // CP方需使用该cpUid作为账号唯一标示ID
                val cpUid = userMap["cpUid"].toString()

                // CP自己的代码，比如回到游戏界面给玩家奖励
            }

            override fun onFailure(pair: Pair<Int, String>) {
                Log.e("SDK", "err = $pair")
                // 绑定失败
            }

            override fun onCancel() {
                // 绑定取消,无需特别处理
            }
        })
    }

    /**
     * OMNI SDK 账号注销登出接口示例（必须接入）.
     * 请在玩家在游戏内触发`登出`功能的时候先调用OMNI SDK 的登出接口.在收到成功回调后才可以执行CP自己的登出业务
     */
    fun logout() {

        OmniSDK.instance.logout(this, object : ResponseCallback {
            override fun onSuccess() {
                // Omni SDK已登出当前账号用户，游戏可以开始自己的登出业务逻辑

                // CP自己的代码，比如注销游戏自身的账号回到游戏开始登录的界面等等
            }

            override fun onFailure(pair: Pair<Int, String>) {
                // 登出失败,无需特别处理,回到游戏界面即可
            }
        })
    }

    /* ***************************** SDK 支付功能接口示例如下 ********************************** */

    /**
     * OMNI SDK 支付接口示例（若无特殊说明,SDK支付接口为必须接入接口）
     *
     * @see Product
     */
    fun pay() {

        val productId = "10010"          // 商品 ID (必传数据)
        val productName = "750 Diamonds" // 商品名称，有则传值，无则保持和商品ID一致
        val productDesc = "750 Diamonds" // 商品描述，有则传值，无则保持和商品ID一致
        val productPrice = 9.99          // 商品价格(单位为元,必传数据),比如9.99，0.99等等
        val payAmount = 9.99              // 实际支付总额(单位为元)
        val currency = "USD"             // 商品价格对应的货币单位（必传数据）, 比如 USD HKD 等等
        val serverId = "s01"             // 服务器 ID（必传数据），对于没有区服概念的游戏请直接传空字符串""
        val roleId = "r15996112_122"     // 游戏角色唯一标示ID（必传数据）

        // 游戏订单号ID.有则传值，没有则传空字符串""
        val gameTradeNo = "gamecno123456789xxx"

        // CP方服务器支付回调地址，有则传值，没有则传空字符串"".若传空字符串""则SDK将使用后台配置的回调地址
        val gameCallbackUrl = "https://game.sdk.server/android/payment"

        // CP方自定义扩展数据,会在回调CP方服务器支付数据的时候原样返回.有则传值，没有则传空字符串""
        val extJson = "{.}"

        // 创建待购买产品的数据实体
        val purchaseProduct = Product(
            productId,
            productName,
            productDesc,
            productPrice,
            payAmount,
            currency,
            serverId,
            roleId,
            gameTradeNo,
            gameCallbackUrl,
            extJson
        )

        // 调用SDK支付接口
        OmniSDK.instance.pay(this, purchaseProduct, object : PayCallback {
            override fun onSuccess(
                order: Order,
                codeMsg: Pair<Pair<Int, String>, Pair<Int, String>?>
            ) {
                // 支付成功，对于非单机游戏，CP方必须以OMNI SDK通知游戏服务器端的支付成功数据作为发货依据

                // OMNI SDK支付唯一订单号,CP方可按照自身需要使用该值来标示本次支付购买
                val orderId = order.orderId
            }

            override fun onFailure(
                order: Order?,
                codeMsg: Pair<Pair<Int, String>, Pair<Int, String>?>
            ) {
                Log.e("SDK", "err = $codeMsg")
                // 支付失败,CP方可以按照自身需求进行业务处理，比如弹出"提示支付失败"提示等等
            }

            override fun onCancel() {
                // 支付取消,无需特别处理
            }
        })
    }

    /* ***************************** SDK 社交功能接口示例如下 ********************************** */

    /**
     * OMNI SDK 社交分享(链接，图片等等)接口示例
     */
    fun share() {

        // 1. 创建分享参数
        val shareType = ShareType.IMAGE            // 分享类型，目前支持LINK, GROUP_LINK, IMAGE
        val title = "Join Us"                      // 分享标题文本
        val description = "Come to play with me"   // 分享具体描述文本
        val link = "https://play.google.com/games" // 分享内容URL链接地址,可选

        // 在分享图片的时候需要指定该值，目前支持类型为NETWORK（网络图片),LOCAL(本地图片uri),SCREENSHOT(截屏)
        val imageType = ShareImageType.LOCAL
        // 在分享图片的时候需要指定该值, 给出分享图片本地Uri或网络图片Uri地址
        val imageUri = "https://www.game/com/123.png"

        val shareParam = ShareParam(shareType, title, description, link, imageType, imageUri)

        // 2. 指定分享的社交平台，比如facebook,twitter
        val platform = "facebook"

        // 3. 调用SDK社交分享接口
        OmniSDK.instance.share(this, platform, shareParam, object : ResponseCallback {
            override fun onSuccess() {
                // 分享成功，CP方按自身需求执行之后业务比如给与奖励等等
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 分享失败
                Log.e("SDK", "err = $responseCode")
            }
        })
    }

    /**
     * OMNI SDK 游戏邀请接口示例
     */
    fun invite() {

        // 指定社交平台，比如facebook,twitter
        val platform = "facebook"

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
        val inviteJson = "{\"message\":\"invite-message\"}"

        //3. 调用SDK社交分享接口
        OmniSDK.instance.invite(this, platform, inviteJson, object : ResultCallback {
            override fun onSuccess(resultJson: String) {
                // 邀请享成功，CP方按自身需求执行之后业务比如给与奖励等等
                Log.i("SDK", "resultJson = $resultJson")
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 邀请失败
                Log.e("SDK", "err = $responseCode")
            }
        })
    }

    /**
     * OMNI SDK 获取当前玩家的社交账号信息接口示例
     */
    fun getSocialInfo() {

        // 指定社交平台，比如facebook,twitter
        val platform = "facebook"

        OmniSDK.instance.getSocialInfo(this, platform, object : ResultCallback {
            override fun onSuccess(resultJson: String) {
                try {
                    // 解析玩家社交账号信息数据
                    val socialInfo = Gson().fromJson(resultJson, SocialInfo::class.java)
                    val nickName: String = socialInfo.nickName // 昵称
                    val imageUrl: String = socialInfo.imageUrl // 头像ICON地址
                    val width: Int = socialInfo.width          // 头像宽度像素
                    val height: Int = socialInfo.height        // 头像高度像素
                    val gender: String = socialInfo.gender     // 性别 "1"为男性 "0"为女性
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 获取失败
                Log.e("SDK", "err = $responseCode")
            }
        })
    }

    /**
     * OMNI SDK 获取玩家游戏好友的社交账号信息接口示例
     */
    fun getFriendInfo() {

        // 指定社交平台，比如facebook,twitter
        val platform = "facebook"
        OmniSDK.instance.getFriendInfo(this, platform, object : ResultCallback {
            override fun onSuccess(resultJson: String) {
                try {
                    // 解析玩家游戏好友的社交账号信息数据
                    val friendsInList = Gson().fromJson<ArrayList<FriendInfo>>(
                        resultJson,
                        object : TypeToken<ArrayList<FriendInfo>>() {}.type
                    )
                    friendsInList.forEach { friendInfo ->
                        val id: String = friendInfo.id             // 好友社交平台账号ID
                        val nickName: String = friendInfo.nickName // 昵称
                        val imageUrl: String = friendInfo.imageUrl // 头像ICON地址
                        val width: Int = friendInfo.width          // 头像宽度像素
                        val height: Int = friendInfo.height        // 头像高度像素
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 获取失败
                Log.e("SDK", "err = $responseCode")
            }
        })
    }

}