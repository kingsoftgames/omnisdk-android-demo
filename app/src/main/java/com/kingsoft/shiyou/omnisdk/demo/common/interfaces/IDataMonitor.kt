package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/24 9:57
 *
 */
interface IDataMonitorApi {

    fun addCommonAttributeImpl(key: String, value: String)

    fun onCreateRoleImpl(roleInfo: RoleInfo)
    fun onEnterGameImpl(roleInfo: RoleInfo)
    fun onRoleLevelUpImpl(roleInfo: RoleInfo)

    fun onNewUserMissionImpl(roleInfo: RoleInfo)

    fun onGameLoadResourceImpl()
    fun onGameLoadConfigImpl()

    fun onOpenAnnouncementImpl()
    fun onCloseAnnouncementImpl()

    fun onPayFinishImpl(payInfo: PayInfo)

    fun onMissionBeginImpl(
        roleInfo: RoleInfo,
        missionId: String,
        missionName: String,
        doMissionTimes: Int,
        roleCurrentPower: Int,
        customParams: String
    )

    fun onMissionSuccessImpl(
        roleInfo: RoleInfo,
        missionId: String,
        missionName: String,
        doMissionTimes: Int,
        roleCurrentPower: Int,
        customParams: String
    )

    fun onMissionFailImpl(
        roleInfo: RoleInfo,
        missionId: String,
        missionName: String,
        doMissionTimes: Int,
        roleCurrentPower: Int,
        customParams: String
    )

    fun onVirtualCurrencyPurchaseImpl(
        roleInfo: RoleInfo,
        amount: Int,
        virtualCurrencyType: String,
        virtualCurrencyTotal: Int,
        gameTradeNo: String
    )

    fun onVirtualCurrencyRewardImpl(
        roleInfo: RoleInfo,
        amount: Int,
        virtualCurrencyType: String,
        virtualCurrencyTotal: Int,
        gainChannel: String,
        gainChannelType: String,
        gameTradeNo: String
    )

    fun onVirtualCurrencyConsumeImpl(
        roleInfo: RoleInfo,
        amount: Int,
        virtualCurrencyType: String,
        virtualCurrencyTotal: Int,
        itemName: String,
        itemNum: Int,
        itemType: String
    )

    fun onPrivateFunCodeUseImpl(
        roleInfo: RoleInfo,
        funCode: String,
        funCodeDesc: String,
        funCodeType: String,
        funCodeBatchId: String
    )

    fun onPublicFunCodeUseImpl(
        roleInfo: RoleInfo,
        funCode: String,
        funCodeDesc: String,
        funCodeType: String,
        funCodeBatchId: String
    )

    fun onEventImpl(
        roleInfo: RoleInfo,
        eventId: String,
        eventDesc: String,
        eventVal: Int,
        eventBody: String
    )

    fun dataConsumeEvent(roleInfo: RoleInfo, consumeNum: Int)

}

interface IDataMonitorCallback : ICallback {
    fun onSucceeded(resultData: String)
    fun onFailed(responseCode: Pair<Int, String>)
}