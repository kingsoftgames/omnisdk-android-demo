package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Activity
import android.util.Log
import androidx.annotation.Keep
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.PayCallback
import com.kingsoft.shiyou.omnisdk.api.entity.Order
import com.kingsoft.shiyou.omnisdk.api.entity.Product
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayCallback

/**
 * Description: OMNI SDK支付API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 11:36
 *
 */
@Keep
class PayApi(private val demoActivity: Activity, private val callback: IPayCallback) : IPayApi {

    /* ******************************** SDK 支付API接口示例如下 *********************************** */

    private fun pay() {

        val productId = mProductId       // 商品ID（必传数据）
        val productName = mProductName   // 商品名称（有则传值，无则保持和商品ID一致）
        val productDesc = mProductDesc   // 商品描述（有则传值，无则保持和商品ID一致）
        val productPrice = mProductPrice // 商品价格（必传数据，单位为元，比如9.99，0.99等等）
        val payAmount = mPayAmount       // 实际支付总金额（单位为元）
        val currency = mCurrency         // 价格金额对应的货币单位（必传数据，比如USD，HKD等等）
        val serverId = mServerId         // 服务器ID（必传数据，对于没有服务器概念的游戏直接传字符串"0"）
        val roleId = mRoleId             // 游戏角色唯一标示ID（必传数据）
        val gameTradeNo = mGameTradeNo   // CP方自身的订单号（有则传值，没有则传空字符串""）
        /*
         * CP方服务器支付结果回调地址，有则传值，没有则传空字符串""
         * 若传空字符串""，则将使用SDK端后台配置的CP方服务器回调地址
         */
        val gameCallbackUrl = mGameCallbackUrl
        /*
         * CP方可自定义的扩展数据，SDK服务器将在回调CP方服务器支付结果的时候原样返回
         * 有则传值，没有则传空字符串""
         */
        val extJson = mExtJson

        // 创建支付商品数据实体
        val product = Product(
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

        // 调用SDK支付接口进行支付
        OmniSDK.instance.pay(demoActivity, product, object : PayCallback {
            override fun onSuccess(
                order: Order,
                codeMsg: Pair<Pair<Int, String>, Pair<Int, String>?>
            ) {
                /*
                 * 支付成功，CP对接方必须以OMNI SDK服务器端通知CP服务器端的支付成功数据作为发货依据。
                 *
                 */
                // OMNI SDK支付唯一订单号，CP方可按照自身需要使用该值来标示本次支付购买
                val orderId = order.orderId
                Log.i(tag, "pay successfully, orderId = $orderId")
                callback.onSucceeded(order)
            }

            override fun onFailure(
                order: Order?,
                codeMsg: Pair<Pair<Int, String>, Pair<Int, String>?>
            ) {
                // 支付失败, CP方可以按照自身需求进行业务处理，比如弹出"提示支付失败"提示等等
                callback.onFailed(codeMsg)
            }

            override fun onCancel() {
                // 支付取消, 无需特别处理
                callback.onCancelled()
            }
        })
    }

    /* ****************************************************************************************** */

    private var tag = "SDK: " + this::class.qualifiedName

    private var mProductId = ""
    private var mProductName = ""
    private var mProductDesc = ""
    private var mProductPrice = 0.0
    private var mPayAmount = 0.0
    private var mCurrency = ""
    private var mServerId = ""
    private var mRoleId = ""
    private var mGameTradeNo = ""
    private var mGameCallbackUrl = ""
    private var mExtJson = ""

    override fun payImpl(
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
    ) {
        mProductId = productId
        mProductName = productName
        mProductDesc = productDesc
        mProductPrice = productPrice
        mPayAmount = payAmount
        mCurrency = currency
        mServerId = serverId
        mRoleId = roleId
        mGameTradeNo = gameTradeNo
        mGameCallbackUrl = gameCallbackUrl
        mExtJson = extJson
        pay()
    }

}