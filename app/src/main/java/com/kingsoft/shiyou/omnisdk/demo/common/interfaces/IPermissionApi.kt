package com.kingsoft.shiyou.omnisdk.demo.common.interfaces

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
    fun onCustomApplyImpl(jsonString: String)
}

interface IPermissionCallback : ICallback {
    fun onResult(resultJson: String)
    fun showMessageDialog(content: String, title:String)
}