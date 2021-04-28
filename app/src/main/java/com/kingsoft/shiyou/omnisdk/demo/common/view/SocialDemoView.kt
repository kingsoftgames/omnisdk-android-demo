package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.kingsoft.shiyou.omnisdk.api.entity.ShareImageType
import com.kingsoft.shiyou.omnisdk.api.entity.ShareType
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.ISocialApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.ISocialCallback
import java.util.*

/**
 * Description: 社交功能相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/11 11:20
 *
 */
class SocialDemoView : DemoView, ISocialCallback {

    private lateinit var socialApi: ISocialApi

    private var shareType: ShareType = ShareType.LINK
    private var shareImageType: ShareImageType = ShareImageType.NO

    private val sharePlatformEt: EditText by EditTextDelegate()
    private val shareTitleEt: EditText by EditTextDelegate()
    private val shareDescriptionEt: EditText by EditTextDelegate()
    private val shareContentLinkUrlEt: EditText by EditTextDelegate()

    private val shareImageSourceUrlEt: EditText by EditTextDelegate()

    private val invitePlatformEt: EditText by EditTextDelegate()
    private val inviteDataEt: EditText by EditTextDelegate()

    private val getSocialInfoPlatformEt: EditText by EditTextDelegate()
    private val getFriendSocialInfoPlatformEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private lateinit var imageTypeContainer: LinearLayout

    override fun initView() {

        // 获取社交功能API接口实例
        socialApi = ApiManager.instance.getSocialApi(this)

        imageTypeContainer = findViewById(R.id.social_demo_view_share_image_type_container)
        initShareTypes()
        sharePlatformEt
        shareTitleEt
        shareDescriptionEt
        shareContentLinkUrlEt
        shareImageSourceUrlEt
        R.id.social_demo_view_share_btn.addClickListener { share() }
        invitePlatformEt
        inviteDataEt
        R.id.social_demo_view_invite_btn.addClickListener { invite() }
        getSocialInfoPlatformEt
        R.id.social_demo_view_get_info_btn.addClickListener { getSocialInfo() }
        getFriendSocialInfoPlatformEt
        R.id.social_demo_view_get_friend_info_btn.addClickListener { getFriendInfo() }
    }

    private fun initShareTypes() {
        val shareTypeSpinner = findViewById<Spinner>(R.id.social_demo_view_share_type_spinner)
        val shareTypes = ArrayList<String>()
        ShareType.values().forEach {
            shareTypes.add(it.name)
        }
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, shareTypes)
        shareTypeSpinner.adapter = adapter
        shareTypeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                (shareTypeSpinner.selectedItem as? String)?.let {
                    shareType = ShareType.valueOf(it)
                    Log.e("SDK", "shareType = $shareType")
                    if (shareType == ShareType.IMAGE) {
                        showImageType()
                    } else {
                        imageTypeContainer.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun showImageType() {
        imageTypeContainer.visibility = View.VISIBLE
        val imageTypeSpinner = findViewById<Spinner>(R.id.social_demo_view_share_image_type_spinner)
        val imageTypes = ArrayList<String>()
        ShareImageType.values().forEach {
            imageTypes.add(it.name)
        }
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, imageTypes)
        imageTypeSpinner.adapter = adapter
        imageTypeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                (imageTypeSpinner.selectedItem as? String)?.let {
                    shareImageType = ShareImageType.valueOf(it)
                    Log.i("SDK", "shareImageType = $shareImageType")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun share() {
        // 1. 创建分享参数

        // 分享类型，目前支持LINK, GROUP_LINK, IMAGE
        val type = shareType
        // 分享标题文本
        val title = shareTitleEt.content()
        // 分享具体描述文本
        val description = shareDescriptionEt.content()
        // 分享内容URL链接地址,可选
        val link = shareContentLinkUrlEt.content()
        // 在分享图片的时候需要指定该值，目前支持类型为NETWORK（网络图片),LOCAL(本地图片uri),SCREENSHOT(截屏)
        val imageType = shareImageType
        // 在分享图片的时候需要指定该值, 给出分享图片本地Uri或网络图片Uri地址
        val imageUri = shareImageSourceUrlEt.content()

        // 2. 指定分享的社交平台，比如facebook,twitter
        val platform = sharePlatformEt.content()

        if (platform.isBlank()) {
            appView.showToastMessage("请指定分享社交平台")
            return
        }

        socialApi.shareImpl(platform, type, title, description, link, imageType, imageUri)
    }

    private fun invite() {
        // 指定社交平台，比如facebook,twitter
        val platform = invitePlatformEt.content()
        if (platform.isBlank()) {
            appView.showToastMessage("请指定邀请社交平台")
            return
        }
        /*
         * 邀请额外JSON数据,比如:
         * {
         *   "message":"invite-message",
         *   "channelName":"facebook",
         *   "appLinkURL":"appLinkURL",
         *   "previewImageURL":"previewImageURL",
         *   "extJsonParams":"extJsonParams"
         * }
         * 若无特殊说明,只需指定message即可
         * */
        val json = inviteDataEt.content()
        socialApi.inviteImpl(platform, json)
    }

    private fun getSocialInfo() {

        // 指定社交平台，比如facebook,twitter
        val platform = getSocialInfoPlatformEt.content()

        if (platform.isBlank()) {
            appView.showToastMessage("请指定社交平台")
            return
        }
        socialApi.getSocialInfoImpl(platform)
    }

    private fun getFriendInfo() {

        // 指定社交平台，比如facebook,twitter
        val platform = getFriendSocialInfoPlatformEt.content()

        if (platform.isBlank()) {
            appView.showToastMessage("请指定社交平台")
            return
        }

        socialApi.getFriendInfoImpl(platform)
    }

    override fun onSucceeded(resultJson: String) {
        appView.showToastMessage("成功: $resultJson")
    }

    override fun onFailed(responseCode: Pair<Int, String>) {
        appView.showToastMessage("失败: $responseCode")
    }

    override fun onCancelled() {
        appView.showToastMessage("取消")
    }

}