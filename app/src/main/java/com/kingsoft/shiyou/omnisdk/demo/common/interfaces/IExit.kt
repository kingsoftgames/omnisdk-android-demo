package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

/**
 * Description:
 *
 * @author: LuXing created on 2022/1/21 15:29
 *
 */
interface IExitApi {
    fun onExitImpl(hasGameCustomExitDialog: Boolean)
}

interface IExitCallback : ICallback {
    fun showExitUi()
}