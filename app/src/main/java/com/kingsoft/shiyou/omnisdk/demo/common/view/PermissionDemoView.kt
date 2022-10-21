package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.Manifest
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.Permission2Callback
import com.kingsoft.shiyou.omnisdk.api.entity.PermissionResultModel
import com.kingsoft.shiyou.omnisdk.basic.LogUtils
import com.kingsoft.shiyou.omnisdk.basic.fromJson
import com.kingsoft.shiyou.omnisdk.demo.R


/**
 * Description:
 *
 * @author: LuXing created on 2022/1/21 15:36
 *
 */
class PermissionDemoView : DemoView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        R.id.permission_demo_view_storage_btn.addClickListener {
            val json =
                "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.WRITE_EXTERNAL_STORAGE\",\"android.permission.READ_EXTERNAL_STORAGE\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}"
            OmniSDK.instance.registerPermissionsCallback(object : Permission2Callback {
                override fun onResult(jsonString: String) {
                    LogUtils.d({ TAG }, { "jsonString: $jsonString" })
                    val result = jsonString.fromJson<PermissionResultModel>()!!
                    showMsg(result)
                }
            })
            OmniSDK.instance.requestPermissions(appActivity, json)
        }
        R.id.permission_demo_view_phone_btn.addClickListener {
            val json =
                "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.READ_PHONE_STATE\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}"
            OmniSDK.instance.registerPermissionsCallback(object : Permission2Callback {
                override fun onResult(jsonString: String) {
                    LogUtils.d({ TAG }, { "jsonString: $jsonString" })
                    val result = jsonString.fromJson<PermissionResultModel>()!!
                    showMsg(result)
                }
            })
            OmniSDK.instance.requestPermissions(appActivity, json)
        }

        R.id.permission_demo_view_window_btn.addClickListener {
            val json =
                "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.SYSTEM_ALERT_WINDOW\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要悬浮窗权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要悬浮窗权限\\n\\n请在设置界面选中(游戏)开启悬浮窗权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)系统限制，需要在设置界面开启悬浮窗权限\\n\\n请在设置界面选中(游戏)开启悬浮窗权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}"
            OmniSDK.instance.registerPermissionsCallback(object : Permission2Callback {
                override fun onResult(jsonString: String) {
                    LogUtils.d({ TAG }, { "jsonString: $jsonString" })
                    val result = jsonString.fromJson<PermissionResultModel>()!!
                    showMsg(result)
                }
            })
            OmniSDK.instance.requestPermissions(appActivity, json)
        }

        R.id.permission_demo_view_location_btn.addClickListener {
            val json =
                "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.ACCESS_FINE_LOCATION\",\"android.permission.ACCESS_COARSE_LOCATION\",\"android.permission.ACCESS_BACKGROUND_LOCATION\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\\n\\n请在系统设置界面-应用权限，开启定位权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)系统限制，需要继续开启后台定位，用于推荐本地好友\\n\\n请点击定位-始终允许，开启后台定位权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}"
            OmniSDK.instance.registerPermissionsCallback(object : Permission2Callback {
                override fun onResult(jsonString: String) {
                    LogUtils.d({ TAG }, { "jsonString: $jsonString" })
                    val result = jsonString.fromJson<PermissionResultModel>()!!
                    showMsg(result)
                }
            })
            OmniSDK.instance.requestPermissions(appActivity, json)
        }

        R.id.permission_demo_view_install_btn.addClickListener {
            val json =
                "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.REQUEST_INSTALL_PACKAGES\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请在系统设置界面，开启-允许来自此来源的应用，打开安装APK权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请在系统设置界面，开启-允许来自此来源的应用，打开安装APK权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}"
            OmniSDK.instance.registerPermissionsCallback(object : Permission2Callback {
                override fun onResult(jsonString: String) {
                    LogUtils.d({ TAG }, { "jsonString: $jsonString" })
                    val result = jsonString.fromJson<PermissionResultModel>()!!
                    showMsg(result)
                }
            })
            OmniSDK.instance.requestPermissions(appActivity, json)
        }


        R.id.permission_demo_view_bluetooth_btn.addClickListener {
            val json =
                "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.BLUETOOTH_CONNECT\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要蓝牙权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要蓝牙权限\\n\\n请在系统设置界面，开启蓝牙权限(权限-连接附近的设备)\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}"
            OmniSDK.instance.registerPermissionsCallback(object : Permission2Callback {
                override fun onResult(jsonString: String) {
                    LogUtils.d({ TAG }, { "jsonString: $jsonString" })
                    val result = jsonString.fromJson<PermissionResultModel>()!!
                    showMsg(result)
                }
            })
            if (Build.VERSION.SDK_INT >= 31) {
                // 大于必须申请，否则在Android 12 以上 蓝牙API 使用时会崩溃。
                OmniSDK.instance.requestPermissions(appActivity, json)
            } else {
                // 低于不需要申请
                val bluetooth = OmniSDK.instance.getPermissionGrantedStatus(
                    appActivity,
                    Manifest.permission.BLUETOOTH
                )
                val bluetoothAdmin = OmniSDK.instance.getPermissionGrantedStatus(
                    appActivity,
                    Manifest.permission.BLUETOOTH_ADMIN
                )

                appView.showMessageDialog(
                    "SDK_INT<31, bluetooth=${bluetooth}, bluetoothAdmin=${bluetoothAdmin}\n\n值为0时权限被授予。",
                    "申请结果"
                )
            }
        }
    }

    private fun showMsg(result: PermissionResultModel) {
        val deniedList = result.deniedList.map {
            "${it}=${OmniSDK.instance.getPermissionGrantedStatus(appActivity, it)}"
        }
        appView.showMessageDialog(
            "allGranted: ${result.allGranted}\n\n" +
                    "同意:${result.grantedList}\n\n" +
                    "拒绝:${deniedList.joinToString("\n")}",
            "申请结果"
        )
    }

}