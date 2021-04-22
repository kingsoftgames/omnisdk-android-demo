package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Activity
import android.util.Log
import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback
import com.kingsoft.shiyou.omnisdk.api.callback.ResultCallback
import com.kingsoft.shiyou.omnisdk.api.entity.ShareImageType
import com.kingsoft.shiyou.omnisdk.api.entity.ShareParam
import com.kingsoft.shiyou.omnisdk.api.entity.ShareType
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.ISocialApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.ISocialCallback
import java.util.*

/**
 * Description: OMNI SDK社交功能相关API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 14:25
 *
 */
@Keep
class SocialApi(private val demoActivity: Activity, private val callback: ISocialCallback) :
    ISocialApi {

    /* ***************************** SDK 社交功能相关API接口示例 ********************************** */

    /**
     * 社交分享接口示例
     *
     * @param platform SDK支持的社交平台(如 "facebook")或指定特定的包名
     * @param type 分享类型
     * @param title 分享标题文本
     * @param description 分享具体描述文本
     * @param link 分享内容URL链接地址
     * @param imageType 分享的图片类型
     * @param imageUri 分享图片链接：本地Uri或网络图片
     *
     * @see ShareImageType
     */
    private fun share(
        platform: String,
        type: ShareType,
        title: String,
        description: String,
        link: String,
        imageType: ShareImageType,
        imageUri: String
    ) {

        // 创建分享数据实体
        val shareParam = ShareParam(type, title, description, link, imageType, imageUri)

        // 调用SDK社交分享接口进行分享
        OmniSDK.instance.share(demoActivity, platform, shareParam, object : ResponseCallback {
            override fun onSuccess() {
                // 分享成功，CP方按自身需求执行之后业务比如给与奖励等等
                Log.i(tag, "share successfully")
                callback.onSucceeded("success")
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 分享失败
                callback.onFailed(responseCode)
            }
        })

    }

    /**
     * 社交邀请接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     * @param json
     * ```json
     * {"message":"invite-message"}
     * ```
     */
    private fun invite(platform: String, json: String) {

        // 调用SDK社交邀请接口
        OmniSDK.instance.invite(demoActivity, platform, json, object : ResultCallback {
            override fun onSuccess(resultJson: String) {
                // 邀请成功，CP方按自身需求执行之后业务比如给与奖励等等
                callback.onSucceeded(resultJson)
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 邀请失败
                callback.onFailed(responseCode)
            }
        })
    }

    /**
     * 第三方平台用户账号信息
     *
     * @param id 第三方平台账号标示ID
     * @param nickName 第三方平台账号昵称
     * @param gender 男 `male` 女 `female`
     * @param imageUrl 用户头像Url地址
     * @param width 头像宽度像素
     * @param height 头像高度像素
     *
     */
    data class SocialAccountInfo(
        @Keep val id: String,
        @Keep val nickName: String,
        @Keep val gender: String,
        @Keep val imageUrl: String,
        @Keep val width: Int,
        @Keep val height: Int
    )

    /**
     * 获取社交平台自身账号信息接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     */
    private fun getSocialInfo(platform: String) {

        // 调用获取社交平台自身账号信息接口
        OmniSDK.instance.getSocialInfo(demoActivity, platform, object : ResultCallback {
            override fun onSuccess(resultJson: String) {
                try {
                    // 解析玩家社交账号信息数据
                    val socialAccountInfo = Gson().fromJson(
                        resultJson,
                        SocialAccountInfo::class.java
                    )
                    val id = socialAccountInfo.id             // 第三方平台账号标示ID
                    val nickName = socialAccountInfo.nickName // 第三方平台账号昵称
                    val gender = socialAccountInfo.gender     // 男 `male` 女 `female`
                    val imageUrl = socialAccountInfo.imageUrl // 用户头像Url地址
                    val width = socialAccountInfo.width       // 头像宽度像素
                    val height = socialAccountInfo.height     // 头像高度像素

                    Log.i(tag, socialAccountInfo.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                callback.onSucceeded(resultJson);
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 获取失败
                callback.onFailed(responseCode)
            }
        })
    }

    /**
     * 获取社交平台好友账号信息接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     */
    private fun getFriendInfo(platform: String) {

        // 调用获取社交平台好友账号信息接口
        OmniSDK.instance.getFriendInfo(demoActivity, platform, object : ResultCallback {
            override fun onSuccess(resultJson: String) {
                try {
                    // 解析玩家游戏好友的社交账号信息数据
                    val friendsInList = Gson().fromJson<ArrayList<SocialAccountInfo>>(
                        resultJson,
                        object : TypeToken<ArrayList<SocialAccountInfo>>() {}.type
                    )
                    friendsInList.forEach { socialAccountInfo ->
                        val id = socialAccountInfo.id             // 第三方平台账号标示ID
                        val nickName = socialAccountInfo.nickName // 第三方平台账号昵称
                        val imageUrl = socialAccountInfo.imageUrl // 用户头像Url地址
                        val width = socialAccountInfo.width       // 头像宽度像素
                        val height = socialAccountInfo.height     // 头像高度像素

                        Log.i(tag, socialAccountInfo.toString())
                    }
                    callback.onSucceeded(resultJson)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(responseCode: Pair<Int, String>) {
                // 获取失败
                callback.onFailed(responseCode)
            }
        })

    }

    /* ****************************************************************************************** */

    private var tag = "SDK: " + this::class.qualifiedName

    override fun shareImpl(
        platform: String,
        type: ShareType,
        title: String,
        description: String,
        link: String,
        imageType: ShareImageType,
        imageUri: String
    ) {
        share(platform, type, title, description, link, imageType, imageUri)
    }

    override fun inviteImpl(platform: String, json: String) {
        invite(platform, json)
    }

    override fun getSocialInfoImpl(platform: String) {
        getSocialInfo(platform)
    }

    override fun getFriendInfoImpl(platform: String) {
        getFriendInfo(platform)
    }

}