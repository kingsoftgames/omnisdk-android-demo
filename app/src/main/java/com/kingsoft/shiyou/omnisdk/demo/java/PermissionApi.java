package com.kingsoft.shiyou.omnisdk.demo.java;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.Permission2Callback;
import com.kingsoft.shiyou.omnisdk.api.entity.PermissionModel;
import com.kingsoft.shiyou.omnisdk.api.entity.PermissionRationaleModel;
import com.kingsoft.shiyou.omnisdk.api.entity.PermissionRequestModel;
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPermissionApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IPermissionCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请示例
 *
 * @author tangxiyong
 */
@Keep
public class PermissionApi implements IPermissionApi {
    private final String tag = "PermissionApi# ";

    private final Activity appActivity;
    private final IPermissionCallback callback;

    public PermissionApi(Activity activity, IPermissionCallback permissionCallback) {
        this.appActivity = activity;
        this.callback = permissionCallback;
    }

    @Override
    public void onExternalStorageImpl() {
        // 方式一，通过 PermissionRequestModel 组装
        List<String> permissions = new ArrayList<>();
        permissions.add("android.permission.WRITE_EXTERNAL_STORAGE");
        permissions.add("android.permission.READ_EXTERNAL_STORAGE");

        PermissionRationaleModel applyDialog = new PermissionRationaleModel("温馨提示", "(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请允许权限申请", "允许", "取消");
        PermissionRationaleModel guideDialog = new PermissionRationaleModel("温馨提示", "(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限", "去开启", "取消");
        PermissionRationaleModel sDialog = new PermissionRationaleModel("温馨提示", "(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限", "去开启", "取消");
        PermissionModel permissionModel = new PermissionModel(permissions, applyDialog, sDialog, guideDialog);

        List<PermissionModel> permissionList = new ArrayList<>();
        permissionList.add(permissionModel);

        PermissionRequestModel requestModel = new PermissionRequestModel(true, 48, permissionList);

        String requestJsonString = OmniUtils.toJson(requestModel);

        DemoLogger.i(tag, "onExternalStorageImpl-requestJsonString: " + requestJsonString);

        // 方式二，直接传 Json 数据
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.WRITE_EXTERNAL_STORAGE\",\"android.permission.READ_EXTERNAL_STORAGE\", \"android.permission.READ_PHONE_STATE\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";

        // 注册回调结果
        OmniSDK.getInstance().registerPermissionsCallback(new Permission2Callback() {
            @Override
            public void onResult(@NonNull String jsonString) {
                DemoLogger.i(tag, "onExternalStorageImpl: " + jsonString);
                callback.onResult(jsonString);
            }
        });

        // 申请权限
        OmniSDK.getInstance().requestPermissions(appActivity, requestJsonString);
//        OmniSDK.getInstance().requestPermissions(appActivity, jsonString);
    }

    @Override
    public void onPhoneStateImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.READ_PHONE_STATE\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";
        OmniSDK.getInstance().registerPermissionsCallback(new Permission2Callback() {
            @Override
            public void onResult(@NonNull String jsonString) {
                DemoLogger.i(tag, "onPhoneStateImpl: " + jsonString);
                callback.onResult(jsonString);
            }
        });
        OmniSDK.getInstance().requestPermissions(appActivity, jsonString);
    }

    @Override
    public void onFloatingWindowImpl() {
        // 方式一，通过 PermissionRequestModel 组装
        List<String> permissions = new ArrayList<>();
        permissions.add("android.permission.SYSTEM_ALERT_WINDOW");

        PermissionRationaleModel applyDialog = new PermissionRationaleModel("温馨提示", "(游戏)需要悬浮窗权限\\n\\n请允许权限申请", "允许", "取消");
        PermissionRationaleModel guideDialog = new PermissionRationaleModel("温馨提示", "(游戏)需要悬浮窗权限\\n\\n请在系统设置界面，点击-允许显示悬浮窗，开启悬浮窗权限", "去开启", "取消");
        PermissionRationaleModel specialDialog = new PermissionRationaleModel("温馨提示", "(游戏)系统限制，需要在设置界面开启悬浮窗权限\\n\\n请在设置界面选中(游戏)开启悬浮窗权限", "去开启", "取消");
        PermissionModel permissionModel = new PermissionModel(permissions, applyDialog, specialDialog, guideDialog);

        List<PermissionModel> permissionList = new ArrayList<>();
        permissionList.add(permissionModel);

        PermissionRequestModel requestModel = new PermissionRequestModel(true, 48, permissionList);

        String requestJsonString = OmniUtils.toJson(requestModel);

        DemoLogger.i(tag, "onFloatingWindowImpl-requestJsonString: " + requestJsonString);

        // 方式二，直接传 Json 数据
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.SYSTEM_ALERT_WINDOW\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要悬浮窗权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要悬浮窗权限\\n\\n请在设置界面选中(游戏)开启悬浮窗权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)系统限制，需要在设置界面开启悬浮窗权限\\n\\n请在设置界面选中(游戏)开启悬浮窗权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";

        // 注册回调结果
        OmniSDK.getInstance().registerPermissionsCallback(new Permission2Callback() {
            @Override
            public void onResult(@NonNull String jsonString) {
                DemoLogger.i(tag, "onFloatingWindowImpl: " + jsonString);
                callback.onResult(jsonString);
            }
        });

        // 申请权限
        OmniSDK.getInstance().requestPermissions(appActivity, requestJsonString);
