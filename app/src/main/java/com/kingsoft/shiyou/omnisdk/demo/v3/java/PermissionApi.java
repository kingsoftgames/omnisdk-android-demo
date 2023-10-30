package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.entity.PermissionModel;
import com.kingsoft.shiyou.omnisdk.api.entity.PermissionRationaleModel;
import com.kingsoft.shiyou.omnisdk.api.entity.PermissionRequestModel;
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils;
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKGetPermissionOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKPermissionRationaleOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKRequestPermissionsOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKRequestPermissionsResult;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IPermissionApi;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IPermissionCallback;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger;

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
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.WRITE_EXTERNAL_STORAGE\",\"android.permission.READ_EXTERNAL_STORAGE\", \"android.permission.READ_PHONE_STATE\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        OmniSDKPermissionRationaleOptions applyDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请允许权限申请")
                .ok("允许")
                .cancel("取消")
                .build();

        OmniSDKPermissionRationaleOptions guideDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要访问手机的存储空间，用于游戏资源更新等\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限")
                .ok("去开启")
                .cancel("取消")
                .build();

        OmniSDKRequestPermissionsOptions opts = OmniSDKRequestPermissionsOptions
                .builder()
                .showApplyDialog(true)
                .timeLimit(48)
                .permissionsList(permissions)
                .applyDialog(applyDialog)
                .guideToSettingDialog(guideDialog)
                .build();
        OmniSDKv3.getInstance().requestPermissions(appActivity, opts, result -> {
            if (result.isSuccess()) {
                OmniSDKRequestPermissionsResult requestResult = result.get();
                DemoLogger.i(tag, "onExternalStorageImpl: " + result.get());
                callback.onResult(requestResult.getAllGranted(),requestResult.getDeniedList(),requestResult.getGrantedList());
            } else {
                OmniSDKError error = result.error();
                Log.e(tag, "onExternalStorageImpl error:" + error);
            }
        });

    }

    @Override
    public void onPhoneStateImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.READ_PHONE_STATE\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请在系统设置界面-应用权限，开启访问存储空间的权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_PHONE_STATE);

        OmniSDKPermissionRationaleOptions applyDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请允许权限申请")
                .ok("允许")
                .cancel("取消")
                .build();

        OmniSDKPermissionRationaleOptions guideDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要获取设备唯一标识码用于用户识别、统计\\n\\n请在系统设置界面-应用权限，开启访问唯一识别码的权限")
                .ok("去开启")
                .cancel("取消")
                .build();

        OmniSDKRequestPermissionsOptions opts = OmniSDKRequestPermissionsOptions
                .builder()
                .showApplyDialog(true)
                .timeLimit(48)
                .permissionsList(permissions)
                .applyDialog(applyDialog)
                .guideToSettingDialog(guideDialog)
                .build();
        OmniSDKv3.getInstance().requestPermissions(appActivity, opts, result -> {
            if (result.isSuccess()) {
                OmniSDKRequestPermissionsResult requestResult = result.get();
                DemoLogger.i(tag, "onPhoneStateImpl: " + result.get());
                callback.onResult(requestResult.getAllGranted(),requestResult.getDeniedList(),requestResult.getGrantedList());
            } else {
                OmniSDKError error = result.error();
                Log.e(tag, "onPhoneStateImpl error:" + error);
            }
        });
    }

    @Override
    public void onFloatingWindowImpl() {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW);

        OmniSDKPermissionRationaleOptions applyDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要悬浮窗权限\n\n请允许权限申请")
                .ok("允许")
                .cancel("取消")
                .build();

        OmniSDKPermissionRationaleOptions guideDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要悬浮窗权限\n\n请在系统设置界面，点击-允许显示悬浮窗，开启悬浮窗权限")
                .ok("去开启")
                .cancel("取消")
                .build();

        OmniSDKRequestPermissionsOptions opts = OmniSDKRequestPermissionsOptions
                .builder()
                .showApplyDialog(true)
                .permissionsList(permissions)
                .applyDialog(applyDialog)
                .guideToSettingDialog(guideDialog)
                .timeLimit(48)
                .build();
        OmniSDKv3.getInstance().requestPermissions(appActivity, opts, result -> {
            if (result.isSuccess()) {
                 OmniSDKRequestPermissionsResult requestResult = result.get();
                DemoLogger.i(tag, "onFloatingWindowImpl: " + result.get());
                callback.onResult(requestResult.getAllGranted(),requestResult.getDeniedList(),requestResult.getGrantedList());
            } else {
                OmniSDKError error = result.error();
                Log.e(tag, "onFloatingWindowImpl error:" + error);
            }
        });
    }

    @Override
    public void onLocationImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.ACCESS_FINE_LOCATION\",\"android.permission.ACCESS_COARSE_LOCATION\",\"android.permission.ACCESS_BACKGROUND_LOCATION\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\\n\\n请在系统设置界面-应用权限，开启定位权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)系统限制，需要继续开启后台定位，用于推荐本地好友\\n\\n请点击定位-始终允许，开启后台定位权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        OmniSDKPermissionRationaleOptions applyDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\n\n请允许权限申请")
                .ok("允许")
                .cancel("取消")
                .build();

        OmniSDKPermissionRationaleOptions guideDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要获取模糊、精确、后台定位，用于推荐本地好友\n\n请在系统设置界面-应用权限，开启定位权限")
                .ok("去开启")
                .cancel("取消")
                .build();

        OmniSDKRequestPermissionsOptions opts = OmniSDKRequestPermissionsOptions
                .builder()
                .showApplyDialog(true)
                .permissionsList(permissions)
                .applyDialog(applyDialog)
                .guideToSettingDialog(guideDialog)
                .timeLimit(48)
                .build();
        OmniSDKv3.getInstance().requestPermissions(appActivity, opts, result -> {
            if (result.isSuccess()) {
                OmniSDKRequestPermissionsResult requestResult = result.get();
                DemoLogger.i(tag, "onLocationImpl: " + result.get());
                callback.onResult(requestResult.getAllGranted(),requestResult.getDeniedList(),requestResult.getGrantedList());
            } else {
                OmniSDKError error = result.error();
                Log.e(tag, "onLocationImpl error:" + error);
            }
        });
    }

    @Override
    public void onApkInstallImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.REQUEST_INSTALL_PACKAGES\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请在系统设置界面，开启-允许来自此来源的应用，打开安装APK权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"},\"specialDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要安装APk权限\\n\\n请在系统设置界面，开启-允许来自此来源的应用，打开安装APK权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";

        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.REQUEST_INSTALL_PACKAGES);

        OmniSDKPermissionRationaleOptions applyDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要安装APk权限\n\n请允许权限申请")
                .ok("允许")
                .cancel("取消")
                .build();

        OmniSDKPermissionRationaleOptions guideDialog = OmniSDKPermissionRationaleOptions
                .builder()
                .title("温馨提示")
                .rationale("(游戏)需要安装APk权限\n\n请在系统设置界面，开启-允许来自此来源的应用，打开安装APK权限")
                .ok("去开启")
                .cancel("取消")
                .build();

        OmniSDKRequestPermissionsOptions opts = OmniSDKRequestPermissionsOptions
                .builder()
                .showApplyDialog(true)
                .permissionsList(permissions)
                .applyDialog(applyDialog)
                .guideToSettingDialog(guideDialog)
                .timeLimit(48)
                .build();
        OmniSDKv3.getInstance().requestPermissions(appActivity, opts, result -> {
            if (result.isSuccess()) {
                OmniSDKRequestPermissionsResult requestResult = result.get();
                DemoLogger.i(tag, "onApkInstallImpl: " + result.get());
                callback.onResult(requestResult.getAllGranted(),requestResult.getDeniedList(),requestResult.getGrantedList());
            } else {
                OmniSDKError error = result.error();
                Log.e(tag, "onApkInstallImpl error:" + error);
            }
        });

    }

    @Override
    public void onBluetoothImpl() {
        String jsonString = "{\"showDialog\":true,\"timeLimit\":48,\"permissions\":[{\"group\":[\"android.permission.BLUETOOTH_CONNECT\"],\"applyDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要蓝牙权限\\n\\n请允许权限申请\",\"ok\":\"允许\",\"cancel\":\"取消\"},\"guideDialog\":{\"title\":\"温馨提示\",\"rationale\":\"(游戏)需要蓝牙权限\\n\\n请在系统设置界面，开启蓝牙权限\",\"ok\":\"去开启\",\"cancel\":\"取消\"}}]}";

        if (Build.VERSION.SDK_INT >= 31) {
            List<String> permissions = new ArrayList<>();
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT);

            // 大于必须申请，否则在Android 12 以上 蓝牙API 使用时会崩溃。
            OmniSDKPermissionRationaleOptions applyDialog = OmniSDKPermissionRationaleOptions
                    .builder()
                    .title("温馨提示")
                    .rationale("(游戏)需要蓝牙权限\n\n请允许权限申请")
                    .ok("允许")
                    .cancel("取消")
                    .build();

            OmniSDKPermissionRationaleOptions guideDialog = OmniSDKPermissionRationaleOptions
                    .builder()
                    .title("温馨提示")
                    .rationale("(游戏)需要蓝牙权限\\n\\n请在系统设置界面，开启蓝牙权限")
                    .ok("去开启")
                    .cancel("取消")
                    .build();

            OmniSDKRequestPermissionsOptions opts = OmniSDKRequestPermissionsOptions
                    .builder()
                    .showApplyDialog(true)
                    .permissionsList(permissions)
                    .applyDialog(applyDialog)
                    .guideToSettingDialog(guideDialog)
                    .timeLimit(48)
                    .build();
            OmniSDKv3.getInstance().requestPermissions(appActivity, opts, result -> {
                if (result.isSuccess()) {
                    OmniSDKRequestPermissionsResult requestResult = result.get();
                    DemoLogger.i(tag, "onBluetoothImpl: " + result.get());
                    callback.onResult(requestResult.getAllGranted(),requestResult.getDeniedList(),requestResult.getGrantedList());
                } else {
                    OmniSDKError error = result.error();
                    Log.e(tag, "onBluetoothImpl error:" + error);
                }
            });
        } else {
            // 低于不需要申请
            OmniSDKGetPermissionOptions bluetoothOpts = OmniSDKGetPermissionOptions
                    .builder()
                    .permission(Manifest.permission.BLUETOOTH)
                    .build();
            int bluetooth = OmniSDKv3.getInstance().getPermissionGrantedStatus(appActivity, bluetoothOpts);


            OmniSDKGetPermissionOptions bluetoothAdminOpts = OmniSDKGetPermissionOptions
                    .builder()
                    .permission(Manifest.permission.BLUETOOTH_ADMIN)
                    .build();

            int bluetoothAdmin = OmniSDKv3.getInstance().getPermissionGrantedStatus(appActivity, bluetoothAdminOpts);
            callback.showMessageDialog(
                    "SDK_INT<31, bluetooth=" + bluetooth + ", bluetoothAdmin=" + bluetoothAdmin + "\n\n值为0时代表权限被授予。",
                    "申请结果"
            );
        }
    }
}
