package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.kingsoft.shiyou.omnisdk.api.entity.Order
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayCallback
import java.util.*

/**
 * Description: 支付相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/10 13:52
 *
 */
class PayDemoView : DemoView, IPayCallback {

    private lateinit var payApi: IPayApi

    private val productIdEt: EditText by EditTextDelegate()
    private val productNameEt: EditText by EditTextDelegate()
    private val productDescEt: EditText by EditTextDelegate()
    private val productPriceEt: EditText by EditTextDelegate()
    private val productTotalAmountEt: EditText by EditTextDelegate()
    private val productCurrencyEt: EditText by EditTextDelegate()
    private val serverIdEt: EditText by EditTextDelegate()
    private val roleIdEt: EditText by EditTextDelegate()
    private val gameCnoEt: EditText by EditTextDelegate()
    private val extDataEt: EditText by EditTextDelegate()
    private val callbackUrlEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {

        // 获取支付购买API接口实例
        payApi = ApiManager.instance.getPayApi(this)

        // 初始化输入EditText控件
        productIdEt
        productNameEt
        productDescEt
        productPriceEt
        productTotalAmountEt
        productCurrencyEt
        serverIdEt
        roleIdEt
        gameCnoEt
        callbackUrlEt
        extDataEt
        // 监听按钮点击
        R.id.pay_demo_view_create_cno_btn.addClickListener {
            generateGameCno()
        }
        R.id.pay_demo_view_pay_btn.addClickListener {
            if (checkPaymentData()) pay()
        }
    }

    /**
     * 检测支付数据的合法性
     */
    private fun checkPaymentData(): Boolean {
        if (appView.getUser().isNullOrEmpty()) {
            appView.showToastMessage("请先登录账号，再进行支付")
            return false
        }
        if (productIdEt.content().isBlank()) {
            appView.showToastMessage("产品ID不能为空")
            return false
        }
        if (productPriceEt.content().toDoubleOrNull() == null) {
            appView.showToastMessage("产品价格错误")
            return false
        }
        if (productTotalAmountEt.content().toDoubleOrNull() == null) {
            appView.showToastMessage("支付总金额错误")
            return false
        }
        if (productCurrencyEt.content().isBlank()) {
            appView.showToastMessage("产品价格货币单位不能为空")
            return false
        }
        if (roleIdEt.content().isBlank()) {
            appView.showToastMessage("玩家角色ID标示不能为空")
            return false
        }
        return true
    }

    // 支付
    private fun pay() {

        // 商品 ID (必传数据)
        val productId = productIdEt.content()

        // 商品名称，有则传值，无则保持和商品ID一致
        val productName = productNameEt.content()

        // 商品描述，有则传值，无则保持和商品ID一致
        val productDesc = productDescEt.content()

        // 商品价格(单位为元,必传数据),比如9.99，0.99等等
        val productPrice = productPriceEt.content().toDouble()

        // 实际支付的总金额(单位为元,必传数据)
        val payAmount = productTotalAmountEt.content().toDouble()

        // 商品价格对应的货币单位（必传数据）, 比如 USD HKD 等等
        val currency = productCurrencyEt.content()

        // 服务器 ID（必传数据），对于没有区服概念的游戏请直接传空字符串""
        val serverId = serverIdEt.content()

        // 游戏角色唯一标示ID（必传数据）
        val roleId = roleIdEt.content()

        // 游戏订单号ID.有则传值，没有则传空字符串""
        val gameTradeNo = gameCnoEt.content()

        // CP方服务器支付回调地址，有则传值，没有则传空字符串"".若传空字符串""则SDK将使用后台配置的回调地址
        val gameCallbackUrl = callbackUrlEt.content()

        // CP方自定义扩展数据,会在回调CP方服务器支付数据的时候原样返回.有则传值，没有则传空字符串""
        val extJson = extDataEt.content()

        payApi.payImpl(
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
    }

    private var gameCno: String = ""
        set(value) {
            field = value
            gameCnoEt.setText(value)
        }

    /**
     * 模拟订单号生成
     */
    private fun generateGameCno() {
        gameCno = "sdk_" + UUID.randomUUID().toString().replace("-", "")
        appView.showMessageDialog("订单号: $gameCno", "创建成功")
    }

    override fun onSucceeded(order: Order) {
        appView.showMessageDialog("订单号: ${order.orderId}", "支付成功")
    }

    override fun onFailed(codeMsg: Pair<Pair<Int, String>, Pair<Int, String>?>) {
        appView.showErrorDialog("`${codeMsg.first}` || `${codeMsg.second}`")
    }

    override fun onCancelled() {
        appView.showToastMessage("支付取消")
    }
}