//        OmniSDK.getInstance().requestPermissions(appActivity, jsonString);
    }

    @Override
    public void onLocationImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.ACCESS_FINE_LOCATION\",\"android.permission.ACCESS_COARSE_LOCATION\",\"android.permission.ACCESS_BACKGROUND_LOCATION\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\\n\\n请在系统设置界面-应用权限，开启定位权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)系统限制，需要继续开启后台定位，用于推荐本地好友\\n\\n请点击定位-始终允许，开启后台定位权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";
        OmniSDK.getInstance().registerPermissionsCallback(new Permission2Callback() {
            @Override
            public void onResult(@NonNull String jsonString) {
                DemoLogger.i(tag, "onLocationImpl: " + jsonString);
                callback.onResult(jsonString);
            }
        });
        OmniSDK.getInstance().requestPermissions(appActivity, jsonString);
    }

    @Override
    public void onApkInstallImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.REQUEST_INSTALL_PACKAGES\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请在系统设置界面，开启-允许来自此来源的应用，打开安装APK权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请在系统设置界面，开启-允许来自此来源的应用，打开安装APK权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";
        OmniSDK.getInstance().registerPermissionsCallback(new Permission2Callback() {
            @Override
            public void onResult(@NonNull String jsonString) {
                DemoLogger.i(tag, "onApkInstallImpl: " + jsonString);
                callback.onResult(jsonString);
            }
        });
        OmniSDK.getInstance().requestPermissions(appActivity, jsonString);
    }

    @Override
    public void onBluetoothImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.BLUETOOTH_CONNECT\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要蓝牙权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要蓝牙权限\\n\\n请在系统设置界面，开启蓝牙权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";
        OmniSDK.getInstance().registerPermissionsCallback(new Permission2Callback() {
            @Override
            public void onResult(@NonNull String jsonString) {
                DemoLogger.i(tag, "onApkInstallImpl: " + jsonString);
                callback.onResult(jsonString);
            }
        });
        if (Build.VERSION.SDK_INT >= 31) {
            // 大于必须申请，否则在Android 12 以上 蓝牙API 使用时会崩溃。
            OmniSDK.getInstance().requestPermissions(appActivity, jsonString);
        } else {
            // 低于不需要申请
            int bluetooth = OmniSDK.getInstance().getPermissionGrantedStatus(appActivity, Manifest.permission.BLUETOOTH);
            int bluetoothAdmin = OmniSDK.getInstance().getPermissionGrantedStatus(appActivity, Manifest.permission.BLUETOOTH_ADMIN);
            callback.showMessageDialog(
                    "SDK_INT<31, bluetooth=" + bluetooth + ", bluetoothAdmin=" + bluetoothAdmin + "\n\n值为0时代表权限被授予。",
                    "申请结果"
            );
        }
    }

    @Override
    public void onCustomApplyImpl(@NotNull String jsonString) {
        OmniSDK.getInstance().registerPermissionsCallback(new Permission2Callback() {
            @Override
            public void onResult(@NonNull String jsonString) {
                DemoLogger.i(tag, "onCustomApplyImpl: " + jsonString);
                callback.onResult(jsonString);
            }
        });
        OmniSDK.getInstance().requestPermissions(appActivity, jsonString);
    }
}
