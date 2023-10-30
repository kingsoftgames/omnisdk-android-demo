package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.entity.ShareImageType;
import com.kingsoft.shiyou.omnisdk.api.entity.ShareType;
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKSocialSharePlatform;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKSocialShareOptions;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ISocialApi;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ISocialCallback;

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

    public OmniSDKSocialSharePlatform toSocialSharePlatform(String platform) {
        switch (platform) {
            case "system":
                return OmniSDKSocialSharePlatform.SYSTEM;
            case "facebook":
                return OmniSDKSocialSharePlatform.FACEBOOK;
            case "line":
                return OmniSDKSocialSharePlatform.LINE;
        }
        return OmniSDKSocialSharePlatform.SYSTEM;
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
            @NonNull Activity activity,
            String platform,
            ShareType type,
            String title,
            String description,
            String link,
            ShareImageType imageType,
            String imageUri) {

        OmniSDKSocialShareOptions opts = OmniSDKSocialShareOptions.builder()
                .platform(toSocialSharePlatform(platform))
                .title(title)
                .description(description)
                .linkUrl(link)
                .imageUrl(imageUri)
                .build();

        OmniSDKv3.getInstance().socialShare(activity, opts, result -> {
            if (result.isSuccess()) {
                Log.i(tag, "share successfully");
                callback.onSucceeded("success");
            } else {
                OmniSDKError error = result.error();
                Log.e(tag, error.toString());
                // 分享失败
                callback.onFailed(error);
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

        // 调用SDK社交邀请接口 todo V3 暂时不实现
//        OmniSDK.getInstance().invite(demoActivity, platform, json, new ResultCallback() {
//            @Override
//            public void onSuccess(String resultJson) {
//                // 邀请成功，CP方按自身需求执行之后业务比如给与奖励等等
//                callback.onSucceeded(resultJson);
//            }
//
//            @Override
//            public void onFailure(Pair<Integer, String> responseCode) {
//                // TODO: V3 邀请失败
//                // callback.onFailed(responseCode);
//            }
//        });
    }

    /**
     * 获取社交平台自身账号信息接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     */
    public void getSocialInfo(String platform) {

        // 调用获取社交平台自身账号信息接口  todo V3 暂时不实现
//        OmniSDK.getInstance().getSocialInfo(demoActivity, platform, new ResultCallback() {
//            @Override
//            public void onSuccess(String resultJson) {
//                try {
//                    // 解析玩家社交账号信息数据
//                    SocialAccountInfo socialAccountInfo = new Gson().fromJson(resultJson, SocialAccountInfo.class);
//                    String id = socialAccountInfo.id;             // 第三方平台账号标示ID
//                    String nickName = socialAccountInfo.nickName; // 第三方平台账号昵称
//                    String gender = socialAccountInfo.gender;     // 男 `male` 女 `female`
//                    String imageUrl = socialAccountInfo.imageUrl; // 用户头像Url地址
//                    int width = socialAccountInfo.width;          // 头像宽度像素
//                    int height = socialAccountInfo.height;        // 头像高度像素
//
//                    Log.i(tag, socialAccountInfo.toString());
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                callback.onSucceeded(resultJson);
//            }
//
//            @Override
//            public void onFailure(Pair<Integer, String> responseCode) {
//                // TODO: V3 获取失败
//                // callback.onFailed(responseCode);
//            }
//        });
    }

    /**
     * 获取社交平台好友账号信息接口示例
     *
     * @param platform 哪个平台，如 "facebook"
     */
    public void getFriendInfo(String platform) {

        // 调用获取社交平台好友账号信息接口  todo V3 暂时不实现
//        OmniSDK.getInstance().getFriendInfo(demoActivity, platform, new ResultCallback() {
//            @Override
//            public void onSuccess(String resultJson) {
//                // 解析玩家游戏好友的社交账号信息数据
//                ArrayList<SocialAccountInfo> friendsInList = new Gson().fromJson(resultJson, new TypeToken<ArrayList<SocialAccountInfo>>() {
//                }.getType());
//                for (SocialAccountInfo socialAccountInfo : friendsInList) {
//                    String id = socialAccountInfo.id;             // 第三方平台账号标示ID
//                    String nickName = socialAccountInfo.nickName; // 第三方平台账号昵称
//                    String imageUrl = socialAccountInfo.imageUrl; // 用户头像Url地址
//                    int width = socialAccountInfo.width;          // 头像宽度像素
//                    int height = socialAccountInfo.height;        // 头像高度像素
//
//                    Log.i(tag, socialAccountInfo.toString());
//                }
//                callback.onSucceeded(resultJson);
//            }
//
//            @Override
//            public void onFailure(Pair<Integer, String> responseCode) {
//                // TODO: V3 获取失败
//                // callback.onFailed(responseCode);
//            }
//        });
    }

    @Override
    public void shareImpl(@NonNull Activity activity, String platform, ShareType type, String title, String description, String link, ShareImageType imageType, String imageUri) {
        share(activity, platform, type, title, description, link, imageType, imageUri);
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
