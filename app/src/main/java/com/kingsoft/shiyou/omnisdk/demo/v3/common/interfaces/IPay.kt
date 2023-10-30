package com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces

import com.kingsoft.shiyou.omnisdk.api.entity.Order
import com.kingsoft.shiyou.omnisdk.api.entity.SkuType
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKProductType
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ICallback

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/23 11:34
 *
 */
interface IPayApi {
    fun payImpl(
        skuType: OmniSDKProductType,
        productId: String,
        productName: String,
        productDesc: String,
        productPrice: Double,
        payAmount: Double,
        currency: String,
        serverId: String,
        roleId: String,
        gameTradeNo: String,
        gameCallbackUrl: String,
        extJson: String,
        zoneId: String,
        roleName: String,
        roleLevel: String,
        roleVipLevel: String
    )
}

interface IPayCallback : ICallback {
    fun onSucceeded(orderId: String)
    fun onFailed(error: OmniSDKError)
    fun onCancelled()
    fun onPreOrderCallback(msg: String)
}