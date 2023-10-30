package com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces

import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ICallback

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

    fun onPayFinishImpl(payInfo: PayInfo)

    fun dataConsumeEvent(roleInfo: RoleInfo, consumeNum: Int)

}

interface IDataMonitorCallback : ICallback {
    fun onSucceeded(resultData: String)
    fun onFailed(responseCode: Pair<Int, String>)
}