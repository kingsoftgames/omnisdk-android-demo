package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherCallback;

import kotlin.Pair;

/**
 * Description: Omni SDK其他功能API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 17:06
 */
@Keep
public class OtherApi implements IOtherApi {

    /* ***************************** SDK 其他功能API接口示例如下 ********************************** */

    /**
     * SDK进行APP评分接口
     */
    public void showScoreDialog() {
        OmniSDK.getInstance().showScoreDialog(demoActivity, new ResponseCallback() {
            @Override
            public void onSuccess() {
                Log.i(tag, "评分成功");
                callback.onSucceeded("评分成功");
            }

            @Override
            public void onFailure(Pair<Integer, String> responseCode) {
                Log.e(tag, "评分失败: " + responseCode.toString());
                callback.onFailed(responseCode);
            }
        });
    }

    /**
     * webview js 测试
     */
    private void webJsTest() {
        String url = "file:///android_asset/webviewJS.html";
        OmniSDK.getInstance().openBrowserActivity(demoActivity, url, "");
    }

    /* Sunit茄子渠道独有功能方法接口反射调用 */

    /**
     * 茄子渠道激励视频场景入口上报（此方法调用可能会比较频繁,请耐心接入)
     *
     * @param jsonStr json格式的字符串
     *                格式如下{"scene":"home","subPortals":["portal1","portal2"]}
     *                其中scene 表示上报场景：进入场景（打开弹窗或打开各个游戏场景）或重新回到该场景（锁屏、home键、跳转其他场景后返回到原来场景）
     *                或者理解为包含激励视频入口按钮的场景或弹窗,得到焦点
     */
    public void invokeShowRewardedBadgeViewMethod(String jsonStr) {
        // 调用SDK功能方法反射接口
        OmniSDK.getInstance().invokeMethod("showRewardedBadgeView", jsonStr);

        callback.onSucceeded("反射调用showRewardedBadgeView完成");
    }

    /**
     * 茄子渠道自定义事件上报
     *
     * @param jsonStr json格式的字符串
     *                格式如下{"eventId":"home","params":{"key1":"value1","key2":"value2"}}
     *                其中eventId 表示上报事件ID：添加的事件名称，需要通知SHAREit运营）
     *                parmas:上报事件参数 （参数的key名称必须要小写）
     */
    public void invokeOnSunitEventMethod(Context context, String jsonStr) {
        // 调用SDK功能方法反射接口
        OmniSDK.getInstance().invokeMethod("onSunitEvent", context, jsonStr);

        callback.onSucceeded("反射调用onSunitEvent完成");
    }

    /* end */

    /* Transsion传音渠道独有功能方法接口反射调用 */

    /**
     * 展示悬浮窗广告
     *
     * @param activity 当前展示界面 activity
     */
    public void invokeShowFloatAdMethod(Activity activity) {
        // 调用SDK功能方法反射接口
        OmniSDK.getInstance().invokeMethod("showFloatAd", activity);

        callback.onSucceeded("反射调用showFloatAd完成");
    }

    /**
     * 关闭悬浮窗广告
     *
     * @param activity 当前展示界面 activity
     */
    public void invokeCloseFloatAdMethod(Activity activity) {
        // 调用SDK功能方法反射接口
        OmniSDK.getInstance().invokeMethod("closeFloatAd", activity);

        callback.onSucceeded("反射调用closeFloatAd完成");
    }

    /* end */

    /* ****************************************************************************************** */
    private final String tag = "SDK: " + this.getClass().getName();
    private final Activity demoActivity;
    private final IOtherCallback callback;

    public OtherApi(Activity activity, IOtherCallback otherCallback) {
        this.demoActivity = activity;
        this.callback = otherCallback;
    }

    @Override
    public void webJsImpl() {
        webJsTest();
    }
}
