package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Activity
import android.util.Log
import androidx.annotation.Keep
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.BindAccountCallback
import com.kingsoft.shiyou.omnisdk.api.callback.LoginCallback
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback
import com.kingsoft.shiyou.omnisdk.api.callback.SwitchAccountCallback
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountCallback

/**
 * Description: OMNI SDK账号API接口示例Demo
 *
 * @author: LuXing created on 2021/3/22 15:31
 *
 */
@Keep
class AccountApi(private val demoActivity: Activity, private val callback: IAccountCallback) :
    IAccountApi {

    /* ***************************** SDK 账号API接口示例如下 ********************************** */

    /**
     * OMNI SDK账号登录接口示例（若无特殊说明,SDK登录接口为必须接入接口）
     */
    private fun login() {

        // 构建登录请求数据Map
        val loginParams: MutableMap<String, Any> = mutableMapOf()

        /*
         * 指定登录类型,其key值必须为`loginType`,value为Int类型
         * `0`或者不设置表示不指定特定登录类型,直接使用OMNI SDK登录界面。
         * 非`0`表示CP使用自身的登录选项UI界面,在用户选择账号类型后传入对应的账号标示ID调用接口完成登录业务.
         * （比如 `2`为邮箱账号 `3`为Facebook账号, `4`为Google账号等等）
         */
        loginParams["loginType"] = mLoginType

        // 调用SDK登录接口
        OmniSDK.instance.login(demoActivity, loginParams, object : LoginCallback {
            override fun onSuccess() {

                // 登录成功, 获取登录账号数据(Json字符串格式)
                val userJson = OmniSDK.instance.getUserInfo()!!
                Log.i(tag, "userJson = $userJson")

                // 账号Json数据解析(可将其转成Map数据结构或者org.json.JSONObject方式来进行解析)
                val userMap: Map<String, Any> = OmniUtils.toMap(userJson)
                // SDK账号唯一标示ID
                val authUserId = userMap["uid"].toString()
                // 游戏CP方必须使用该uid作为账号唯一标示ID
                val cpUid = userMap["cpUid"].toString()
                // SDK账号Token值 (CP对接方需验证其有效性)
                val token = userMap["token"].toString()
                // 当前登录的账号类型 `1`为游客账号 `2`为邮箱账号 `3`Facebook账号 `4`Google账号等等
                val accountType = userMap["type"] as Int
                // 是否为游客账号
                val isGuestAccount = !(userMap["isRelated"] as Boolean)

                // 按照需求解析更多账号数据信息
                // CP自己的代码，比如进入游戏业务
                callback.onLoginSucceeded(userMap, accountType)
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 登录失败, CP方按照自身逻辑进行业务处理
                callback.onFailed(responseCode)
            }

            override fun onCancel() {
                // 登录取消
                callback.onCancelled()
            }
        })
    }

    /**
     * OMNI SDK账号切换接口示例（按需求接入）
     */
    private fun switchAccount() {

        // 构建切换账号请求数据Map
        val switchParams: MutableMap<String, Any> = mutableMapOf()

        /*
         * 指定切换账号类型,其key值必须为`switchType`,value为Int类型
         * `0`或者不设置表示不指定特定切换账号类型,直接使用OMNI SDK账号切换界面。
         * 非`0`表示CP使用自身的切换账号选项UI界面,在用户选择账号类型后传入对应的账号标示ID调用接口完成切换业务.
         * （比如 `2`为邮箱账号 `3`为Facebook账号, `4`为Google账号等等）
         */
        switchParams["switchType"] = mSwitchType

        // 调用SDK账号切换接口
        OmniSDK.instance.switchAccount(demoActivity, switchParams, object : SwitchAccountCallback {
            override fun onSuccess() {

                // 切换账号成功, 获取切换后的账号数据(Json字符串格式)
                val userJson = OmniSDK.instance.getUserInfo()!!
                // 账号Json数据解析(比如可以使用其将其转成Map数据结构来进行解析,或者其他方式)
                val userMap: Map<String, Any> = OmniUtils.toMap(userJson)

                // 如有需要，请参照登录成功回调(onSuccess)进行切换后的账号数据解析

                // CP自己的代码，比如退出当前游戏账号，使用新切换的账号重新开始游戏
                callback.onSwitchSucceeded(userMap)
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 切换失败,无需特别处理,回到游戏界面即可
                callback.onFailed(responseCode)
            }

            override fun onCancel() {
                // 切换取消,无需特别处理,回到游戏界面即可
                callback.onCancelled()
            }
        })
    }

    /**
     * OMNI SDK账号绑定接口示例（按需求接入）
     */
    private fun bindAccount() {

        // 构建绑定账号请求数据Map
        val bindParams: MutableMap<String, Any> = mutableMapOf()

        /*
         * 指定绑定账号类型,其key值必须为`bindType`,value为Int类型
         * `0`或者不设置表示不指定特定绑定账号类型,直接使用OMNI SDK账号绑定界面。
         * 非`0`表示CP使用自身的绑定账号选项UI界面,在用户选择账号类型后传入对应的账号标示ID调用接口完成绑定业务.
         * （比如 `2`为邮箱账号 `3`为Facebook账号, `4`为Google账号等等）
         */
        bindParams["bindType"] = mBindType

        // 调用SDK账号绑定接口
        OmniSDK.instance.bindAccount(demoActivity, bindParams, object : BindAccountCallback {
            override fun onSuccess() {

                // 绑定成功, 获取绑定后的账号数据(Json字符串格式)
                val userJson = OmniSDK.instance.getUserInfo()!!
                // 账号Json数据解析(比如可以使用其将其转成Map数据结构来进行解析,或者其他方式)
                val userMap: Map<String, Any> = OmniUtils.toMap(userJson)

                // 如有需要，请参照登录成功回调(onSuccess)进行绑定后的账号数据解析

                // CP自己的代码，比如给与玩家奖励等业务

                callback.onBindSucceeded(userMap)
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 绑定失败
                callback.onFailed(responseCode)
            }

            override fun onCancel() {
                // 绑定取消,无需特别处理
                callback.onCancelled()
            }
        })
    }

    /**
     * OMNI SDK 账号注销登出接口示例（必须接入）。
     * 请在玩家在游戏内触发`登出`功能的时候先调用OMNI SDK 的登出接口，
     * 当收到接口成功回调后才可以执行CP方自己的登出业务。
     */
    private fun logout() {
        OmniSDK.instance.logout(demoActivity, object : ResponseCallback {
            override fun onSuccess() {
                // OMNI SDK已登出当前账号用户，游戏可以开始自己的登出业务逻辑
                // CP自己的代码，比如注销游戏自身的账号回到游戏开始登录的界面等等
                callback.onLogoutSucceeded()
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 登出失败,无需特别处理,回到游戏界面即可
                callback.onLogoutFailed(responseCode)
            }
        })
    }

    /* ****************************************************************************************** */

    private var tag = "SDK: " + this::class.qualifiedName
    private var mLoginType = 0
    private var mSwitchType = 0
    private var mBindType = 0

    override fun loginImpl(type: Int) {
        mLoginType = type
        login()
    }

    override fun switchAccountImpl(type: Int) {
        mSwitchType = type
        switchAccount()
    }

    override fun bindAccountImpl(type: Int) {
        mBindType = type
        bindAccount()
    }

    override fun logoutImpl() = logout()

}