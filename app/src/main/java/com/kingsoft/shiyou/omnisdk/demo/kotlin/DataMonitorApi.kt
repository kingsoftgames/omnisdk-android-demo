package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.app.Activity
import androidx.annotation.Keep
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Description: OMNI SDK事件数据监控追踪API接口示例Demo
 *
 * @author: LuXing created on 2021/3/24 10:00
 *
 */
@Keep
class DataMonitorApi(
    private val demoActivity: Activity,
    private val callback: IDataMonitorCallback
) :
    IDataMonitorApi {

    /* *************************** SDK 事件数据监控追踪API接口示例如下 *************************** */

    /**
     * 监控角色创建事件接口示例
     * @param roleInfo 玩家角色信息
     *
     * @see RoleInfo
     */
    private fun onCreateRole(roleInfo: RoleInfo) {
        OmniSDK.instance.onCreateRole(roleInfo)

        callback.onSucceeded("Monitor(Tracking) Event Is Done")
    }

    /**
     * 监控角色进入游戏事件接口示例
     * @param roleInfo 玩家角色信息
     *
     * @see RoleInfo
     */
    private fun onEnterGame(roleInfo: RoleInfo) {
        OmniSDK.instance.onEnterGame(roleInfo)

        callback.onSucceeded("Monitor(Tracking) Event Is Done")
    }

    /**
     * 监控角色升级事件接口示例
     * @param roleInfo 玩家角色信息
     *
     * @see RoleInfo
     */
    private fun onRoleLevelUp(roleInfo: RoleInfo) {
        OmniSDK.instance.onRoleLevelUp(roleInfo)

        callback.onSucceeded("Monitor(Tracking) Event Is Done")
    }

    /**
     * 监控游戏资源加载事件接口示例
     */
    private fun onGameLoadResource() {
        OmniSDK.instance.onGameLoadResource()

        callback.onSucceeded("Monitor(Tracking) Event Is Done")
    }

    /**
     * 监控玩家消费虚拟货币事件接口示例
     * @param roleInfo [RoleInfo]
     * @param amount 虚拟货币数量,如:10、100、100
     * @param virtualCurrencyType 虚拟货币名称，如：元宝、金币等
     * @param virtualCurrencyTotal 虚拟货币总量,如：100、1000、10000
     * @param itemName 消费项目（比如十连抽，购买体力等)
     * @param itemNum 消费项目的数量,如1、2、3
     * @param itemType 消费类型 比如道具
     *
     * @see RoleInfo
     */
    private fun onVirtualCurrencyConsume(
        roleInfo: RoleInfo,
        amount: Int,
        virtualCurrencyType: String,
        virtualCurrencyTotal: Int,
        itemName: String,
        itemNum: Int,
        itemType: String
    ) {
        OmniSDK.instance.onVirtualCurrencyConsume(
            roleInfo,
            amount,
            virtualCurrencyType,
            virtualCurrencyTotal,
            itemName,
            itemNum,
            itemType
        )

        callback.onSucceeded("Monitor(Tracking) Event Is Done")
    }

    /**
     * 监控自定义事件接口（游戏可以通过此接口进行自助数据上报及分析）
     * @param roleInfo [RoleInfo]
     * @param eventId 事件类型
     * @param eventDesc 事件描述：比如：初始化成功、登录成功等
     * @param eventVal 事件数值。XGSDK的统计平台可以对此数值进行sum／max／min／avg等操作
     * @param eventBody － 事件详细定义，JSON格式，默认为""
     *
     * @see RoleInfo
     */
    private fun onEvent(
        roleInfo: RoleInfo,
        eventId: String,
        eventDesc: String,
        eventVal: Int,
        eventBody: String
    ) {
        OmniSDK.instance.onEvent(roleInfo, eventId, eventDesc, eventVal, eventBody)

        callback.onSucceeded("Monitor(Tracking) Event Is Done")
    }

    /* ****************************************************************************************** */

    private var tag = "SDK: " + this::class.qualifiedName
    private var scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateRoleImpl(roleInfo: RoleInfo) {
        scope.launch {
            onCreateRole(roleInfo)
        }
    }

    override fun onEnterGameImpl(roleInfo: RoleInfo) {
        scope.launch {
            onEnterGame(roleInfo)
        }
    }

    override fun onRoleLevelUpImpl(roleInfo: RoleInfo) {
        scope.launch {
            onRoleLevelUp(roleInfo)
        }
    }

    override fun onGameLoadResourceImpl() {
        scope.launch {
            onGameLoadResource()
        }
    }

    override fun onVirtualCurrencyConsumeImpl(
        roleInfo: RoleInfo,
        amount: Int,
        virtualCurrencyType: String,
        virtualCurrencyTotal: Int,
        itemName: String,
        itemNum: Int,
        itemType: String
    ) {
        scope.launch {
            onVirtualCurrencyConsume(
                roleInfo,
                amount,
                virtualCurrencyType,
                virtualCurrencyTotal,
                itemName,
                itemNum,
                itemType
            )
        }
    }

    override fun onEventImpl(
        roleInfo: RoleInfo,
        eventId: String,
        eventDesc: String,
        eventVal: Int,
        eventBody: String
    ) {
        scope.launch {
            onEvent(roleInfo, eventId, eventDesc, eventVal, eventBody)
        }
    }
}