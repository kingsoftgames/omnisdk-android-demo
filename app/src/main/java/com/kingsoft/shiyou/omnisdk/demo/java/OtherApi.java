package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.ResultCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.Pair;

/**
 * Description: Omni SDK其他功能API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 17:06
 */

@Keep
public class OtherApi implements IOtherApi {

    /* ***************************** SDK 其他功能API接口示例如下 ********************************** */

    /* ****************************************************************************************** */
    private final String tag = "SDK: " + this.getClass().getName();
    private final Activity demoActivity;

    /* Sunit茄子渠道独有功能方法接口反射调用 */
    private final IOtherCallback callback;

    public OtherApi(Activity activity, IOtherCallback otherCallback) {
        this.demoActivity = activity;
        this.callback = otherCallback;
    }

    /* end */

    /* Transsion传音渠道独有功能方法接口反射调用 */

    /**
     * 打开浏览器或者内置Webview
     *
     * @param url    启动网页URL地址
     * @param params 参数数据(Json字符串格式)
     */
    public void openBrowserActivity(String url, String params) {
        /*
          params格式举例如下:

          {
            "default_browser": false,
            "show_refresh": true,
            "show_share": true,
            "show_close": true,
            "show_prev": true,
            "show_next": true,
            "layout": 0,
            "orientation": 0,
            "share_param": {
                             "platform": "facebook,system",
                             "share_type": 0,
                             "title": "OmniShare",
                             "url": "https://developers.facebook.com/"
            },
            "game_headers": {
                              "h5": true,
                              "appid": 198707,
                              "platform": "android",
                              "heg": 175.55
             },
             "title": "Omni Webview",
             "game_ext": "roleId=123456789"
            }

         */
        OmniSDK.getInstance().openBrowserActivity(demoActivity, url, params);
    }

    /**
     * SDK进行APP评分接口
     */
    @Override
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

    /* end */

    /**
     * 茄子渠道激励视频场景入口上报（此方法调用可能会比较频繁,请耐心接入)
     *
     * @param jsonStr json格式的字符串
     *                格式如下{"scene":"home","subPortals":["portal1","portal2"]}
     *                其中scene 表示上报场景：进入场景（打开弹窗或打开各个游戏场景）或重新回到该场景（锁屏、home键、跳转其他场景后返回到原来场景）
     *                或者理解为包含激励视频入口按钮的场景或弹窗,得到焦点
     */
    @Override
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
    @Override
    public void invokeOnSunitEventMethod(Context context, String jsonStr) {
        // 调用SDK功能方法反射接口
        OmniSDK.getInstance().invokeMethod("onSunitEvent", context, jsonStr);

        callback.onSucceeded("反射调用onSunitEvent完成");
    }

    /**
     * 展示悬浮窗广告
     *
     * @param activity 当前展示界面 activity
     */
    @Override
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
    @Override
    public void invokeCloseFloatAdMethod(Activity activity) {
        // 调用SDK功能方法反射接口
        OmniSDK.getInstance().invokeMethod("closeFloatAd", activity);

        callback.onSucceeded("反射调用closeFloatAd完成");
    }

    @Override
    public void invokeQuerySkuDetailsList(@NotNull List<String> skusList, int skuType) {
        OmniSDK.getInstance().invokeMethod("querySkuDetailsList", skusList, skuType, new ResultCallback() {
            @Override
            public void onSuccess(@NotNull String resultJson) {
                callback.onSucceeded(resultJson);
            }

            @Override
            public void onFailure(@NotNull Pair<Integer, String> responseCode) {
                callback.onFailed(responseCode);
            }
        });
    }

    @Override
    public void openBrowserActivityImpl(@NotNull String url, @NotNull String params) {
        openBrowserActivity(url, params);
    }
}
