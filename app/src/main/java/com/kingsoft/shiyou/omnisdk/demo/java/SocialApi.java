package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Keep;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback;
import com.kingsoft.shiyou.omnisdk.api.callback.ResultCallback;
import com.kingsoft.shiyou.omnisdk.api.entity.ShareImageType;
import com.kingsoft.shiyou.omnisdk.api.entity.ShareParam;
import com.kingsoft.shiyou.omnisdk.api.entity.ShareType;
import com.kingsoft.shiyou.omnisdk.api.entity.SocialInfo;
import com.kingsoft.shiyou.omnisdk.api.utils.OmniUtils;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.ISocialApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.ISocialCallback;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

/**
 * Description: OMNI SDK社交功能相关API接口示例Demo
 *
 * @author: LuXing created on 2021/3/23 15:01
 */
@Keep
public class SocialApi implements ISocialApi {

    /* ***************************** SDK 社交功能相关API接口示例 ********************************** */

    private final String tag = "SDK: " + this.getClass().getName();
    private final Activity demoActivity;
    private final ISocialCallback callback;

    public SocialApi(Activity activity, ISocialCallback socialCallback) {
        this.demoActivity = activity;
        this.callback = socialCallback;
    }

    /**
     * 社交分享接口示例
     *
     * @param platform    SDK支持的社交平台(如 "facebook")或指定特定的包名
     * @param type        分享类型
     * @param title       分享标题文本
     * @param description 分享具体描述文本
     * @param link        分享内容URL链接地址
     * @param imageType   分享的图片类型
     * @param imageUri    分享图片链接：本地Uri或网络图片
     * @see ShareImageType
     */
    public void share(
            String platform,
            ShareType type,
            String title,
            String description,
            String link,
            ShareImageType imageType,
            String imageUri) {

        // 创建分享数据实体
        ShareParam shareParam = new ShareParam(type, title, description, link, imageType, imageUri);

        // 调用SDK社交分享接口进行分享
        OmniSDK.getInstance().share(demoActivity, platform, shareParam, new ResponseCallback() {
            @Override
            public void onSuccess() {
                // 分享成功，CP方按自身需求执行之后业务比如给与奖励等等
                Log.i(tag, "share successfully");
                callback.onSucceeded("success");
            }

            @Override
            public void onFailure(Pair<Integer, String> responseCode) {
                // 分享失败
                callback.onFailed(responseCode);
            }
        });

    }

    /* ****************************************************************************************** */

    /**
     * 社交邀请接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     * @param json     ```json
     *                 {"message":"invite-message"}
     *                 ```
     */
    public void invite(String platform, String json) {

        // 调用SDK社交邀请接口
        OmniSDK.getInstance().invite(demoActivity, platform, json, new ResultCallback() {
            @Override
            public void onSuccess(String resultJson) {
                // 邀请成功，CP方按自身需求执行之后业务比如给与奖励等等
                callback.onSucceeded(resultJson);
            }

            @Override
            public void onFailure(Pair<Integer, String> responseCode) {
                // 邀请失败
                callback.onFailed(responseCode);
            }
        });
    }

    /**
     * 获取社交平台自身账号信息接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     */
    public void getSocialInfo(String platform) {

        // 调用获取社交平台自身账号信息接口
        OmniSDK.getInstance().getSocialInfo(demoActivity, platform, new ResultCallback() {
            @Override
            public void onSuccess(String resultJson) {
                try {
                    // 解析玩家社交账号信息数据
                    SocialInfo socialAccountInfo = OmniUtils.fromJson(resultJson, SocialInfo.class);

                    Log.i(tag, socialAccountInfo.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                callback.onSucceeded(resultJson);
            }

            @Override
            public void onFailure(Pair<Integer, String> responseCode) {
                // 获取失败
                callback.onFailed(responseCode);
            }
        });
    }

    /**
     * 获取社交平台好友账号信息接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     */
    public void getFriendInfo(String platform) {

        // 调用获取社交平台好友账号信息接口
        OmniSDK.getInstance().getFriendInfo(demoActivity, platform, new ResultCallback() {
            @Override
            public void onSuccess(String resultJson) {
                // 解析玩家游戏好友的社交账号信息数据
                List<SocialInfo> friendsInList = OmniUtils.<SocialInfo>fromJson(resultJson);
                for (SocialInfo socialAccountInfo : friendsInList) {
                    Log.i(tag, socialAccountInfo.toString());
                }
                callback.onSucceeded(resultJson);
            }

            @Override
            public void onFailure(Pair<Integer, String> responseCode) {
                // 获取失败
                callback.onFailed(responseCode);
            }
        });
    }

    @Override
    public void shareImpl(String platform, ShareType type, String title, String description, String link, ShareImageType imageType, String imageUri) {
        share(platform, type, title, description, link, imageType, imageUri);
    }

    @Override
    public void inviteImpl(String platform, String json) {
        invite(platform, json);
    }

    @Override
    public void getSocialInfoImpl(String platform) {
        getSocialInfo(platform);
    }

    @Override
    public void getFriendInfoImpl(String platform) {
        getFriendInfo(platform);
    }

    /**
     * 第三方平台用户账号信息
     */
    public static class SocialAccountInfo {
        public String id = "";       // 第三方平台账号标示ID
        public String nickName = ""; // 第三方平台账号昵称
        public String gender = "";   // 男 `male` 女 `female`
        public String imageUrl = ""; // 用户头像Url地址
        public int width = 0;        // 头像宽度像素
        public int height = 0;       // 头像高度像素

        @Override
        public String toString() {
            return "SocialAccountInfo{" +
                    "id='" + id + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", gender='" + gender + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }

    }
}
