package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.ExitCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IExitApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IExitCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;

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
        OmniSDK.getInstance().onExit(appActivity, new ExitCallback() {
            @Override
            public void onExit(boolean showExitUi) {
                if (showExitUi) {
                    // 游戏方弹出自身退出UI界面，按照自身需求处理之后的业务逻辑
                    DemoLogger.i(tag, "launch exit ui");
                    callback.showExitUi();
                } else {
                    // 游戏方不弹出自身退出UI界面，立即执行退出游戏业务逻辑处理，比如杀掉游戏应用进程等等
                    try {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }, gameCustom);
    }

    /* ****************************************************************************************** */

    @Override
    public void onExitImpl(boolean hasGameCustomExitDialog) {
        DemoLogger.i(tag, "onExitImpl: gameCustom = " + hasGameCustomExitDialog);
        mGameCustom = hasGameCustomExitDialog;
        onExit();
    }
}
