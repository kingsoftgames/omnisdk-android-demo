package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

import com.kingsoft.shiyou.omnisdk.api.entity.ShareImageType
import com.kingsoft.shiyou.omnisdk.api.entity.ShareType

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/23 14:23
 *
 */
interface ISocialApi {
    fun shareImpl(
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
    fun onFailed(responseCode: Pair<Int, String>)
    fun onCancelled()
}