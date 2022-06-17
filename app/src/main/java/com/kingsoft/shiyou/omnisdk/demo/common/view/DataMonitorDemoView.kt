package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.TestData
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorCallback
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger

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

    private val trackCommonKeyEt: EditText by EditTextDelegate()
    private val trackCommonValueEt: EditText by EditTextDelegate()
    private lateinit var commonDataTv: TextView

    private val payInfoEt: EditText by EditTextDelegate()

    private val trackMissionIdEt: EditText by EditTextDelegate()
    private val trackMissionNameEt: EditText by EditTextDelegate()
    private val trackMissionTimeEt: EditText by EditTextDelegate()
    private val trackMissionRolePowerEt: EditText by EditTextDelegate()
    private val trackMissionCustomParamsEt: EditText by EditTextDelegate()

    private val virtualCurrencyAmountEt: EditText by EditTextDelegate()
    private val virtualCurrencyTypeEt: EditText by EditTextDelegate()
    private val virtualCurrencyTotalAmountEt: EditText by EditTextDelegate()
    private val virtualCurrencyGainChannelEt: EditText by EditTextDelegate()
    private val virtualCurrencyGainChannelTypeEt: EditText by EditTextDelegate()
    private val virtualCurrencyConsumeItemNameEt: EditText by EditTextDelegate()
    private val virtualCurrencyConsumeItemNumEt: EditText by EditTextDelegate()
    private val virtualCurrencyConsumeItemTypeEt: EditText by EditTextDelegate()
    private val virtualCurrencyGameTradeNoEt: EditText by EditTextDelegate()

    private val funCodeIdEt: EditText by EditTextDelegate()
    private val funCodeDescEt: EditText by EditTextDelegate()
    private val funCodeTypeEt: EditText by EditTextDelegate()
    private val funCodeBatchIdEt: EditText by EditTextDelegate()

    private val trackEventIdEt: EditText by EditTextDelegate()
    private val trackEventDescriptionEt: EditText by EditTextDelegate()
    private val trackEventValueEt: EditText by EditTextDelegate()
    private val trackEventBodyEt: EditText by EditTextDelegate()

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
        trackCommonKeyEt
        trackCommonValueEt
        commonDataTv = findViewById(R.id.data_monitor_demo_view_track_common_data_tv)
        commonDataTv.text = gson.toJson(commonData)
        R.id.data_monitor_demo_view_track_common_create_btn.addClickListener {
            val k = trackCommonKeyEt.content()
            val v = trackCommonValueEt.content()
            if (k.isNotBlank()) {
                commonData[k] = v
                dataMonitorApi.addCommonAttributeImpl(k, v)
                commonDataTv.text = gson.toJson(commonData)
            } else {
                appView.showToastMessage("添加的数据无效")
            }
        }

        R.id.data_monitor_demo_view_create_game_role_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onCreateRoleImpl(appView.gameRoleInfo)
            }
        }
        R.id.data_monitor_demo_view_enter_game_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onEnterGameImpl(appView.gameRoleInfo)
            }
        }
        R.id.data_monitor_demo_view_level_up_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onRoleLevelUpImpl(appView.gameRoleInfo)
            }
        }
        R.id.data_monitor_demo_view_new_user_mission_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onNewUserMissionImpl(appView.gameRoleInfo)
            }
        }
        R.id.data_monitor_demo_view_game_resource_load_event_btn.addClickListener {
            dataMonitorApi.onGameLoadResourceImpl()
        }
        R.id.data_monitor_demo_view_game_config_load_event_btn.addClickListener {
            dataMonitorApi.onGameLoadConfigImpl()
        }
        R.id.data_monitor_demo_view_open_announcement_event_btn.addClickListener {
            dataMonitorApi.onOpenAnnouncementImpl()
        }
        R.id.data_monitor_demo_view_close_announcement_event_btn.addClickListener {
            dataMonitorApi.onCloseAnnouncementImpl()
        }
        R.id.data_monitor_demo_view_data_consume_event_btn.addClickListener {
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
        R.id.data_monitor_demo_view_pay_info_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackPayFinish()
            }
        }

        trackMissionIdEt
        trackMissionNameEt
        trackMissionTimeEt
        trackMissionRolePowerEt
        trackMissionCustomParamsEt
        R.id.data_monitor_demo_view_track_mission_start_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackMission("begin")
            }
        }
        R.id.data_monitor_demo_view_track_mission_end_succeeded_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackMission("end_0")
            }
        }
        R.id.data_monitor_demo_view_track_mission_end_failed_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackMission("end_1")
            }
        }

        virtualCurrencyAmountEt
        virtualCurrencyTypeEt
        virtualCurrencyTotalAmountEt
        virtualCurrencyGainChannelEt
        virtualCurrencyGainChannelTypeEt
        virtualCurrencyConsumeItemNameEt
        virtualCurrencyConsumeItemNumEt
        virtualCurrencyConsumeItemTypeEt
        virtualCurrencyGameTradeNoEt
        R.id.data_monitor_demo_view_virtual_currency_purchase_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackVirtualCurrencyPurchaseEvent()
            }
        }
        R.id.data_monitor_demo_view_virtual_currency_reward_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackVirtualCurrencyRewardEvent()
            }
        }
        R.id.data_monitor_demo_view_virtual_currency_consume_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackVirtualCurrencyConsumeEvent()
            }
        }

        funCodeIdEt
        funCodeDescEt
        funCodeTypeEt
        funCodeBatchIdEt
        R.id.data_monitor_demo_view_use_private_fun_code_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                useFunCode(true)
            }
        }
        R.id.data_monitor_demo_view_use_public_fun_code_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                useFunCode(false)
            }
        }

        trackEventIdEt
        trackEventDescriptionEt
        trackEventValueEt
        trackEventBodyEt
        R.id.data_monitor_demo_view_track_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackCustomEvent()
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

    /** 监控跟踪游戏内任务或关卡相关事件 */
    private fun trackMission(missionState: String) {
        val missionId = trackMissionIdEt.content()
        if (missionId.isBlank()) {
            appView.showToastMessage("任务标示ID不能为空")
            return
        }
        val missionName = trackMissionNameEt.content()
        if (missionName.isBlank()) {
            appView.showToastMessage("任务名称不能为空")
            return
        }
        val doMissionTimes = try {
            trackMissionTimeEt.content().toInt()
        } catch (e: Exception) {
            0
        }
        val roleCurrentPower = try {
            trackMissionRolePowerEt.content().toInt()
        } catch (e: Exception) {
            0
        }
        val customParams = trackMissionCustomParamsEt.content()

        DemoLogger.d("trackMission(${missionState}) = [ $missionId , $missionName , $doMissionTimes , $roleCurrentPower , $customParams ]")

        when (missionState) {
            "begin" -> {
                dataMonitorApi.onMissionBeginImpl(
                    appView.gameRoleInfo,
                    missionId,
                    missionName,
                    doMissionTimes,
                    roleCurrentPower,
                    customParams
                )
            }
            "end_0" -> {
                dataMonitorApi.onMissionSuccessImpl(
                    appView.gameRoleInfo,
                    missionId,
                    missionName,
                    doMissionTimes,
                    roleCurrentPower,
                    customParams
                )
            }
            "end_1" -> {
                dataMonitorApi.onMissionFailImpl(
                    appView.gameRoleInfo,
                    missionId,
                    missionName,
                    doMissionTimes,
                    roleCurrentPower,
                    customParams
                )
            }
        }
    }

    private fun trackVirtualCurrencyPurchaseEvent() {
        val amount = try {
            virtualCurrencyAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法虚拟货币数量")
            return
        }
        val type = virtualCurrencyTypeEt.content()
        val totalAmount = try {
            virtualCurrencyTotalAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法虚拟货币总数量")
            return
        }
        val gameTradeNo = virtualCurrencyGameTradeNoEt.content()
        DemoLogger.d("trackVirtualCurrencyPurchaseEvent = [ $amount , $type , $totalAmount, $gameTradeNo ]")
        dataMonitorApi.onVirtualCurrencyPurchaseImpl(
            appView.gameRoleInfo,
            amount,
            type,
            totalAmount,
            gameTradeNo
        )
    }

    private fun trackVirtualCurrencyRewardEvent() {
        val amount = try {
            virtualCurrencyAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法虚拟货币数量")
            return
        }
        val type = virtualCurrencyTypeEt.content()
        val totalAmount = try {
            virtualCurrencyTotalAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法虚拟货币总数量")
            return
        }
        val gainChannel = virtualCurrencyGainChannelEt.content()
        val gainChannelType = virtualCurrencyGainChannelTypeEt.content()
        val gameTradeNo = virtualCurrencyGameTradeNoEt.content()
        DemoLogger.d("trackVirtualCurrencyRewardEvent = [ $amount , $type , $totalAmount, $gainChannel , $gainChannelType , $gameTradeNo ]")
        dataMonitorApi.onVirtualCurrencyRewardImpl(
            appView.gameRoleInfo,
            amount,
            type,
            totalAmount,
            gainChannel,
            gainChannelType,
            gameTradeNo
        )
    }

    private fun trackVirtualCurrencyConsumeEvent() {
        val amount = try {
            virtualCurrencyAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法虚拟货币数量")
            return
        }
        val type = virtualCurrencyTypeEt.content()
        val totalAmount = try {
            virtualCurrencyTotalAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法虚拟货币总数量")
            return
        }

        val itemName = virtualCurrencyConsumeItemNameEt.content()
        val itemNum = try {
            virtualCurrencyConsumeItemNumEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法消费项目的数量")
            return
        }
        val itemType = virtualCurrencyConsumeItemTypeEt.content()

        DemoLogger.d("trackVirtualCurrencyConsumeEvent = [ $amount , $type , $totalAmount, $itemName , $itemNum , $itemType ]")
        dataMonitorApi.onVirtualCurrencyConsumeImpl(
            appView.gameRoleInfo,
            amount,
            type,
            totalAmount,
            itemName,
            itemNum,
            itemType
        )
    }

    private fun useFunCode(isPrivate: Boolean) {
        val funCodeId = funCodeIdEt.content()
        if (funCodeId.isBlank()) {
            appView.showToastMessage("功能码ID值不能为空")
            return
        }
        val funCodeDesc = funCodeDescEt.content()
        val funCodeType = funCodeTypeEt.content()
        val funCodeBatchId = funCodeBatchIdEt.content()

        DemoLogger.d("useFunCode(isPrivate = ${isPrivate}) = [ $funCodeId , $funCodeDesc , $funCodeType, $funCodeBatchId ]")

        if (isPrivate) {
            dataMonitorApi.onPrivateFunCodeUseImpl(
                appView.gameRoleInfo,
                funCodeId,
                funCodeDesc,
                funCodeType,
                funCodeBatchId
            )
        } else {
            dataMonitorApi.onPublicFunCodeUseImpl(
                appView.gameRoleInfo,
                funCodeId,
                funCodeDesc,
                funCodeType,
                funCodeBatchId
            )
        }
    }

    /** 监控自定义事件，游戏可以通过此接口进行自定义事件数据上报及分析 */
    private fun trackCustomEvent() {
        // 事件类型
        val eventId = trackEventIdEt.content()
        if (eventId.isBlank()) {
            appView.showToastMessage("非法事件ID标示")
            return
        }
        // 事件描述：比如：初始化成功、登录成功等
        val eventDesc = trackEventDescriptionEt.content()
        // 事件数值。XGSDK的统计平台可以对此数值进行sum／max／min／avg等操作
        val eventVal = try {
            trackEventValueEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法事件值")
            return
        }
        //事件详细定义，JSON格式，默认为""
        val eventBody = trackEventBodyEt.content()

        DemoLogger.d("trackCustomEvent = [ $eventId , $eventDesc , $eventVal, $eventBody ]")

        dataMonitorApi.onEventImpl(appView.gameRoleInfo, eventId, eventDesc, eventVal, eventBody)
    }

    private fun checkLoggedIn() = if (appView.getUser().isNullOrEmpty()) {
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