package com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces

import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKLoginInfo

/**
 * Description: 账号相关接口
 *
 * @author: LuXing created on 2021/3/23 9:34
 *
 */
interface IAccountApi {
    fun loginImpl(type: Int)
    fun switchAccountImpl(type: Int)
    fun bindAccountImpl(type: Int)
    fun logoutImpl()
}

interface IAccountCallback : ICallback {
    fun onLoginSucceeded(loginInfo: OmniSDKLoginInfo)
    fun onBindSucceeded(loginInfo: OmniSDKLoginInfo)
    fun onSwitchSucceeded(loginInfo: OmniSDKLoginInfo)
    fun onDeleteSucceeded()
    fun onRestoreSucceeded(loginInfo: OmniSDKLoginInfo)
    fun onFailed(error: OmniSDKError)
    fun onCancelled()
    fun onLogoutSucceeded()
    fun onLogoutFailed(responseCode: Pair<Int, String>)
}