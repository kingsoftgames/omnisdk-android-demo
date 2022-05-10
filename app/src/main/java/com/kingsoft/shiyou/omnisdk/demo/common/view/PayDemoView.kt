package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.kingsoft.shiyou.omnisdk.api.entity.Order
import com.kingsoft.shiyou.omnisdk.api.entity.SkuType
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPayCallback
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * Description: 支付相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/10 13:52
 *
 */
class PayDemoView : DemoView, IPayCallback {

    private lateinit var payApi: IPayApi

    @Volatile
    private var gameCno: String = ""
        set(value) {
            field = value
            gameTradeNoEt.setText(value)
        }

    @Volatile
    private var productSkuType: SkuType = SkuType.INAPP
    private val productIdEt: EditText by EditTextDelegate()
    private val productNameEt: EditText by EditTextDelegate()
    private val productDescEt: EditText by EditTextDelegate()
    private val productPriceEt: EditText by EditTextDelegate()
    private val payAmountEt: EditText by EditTextDelegate()
    private val currencyEt: EditText by EditTextDelegate()
    private val serverIdEt: EditText by EditTextDelegate()
    private val roleIdEt: EditText by EditTextDelegate()
    private val gameTradeNoEt: EditText by EditTextDelegate()
    private val gameCallbackUrlEt: EditText by EditTextDelegate()
    private val extJsonEt: EditText by EditTextDelegate()
    private val zoneIdEt: EditText by EditTextDelegate()
    private val roleNameEt: EditText by EditTextDelegate()
    private val roleLevelEt: EditText by EditTextDelegate()
    private val roleVipLevelEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        payApi = ApiManager.instance.getPayApi(appActivity, this)
        initProductSkuTypes()
        productIdEt
        productNameEt
        productDescEt
        productPriceEt
        payAmountEt
        currencyEt
        serverIdEt
        roleIdEt
        gameTradeNoEt
        gameCallbackUrlEt
        extJsonEt
        zoneIdEt
        roleNameEt
        roleLevelEt
        roleVipLevelEt
        R.id.pay_demo_view_create_cno_btn.addClickListener {
            generateGameCno()
            appView.showMessageDialog("订单号: $gameCno", "创建成功")
        }
        R.id.pay_demo_view_pay_btn.addClickListener {
            pay()
        }
        generateGameCno()
    }

    private fun initProductSkuTypes() {
        val skuTypes = ArrayList<String>()
        SkuType.values().forEach {
            skuTypes.add(it.name)
        }

        val skuTypesSpinner = findViewById<Spinner>(R.id.pay_demo_view_product_type_spinner)
        skuTypesSpinner.adapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, skuTypes)
        skuTypesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                (skuTypesSpinner.selectedItem as? String)?.let {
                    productSkuType = SkuType.valueOf(it)
                    DemoLogger.i(TAG, "selected productSkuType = $productSkuType")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun pay() {
        // 先检测必要的支付数据的合法性
        if (appView.getUser().isNullOrEmpty()) {
            appView.showToastMessage("请先登录账号，再进行支付")
            return
        }
        if (productIdEt.content().isBlank()) {
            appView.showToastMessage("产品ID不能为空")
            return
        }
        if (productPriceEt.content().toDoubleOrNull() == null) {
            appView.showToastMessage("产品价格错误")
            return
        }
        if (payAmountEt.content().toDoubleOrNull() == null) {
            appView.showToastMessage("支付总金额错误")
            return
        }
        if (roleIdEt.content().isBlank()) {
            appView.showToastMessage("玩家角色账号ID标示不能为空")
            return
        }

        // 开始支付
        payApi.payImpl(
            productSkuType,
            productIdEt.content(),
            productNameEt.content(),
            productDescEt.content(),
            productPriceEt.content().toDouble(),
            payAmountEt.content().toDouble(),
            currencyEt.content(),
            serverIdEt.content(),
            roleIdEt.content(),
            gameTradeNoEt.content(),
            gameCallbackUrlEt.content(),
            extJsonEt.content(),
            zoneIdEt.content(),
            roleNameEt.content(),
            roleLevelEt.content(),
            roleVipLevelEt.content()
        )

        // 模拟生成新的CP游戏方订单号，确保下次调用支付接口时其值的唯一性
        generateGameCno()
    }

    /** 模拟游戏对接方订单号生成 */
    private fun generateGameCno() {
        gameCno = "sdk_" + UUID.randomUUID().toString().replace("-", "")
    }

    override fun onSucceeded(order: Order) {
        MainScope().launch {
            cancelProcessingDialog()
        }
        appView.showMessageDialog("订单号: ${order.orderId}", "支付成功")
    }

    override fun onFailed(codeMsg: Pair<Pair<Int, String>, Pair<Int, String>?>) {
        MainScope().launch {
            cancelProcessingDialog()
        }
        // 注意200084 不要也不需要向用户展示，用户只需看到GP的提示就行；
        // 因为GP会在一段时间内重复发送这个code类型，但其实只有一个订单，
        // 游戏应该在用户订阅过一次后，隐藏对应的订阅商品
        if (codeMsg.first.first != 200084) {
            appView.showMessageDialog(
                "OmniSdkError: ${codeMsg.first} , ChannelError: ${codeMsg.second}",
                "支付失败"
            )
        }
    }

    override fun onCancelled() {
        MainScope().launch {
            cancelProcessingDialog()
        }
        appView.showToastMessage("支付取消")
    }
}