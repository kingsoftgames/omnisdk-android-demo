package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.EmulatorCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.ResultCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherCallback;
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import kotlin.Pair;
import kotlin.text.Charsets;

/**
 * Description: Omni SDK其他功能API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 17:06
 */

@Keep
public class OtherApi implements IOtherApi {

    /* ***************************** SDK 其他功能API接口示例如下 ********************************** */

    private final String tag = "SDK: " + this.getClass().getName();
    private final Activity demoActivity;

    private final IOtherCallback callback;

    public OtherApi(Activity activity, IOtherCallback otherCallback) {
        this.demoActivity = activity;
        this.callback = otherCallback;
    }

    /**
     * 一些常用接口
     */
    @Override
    public void doActionImpl() {

        String omniSdkVersion = OmniSDK.getInstance().getSDKVersion();
        String omniSdkAppId = OmniSDK.getInstance().getAppId();
        String channelId = OmniSDK.getInstance().getChannelId();
        String channelName = OmniSDK.getInstance().getChannelName();
        String channelCpsName = OmniSDK.getInstance().getCpsName(demoActivity);

        callback.onSucceeded("omniSdkVersion: " + omniSdkVersion + "\n"
                + "omniSdkAppId: " + omniSdkAppId + "\n"
                + "channelId: " + channelId + "\n"
                + "channelName: " + channelName + "\n"
                + "channelCpsName: " + channelCpsName + "\n"
        );

    }

    @Override
    public void getOAIdImpl() {
        // 内部，暂时不对外提供
        callback.onSucceeded("oaid");
    }

    /**
     * 检查是否为模拟器
     */
    @Override
    public void checkEmulatorImpl() {
        // （可选）设备检查点信息监听注册
        boolean isEmulator = OmniSDK.getInstance().checkIsEmulator(demoActivity, new EmulatorCallback() {
            @Override
            public void onEmulator(@Nullable String emulatorInfo) {
                // 设备检查点信息
                DemoLogger.e(tag, "设备检查点信息: " + emulatorInfo);
            }
        }, 3);

        callback.onSucceeded("是否为模拟器: " + isEmulator);
    }

    /**
     * 通过 Custom Tabs 机制 打开网页(默认)
     *
     * @param url    启动网页URL地址
     * @param params 参数数据(Json字符串格式)
     */
    @Override
    public void openBrowserActivityImpl(@NotNull String url, @NotNull String params) {
        // 方式一：直接传 JSON
         /*
          params格式举例如下:
            {
            "web_url": "https://weibo.com/",
            "show_share": true,
            "share_param": {
              "platform": "facebook,system",
              "share_type": 0,
              "image_type": 0,
              "image_uri": 0,
              "title": "OmniShare",
              "content": "content",
              "url": "https://developers.facebook.com/"
            },
            "game_headers": {
              "h5": true,
              "appid": 198707,
              "platform": "android",
              "heg": 175.55
            },
            "custom_tabs_config": {
              "show_title": true,
              "close_icon": "icon drawable_id",
              "toolbar_color": "#AARRGGBB 或 #RRGGBB"
              }
            }

         */

        DemoLogger.e(tag, "params: " + params);
        // 方式一：直接传 JSON
        OmniSDK.getInstance().openBrowserActivity(demoActivity, url, "");
    }

    @Override
    public void openBrowserActivityWithLocalImpl() {
        // "file:///android_asset/webviewJS.html"
        Uri uri = createUri(demoActivity, "webviewJS.html");
        String url = uri.toString();
        DemoLogger.e(tag, "url: " + url);
        OmniSDK.getInstance().openBrowserActivity(demoActivity, url, "");
    }

    /**
     * CustomTabs 无法直接访问 Assets，需要将其copy到 getExternalCacheDir 目录下后通过 FileProvider 访问
     */
    private Uri createUri(Context context, String htmlFileName) {
        try {
            //Create an empty html file in external cache directory
            File redirect = new File(context.getExternalCacheDir(), htmlFileName);

            //Open and read local html file from asset folder
            AssetManager manager = context.getAssets();
            InputStream is = manager.open(htmlFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String templateString = new String(buffer, Charsets.UTF_8);

            //Write the content of file in redirect.html
            FileOutputStream fileOutputStream = new FileOutputStream(redirect);
            fileOutputStream.write(templateString.getBytes(Charsets.UTF_8));

            //Get the uri of redirect.html using content provider
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".omnisdk.FILE_PROVIDER", redirect);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 渠道应用商店评分引导弹窗（部分渠道有）
     */
    @Override
    public void showScoreDialogImpl() {
        // 判断渠道是否支持渠道商店的游戏应用评分
        if (OmniSDK.getInstance().isMethodSupported("openScoreDialog")) {
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
        } else {
            callback.onSucceeded("当前渠道没有评分引导弹窗");
        }
    }

    /**
     * 打开系统权限设置界面
     */
    @Override
    public void openAppPermissionSettingsImpl() {
        OmniSDK.getInstance().openApplicationDetailsSettings(demoActivity);
    }


    @Override
    public void invokeMethodImpl() {
        // Please replace v3.
    }

    /**
     * 茄子渠道激励视频场景入口上报（此方法调用可能会比较频繁,请耐心接入)
     *
     * @param jsonStr json格式的字符串
     *                格式如下{"scene":"home","subPortals":["portal1","portal2"]}
     *                其中scene 表示上报场景：进入场景（打开弹窗或打开各个游戏场景）或重新回到该场景（锁屏、home键、跳转其他场景后返回到原来场景）
     *                或者理解为包含激励视频入口按钮的场景或弹窗,得到焦点
     */
    @Override
    public void invokeChannelSunitShowRewardedBadgeViewMethod(@NonNull String jsonStr) {
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
    public void invokeChannelSunitEventMethod(@NotNull String jsonStr) {
        // 调用SDK功能方法反射接口
        OmniSDK.getInstance().invokeMethod("onSunitEvent", demoActivity, jsonStr);

        callback.onSucceeded("反射调用onSunitEvent完成");
    }

    @Override
    public void invokeQuerySkuDetailsList(@NonNull List<String> skusList, int skuType) {
        Log.e(tag, "getChannelName: " + OmniSDK.getInstance().getChannelName());
        if (OmniSDK.getInstance().isMethodSupported("querySkuDetailsList")) {
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
        } else {
            callback.onSucceeded("当前渠道没有查询商品本地信息化功能");
        }
    }
}
