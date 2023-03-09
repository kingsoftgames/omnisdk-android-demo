package com.kingsoft.shiyou.omnisdk.demo.java;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.InitNotifier;
import com.kingsoft.shiyou.omnisdk.api.callback.PermissionCallback;
import com.kingsoft.shiyou.omnisdk.api.ui.OmniSdkActivity;
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoDialogUtil;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;
import com.kingsoft.shiyou.omnisdk.demo.common.view.AppView;

import java.util.List;

/**
 * 继承OmniSDK提供的Activity，注意顺序
 *
 * @author OmniSDK
 */
public class DemoIfExtendsOmniSdkActivity extends OmniSdkActivity {

    private final String TAG = "DemoAppActivity# ";
    private AppView appView;

    /**
     * 注意顺序
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApiManager.getInstance().initialize(ApiManager.Language.JAVA);
        appView = new AppView();
        appView.attachToActivity(this);

        // 注意顺序
        OmniSDK.getInstance().setInitNotifier(new InitNotifier() {
            @Override
            public void onSuccess() {
                DemoLogger.i(TAG, "Initialization Done Successfully");
                appView.setInitializedDone(true);
            }

            @Override
            public void onFailure(@NonNull String message) {
                DemoLogger.e(TAG, "Initialization Failed, error : " + message);
                appView.setInitializedDone(false);
            }
        });
        // 注意顺序，这里 super 会自动 OmniSDK.getInstance().onCreate
        super.onCreate(savedInstanceState);


        DemoLogger.i(TAG, "onCreate(...) called");

//        注意：如果项目使用自己的隐私协议，则需要在用户同意隐私协议后调用 onCreate
//         示例
//         1. registerInitNotifier 注意顺序不变
//         2. 同意项目的隐私协议后进行初始化
//         if (isAgreementProjectPrivacy) {
//             OmniSDK.getInstance().onCreate(this, savedInstanceState);
//         }

        // CP自己的代码
    }


    /**
     * CP对接方必须在游戏Activity中重载onRequestPermissionsResult方法并在其中调用如下SDK API接口
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        OmniSDK.getInstance().registerRequestPermissions(requestCode, permissions, grantResults, new PermissionCallback() {

            @Override
            public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
                runOnUiThread(() -> DemoDialogUtil.INSTANCE.showDialogWithActivity(DemoIfExtendsOmniSdkActivity.this, "拒绝权限组", perms.toString()));

            }

            @Override
            public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
                runOnUiThread(() -> DemoDialogUtil.INSTANCE.showDialogWithActivity(DemoIfExtendsOmniSdkActivity.this, "同意权限组", perms.toString()));
            }
        });

        // CP自己的代码
    }
}
