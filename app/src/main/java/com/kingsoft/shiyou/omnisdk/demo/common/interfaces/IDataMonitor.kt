package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo

/**
 * Description:
 *
 * @author: LuXing created on 2021/3/24 9:57
 *
 */
interface IDataMonitorApi {

    fun onCreateRoleImpl(roleInfo: RoleInfo)
    fun onEnterGameImpl(roleInfo: RoleInfo)
    fun onRoleLevelUpImpl(roleInfo: RoleInfo)
    fun onGameLoadResourceImpl()

    fun onVirtualCurrencyConsumeImpl(
        roleInfo: RoleInfo,
        amount: Int,
        virtualCurrencyType: String,
        virtualCurrencyTotal: Int,
        itemName: String,
        itemNum: Int,
        itemType: String
    )

    fun onEventImpl(
        roleInfo: RoleInfo,
        eventId: String,
        eventDesc: String,
        eventVal: Int,
        eventBody: String
    )
}

interface IDataMonitorCallback : ICallback {
    fun onSucceeded(resultJson: String)
    fun onFailed(responseCode: Pair<Int, String>)
}