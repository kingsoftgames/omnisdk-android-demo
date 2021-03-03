package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorCallback

/**
 * Description: OMNI SDK事件数据监控追踪功能相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/11 17:08
 *
 */
class DataMonitorDemoView : DemoView, IDataMonitorCallback {

    private lateinit var dataMonitorApi: IDataMonitorApi

    private val trackConsumeAmountEt: EditText by EditTextDelegate()
    private val trackConsumeVirtualCurrencyTypeEt: EditText by EditTextDelegate()
    private val trackConsumeVirtualCurrencyAmountEt: EditText by EditTextDelegate()
    private val trackConsumeItemNameEt: EditText by EditTextDelegate()
    private val trackConsumeItemNumEt: EditText by EditTextDelegate()
    private val trackConsumeItemTypeEt: EditText by EditTextDelegate()

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

    override fun initView() {

        dataMonitorApi = ApiManager.instance.getDataMonitorApi(this)

        R.id.data_monitor_demo_view_create_game_role_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onCreateRoleImpl(appView.gameRole)
            }
        }
        R.id.data_monitor_demo_view_enter_game_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onEnterGameImpl(appView.gameRole)
            }
        }
        R.id.data_monitor_demo_view_level_up_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                dataMonitorApi.onRoleLevelUpImpl(appView.gameRole)
            }
        }
        R.id.data_monitor_demo_view_game_resource_load_event_btn.addClickListener {
            dataMonitorApi.onGameLoadResourceImpl()
        }

        trackConsumeAmountEt
        trackConsumeVirtualCurrencyTypeEt
        trackConsumeVirtualCurrencyAmountEt
        trackConsumeItemNameEt
        trackConsumeItemNumEt
        trackConsumeItemTypeEt
        R.id.data_monitor_demo_view_track_consume_event_btn.addClickListener {
            if (checkLoggedIn() && checkRoleInfo()) {
                trackVirtualCurrencyConsumeEvent()
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

    /**
     * 监控跟踪玩家的购买及消费事件。当玩家消费虚拟货币时，调用此接口
     */
    private fun trackVirtualCurrencyConsumeEvent() {

        // 消耗的虚拟货币数量,如:10、100、100
        val amount = try {
            trackConsumeAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法消耗的虚拟货币数量")
            return
        }

        // 虚拟货币名称，如：元宝、金币等
        val virtualCurrencyType = trackConsumeVirtualCurrencyTypeEt.content()

        // 虚拟货币总量,如：100、1000、10000
        val virtualCurrencyTotal = try {
            trackConsumeVirtualCurrencyAmountEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法消耗的虚拟货币总金额数量")
            return
        }

        // 消费项目（比如十连抽，购买体力等)
        val itemName = trackConsumeItemNameEt.content()

        // 消费项目的数量,如1、2、3
        val itemNum = try {
            trackConsumeItemNumEt.content().toInt()
        } catch (e: Exception) {
            appView.showToastMessage("非法消费项目的数量")
            return
        }

        //消费类型 比如道具
        val itemType = trackConsumeItemTypeEt.content()

        dataMonitorApi.onVirtualCurrencyConsumeImpl(
            appView.gameRole,
            amount,
            virtualCurrencyType,
            virtualCurrencyTotal,
            itemName,
            itemNum,
            itemType
        )

    }

    /**
     * 监控自定义事件，游戏可以通过此接口进行自定义事件数据上报及分析
     */
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

        dataMonitorApi.onEventImpl(appView.gameRole, eventId, eventDesc, eventVal, eventBody)
    }

    private fun checkLoggedIn() = if (appView.getUser().isNullOrEmpty()) {
        appView.showMessageDialog("用户未登录")
        false
    } else {
        true
    }

    private fun checkRoleInfo() =
        if (appView.gameRole.roleId.isBlank()) {
            appView.showMessageDialog("请先创建游戏角色数据")
            false
        } else {
            true
        }

    override fun onSucceeded(resultJson: String) {
        appView.showToastMessage(resultJson)
    }

    override fun onFailed(responseCode: Pair<Int, String>) {
        appView.showToastMessage(responseCode.toString())
    }
}