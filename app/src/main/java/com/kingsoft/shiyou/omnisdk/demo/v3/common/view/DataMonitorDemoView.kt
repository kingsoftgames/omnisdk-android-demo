package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.v3.common.TestData
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IDataMonitorApi
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IDataMonitorCallback
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger

/**
 * Description: OMNI SDK事件数据监控追踪功能相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/11 17:08
 *
 */
class DataMonitorDemoView : DemoView, IDataMonitorCallback {

    private lateinit var dataMonitorApi: IDataMonitorApi

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    // 初始默认Default PayInfo数据
    @Volatile
    private var payInfo = PayInfo(
        "",
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
        "",
        "",
        "",
        ""
    )

    private val payInfoEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val commonData = mutableMapOf<String, String>()

    override fun initView() {
        dataMonitorApi = ApiManager.instance.getDataMonitorApi(appActivity, this)
        commonData.clear()

        R.id.v3_data_monitor_demo_view_create_game_role_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onCreateRoleImpl(appView.gameRoleInfo)
            }
        }
        R.id.v3_data_monitor_demo_view_enter_game_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onEnterGameImpl(appView.gameRoleInfo)
            }
        }
        R.id.v3_data_monitor_demo_view_level_up_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onRoleLevelUpImpl(appView.gameRoleInfo)
            }
        }

        R.id.v3_data_monitor_demo_view_data_consume_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                val num =  (0..10).random()
                appView.showToastMessage("数量：$num")
                dataMonitorApi.dataConsumeEvent(appView.gameRoleInfo, num)
            }

        }

        val initPayInfo = buildPayInfoFromJson(
            TestData.singletonInstance.getValue(
                this::class.simpleName,
                "payInfo"
            )
        )
        initPayInfo?.let {
            payInfo = it
        }
        payInfoEt.setText(gson.toJson(payInfo))
        R.id.v3_data_monitor_demo_view_pay_info_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackPayFinish()
            }
        }
    }

    private fun trackPayFinish() {
        val inputPayInfo = buildPayInfoFromJson(payInfoEt.content())
        if (inputPayInfo != null) {
            payInfo = inputPayInfo
            DemoLogger.d("Tracking : $payInfo")
            // 追踪支付成功完成事件
            dataMonitorApi.onPayFinishImpl(payInfo)
        } else {
            // 输入非法支付数据
            payInfoEt.setText(gson.toJson(payInfo))
            appView.showErrorDialog("PayInfo Json数据无效")
        }
    }


    private fun checkLoggedIn() = if (appView.getUser() == null) {
        appView.showToastMessage("请先进行账号登录", Toast.LENGTH_LONG)
        false
    } else {
        true
    }

    private fun checkRoleInfo() =
        if (appView.gameRoleInfo.roleId.isBlank()) {
            appView.showErrorDialog("请在账号管理模块中创建有效的游戏角色数据")
            false
        } else {
            true
        }

    private fun buildPayInfoFromJson(payInfoJson: String): PayInfo? {
        var inputPayInfo: PayInfo? = null
        if (payInfoJson.isNotBlank()) {
            kotlin.runCatching {
                DemoLogger.d("payInfoJson : $payInfoJson")
                inputPayInfo = gson.fromJson(payInfoJson, PayInfo::class.java)
            }.exceptionOrNull()?.printStackTrace()
        }
        return if (inputPayInfo != null && inputPayInfo!!.productId.isNotBlank()) {
            inputPayInfo
        } else {
            null
        }
    }

    override fun onSucceeded(resultData: String) {
        appView.showMessageDialog(resultData, "成功")
    }

    override fun onFailed(responseCode: Pair<Int, String>) {
        appView.showErrorDialog(responseCode.toString())
    }
}