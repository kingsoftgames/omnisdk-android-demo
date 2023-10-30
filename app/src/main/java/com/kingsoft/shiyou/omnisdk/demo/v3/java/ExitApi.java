package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKQuitOptions;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKQuitResult;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IExitApi;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IExitCallback;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger;

/**
 * Description: OmniSDK应用退出API接口代码示例Demo
 *
 * @author: LuXing created on 2022/1/21 15:33
 */
@Keep
public class ExitApi implements IExitApi {

    private final String tag = "ExitApi# ";

    private final Activity appActivity;
    private final IExitCallback callback;

    private boolean mGameCustom = false;

    public ExitApi(Activity activity, IExitCallback exitCallback) {
        this.appActivity = activity;
        this.callback = exitCallback;
    }

    /* ******************************** OmniSDK应用退出接口示例如下 ******************************** */

    private void onExit() {
        // 该字段由游戏对接方传入，标示是否在退出应用的时候有自身的退出UI界面可进行显示；
        // 务必注意的是最终是否需要显示需根据退出接口回调来决定。
        boolean gameCustom = this.mGameCustom;

        // 调用OmniSDK的应用退出接口，游戏对接方在游戏用户触发`应用退出`功能的时候必须先调用该接口，
        // 然后按照该接口的回调和自身需求做后续业务逻辑处理。

        OmniSDKQuitOptions opts = OmniSDKQuitOptions
                .builder()
                .isCustomExitUi(gameCustom)
                .build();

        OmniSDKv3.getInstance().quit(appActivity, opts, result -> {
            if (result.isSuccess()) {
                OmniSDKQuitResult omniSDKQuitResult = result.get();
                Log.i(tag, "quit successfully " + omniSDKQuitResult);
                if (omniSDKQuitResult.isCustomExitUi()){
                    callback.showExitUi();
                }else {
                    killProcess();
                }
            } else {
                OmniSDKError error = result.error();
                Log.e(tag, "quit error:"+ error);
                // 游戏方不弹出自身退出UI界面，立即执行退出游戏业务逻辑处理，比如杀掉游戏应用进程等等
                killProcess();
            }
        });
    }

    private void killProcess(){
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /* ****************************************************************************************** */

    @Override
    public void onExitImpl(boolean hasGameCustomExitDialog) {
        DemoLogger.i(tag, "onExitImpl: gameCustom = " + hasGameCustomExitDialog);
        mGameCustom = hasGameCustomExitDialog;
        onExit();
    }
}
