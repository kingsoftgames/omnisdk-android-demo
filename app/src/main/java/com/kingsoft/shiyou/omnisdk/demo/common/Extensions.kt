package com.kingsoft.shiyou.omnisdk.demo.common

import android.text.Html
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.entity.Order
import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo
import com.kingsoft.shiyou.omnisdk.api.entity.Product
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils.parseUserInfo
import com.kingsoft.shiyou.omnisdk.basic.printDebugStackTrace
import java.io.File
import java.io.FileInputStream

internal fun String.html() =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }

fun <T> fromJson(file: File, classOfT: Class<T>): T? {
    try {
        return Gson().fromJson(FileInputStream(file).reader(), classOfT)
    } catch (e: JsonParseException) {
        printDebugStackTrace { e }
    } catch (e: JsonSyntaxException) {
        printDebugStackTrace { e }
    } catch (e: Exception) {
        printDebugStackTrace { e }
    }
    return null
}

fun convert(product: Product, order: Order): PayInfo {
    return PayInfo(
        parseUserInfo(OmniSDK.instance.getUserInfo()!!)!!.uid,
        product.productId,
        product.productName,
        product.productDesc,
        "",
        product.productPrice,
        1,
        product.payAmount,
        product.payAmount,
        product.currency,
        product.roleId,
        product.roleName ?: "",
        product.roleLevel ?: "0",
        product.roleVipLevel ?: "0",
        product.serverId,
        product.zoneId ?: "",
        "",
        "",
        "",
        order.gameTradeNo,
        "",
        "",
        order.orderId
    )
}
