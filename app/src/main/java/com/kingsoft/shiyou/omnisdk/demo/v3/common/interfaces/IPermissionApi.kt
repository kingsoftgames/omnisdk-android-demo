package com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces

import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ICallback

/**
 *
 * @author tangxiyong
 */
interface IPermissionApi {
    fun onExternalStorageImpl()
    fun onPhoneStateImpl()
    fun onFloatingWindowImpl()
    fun onLocationImpl()
    fun onApkInstallImpl()
    fun onBluetoothImpl()
}

interface IPermissionCallback : ICallback {
    fun onResult(
        allGranted: Boolean,
        deniedList: List<String>,
        grantedList:  List<String>,
    )

    fun showMessageDialog(content: String, title: String)
}