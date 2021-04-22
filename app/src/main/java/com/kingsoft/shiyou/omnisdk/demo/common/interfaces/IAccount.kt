package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

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
    fun onLoginSucceeded(userMap: Map<String, Any>, type: Int)
    fun onBindSucceeded(userMap: Map<String, Any>)
    fun onSwitchSucceeded(userMap: Map<String, Any>)
    fun onFailed(responseCode: Pair<Int, String>)
    fun onCancelled()
    fun onLogoutSucceeded()
    fun onLogoutFailed(responseCode: Pair<Int, String>)
}