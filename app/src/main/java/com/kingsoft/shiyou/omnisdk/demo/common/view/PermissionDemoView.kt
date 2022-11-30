package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.entity.PermissionResultModel
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils.fromJson
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPermissionApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPermissionCallback

/**
 * @author tangxiyong
 */
class PermissionDemoView : DemoView, IPermissionCallback {

    private lateinit var permissionApi: IPermissionApi
    private val customApplyEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        permissionApi = ApiManager.instance.getPermissionApi(appActivity, this)
        customApplyEt
        R.id.permission_demo_view_storage_btn.addClickListener {
            permissionApi.onExternalStorageImpl()
        }
        R.id.permission_demo_view_phone_btn.addClickListener {
            permissionApi.onPhoneStateImpl()
        }

        R.id.permission_demo_view_window_btn.addClickListener {
            permissionApi.onFloatingWindowImpl()
        }

        R.id.permission_demo_view_location_btn.addClickListener {
            permissionApi.onLocationImpl()
        }

        R.id.permission_demo_view_install_btn.addClickListener {
            permissionApi.onApkInstallImpl()
        }

        R.id.permission_demo_view_bluetooth_btn.addClickListener {
            permissionApi.onBluetoothImpl()
        }

        R.id.permission_demo_view_btn.addClickListener {
            val json = customApplyEt.content()
            permissionApi.onCustomApplyImpl(json)
        }
    }

    private fun showMsg(result: PermissionResultModel) {
        val deniedList = result.deniedList.map {
            "${it}, 拒绝原因=${OmniSDK.instance.getPermissionGrantedStatus(appActivity, it)}"
        }
        appView.showMessageDialog(
            "allGranted: ${result.allGranted}\n\n" +
                    "同意:${result.grantedList}\n\n" +
                    "拒绝:${deniedList.joinToString("\n")}",
            "申请结果"
        )
    }

    override fun onResult(resultJson: String) {
        val model = fromJson(resultJson, PermissionResultModel::class.java)!!
        showMsg(model)
    }

    override fun showMessageDialog(content: String, title: String) {
        appView.showMessageDialog(content, title)
    }

}