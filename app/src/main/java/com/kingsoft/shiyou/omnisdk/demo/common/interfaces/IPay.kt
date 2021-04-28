package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

import com.kingsoft.shiyou.omnisdk.api.entity.Order

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/23 11:34
 *
 */
interface IPayApi {
    fun payImpl(
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
        extJson: String
    )
}

interface IPayCallback : ICallback {
    fun onSucceeded(order: Order)
    fun onFailed(codeMsg: Pair<Pair<Int, String>, Pair<Int, String>?>)
    fun onCancelled()
}