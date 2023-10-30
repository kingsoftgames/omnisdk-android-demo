package com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces

import android.app.Activity
import com.kingsoft.shiyou.omnisdk.api.entity.ShareImageType
import com.kingsoft.shiyou.omnisdk.api.entity.ShareType
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ICallback

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/23 14:23
 *
 */
interface ISocialApi {
    fun shareImpl(
        activity: Activity,
        platform: String,
        type: ShareType,
        title: String,
        description: String,
        link: String,
        imageType: ShareImageType,
        imageUri: String
    )

    fun inviteImpl(platform: String, json: String)

    fun getSocialInfoImpl(platform: String)

    fun getFriendInfoImpl(platform: String)
}

interface ISocialCallback : ICallback {
    fun onSucceeded(resultJson: String)
    fun onFailed(error: OmniSDKError)
    fun onCancelled()
}