package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.content.Context
import android.util.AttributeSet
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKGetPermissionOptions
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IPermissionApi
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IPermissionCallback

/**
 * @author tangxiyong
 */
class PermissionDemoView : DemoView, IPermissionCallback {

    private lateinit var permissionApi: IPermissionApi

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        permissionApi = ApiManager.instance.getPermissionApi(appActivity, this)

        R.id.v3_permission_demo_view_storage_btn.addClickListener {
            permissionApi.onExternalStorageImpl()
        }
        R.id.v3_permission_demo_view_phone_btn.addClickListener {
            permissionApi.onPhoneStateImpl()
        }

        R.id.v3_permission_demo_view_window_btn.addClickListener {
            permissionApi.onFloatingWindowImpl()
        }

        R.id.v3_permission_demo_view_location_btn.addClickListener {
            permissionApi.onLocationImpl()
        }

        R.id.v3_permission_demo_view_install_btn.addClickListener {
            permissionApi.onApkInstallImpl()
        }

        R.id.v3_permission_demo_view_bluetooth_btn.addClickListener {
            permissionApi.onBluetoothImpl()
        }

    }

    override fun onResult(
        allGranted: Boolean,
        deniedList: List<String>,
        grantedList: List<String>
    ) {
        val deniedListTransition =deniedList.map {
            // 低于不需要申请
            val opts = OmniSDKGetPermissionOptions.builder()
                .permission(it)
                .build()
            "${it}, 拒绝原因=${OmniSDKv3.instance.getPermissionGrantedStatus(appActivity, opts)}"
        }
        appView.showMessageDialog(
            "allGranted: ${allGranted}\n\n" +
                    "同意:${grantedList}\n\n" +
                    "拒绝:${deniedListTransition.joinToString("\n")}",
            "申请结果"
        )
    }


    override fun showMessageDialog(content: String, title: String) {
        appView.showMessageDialog(content, title)
    }

}