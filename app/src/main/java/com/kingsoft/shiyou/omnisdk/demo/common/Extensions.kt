package com.kingsoft.shiyou.omnisdk.demo.common

import android.text.Html
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.entity.Order
import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils.parseUserInfo

internal fun String.html() =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }

fun convert(order: Order): PayInfo {
    return PayInfo(
        parseUserInfo(OmniSDK.instance.getUserInfo()!!)!!.uid,
        "",
        "",
        "",
        "",
        0.0,
        0,
        0.0,
        0.0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        order.gameTradeNo,
        "",
        "",
        order.orderId
    )
}
