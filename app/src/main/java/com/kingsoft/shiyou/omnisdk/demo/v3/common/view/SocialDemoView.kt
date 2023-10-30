package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.kingsoft.shiyou.omnisdk.api.entity.ShareImageType
import com.kingsoft.shiyou.omnisdk.api.entity.ShareType
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ISocialApi
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ISocialCallback
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
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

    private lateinit var linkUrlContainer: LinearLayout

    override fun initView() {

        // 获取社交功能API接口实例
        socialApi = ApiManager.instance.getSocialApi(appActivity, this)

        imageTypeContainer = findViewById(R.id.v3_social_demo_view_share_image_type_container)
        linkUrlContainer = findViewById(R.id.v3_social_demo_view_share_link_url_container)
        initShareTypes()
        sharePlatformEt
        shareTitleEt
        shareDescriptionEt
        shareContentLinkUrlEt
        shareImageSourceUrlEt
        R.id.v3_social_demo_view_share_btn.addClickListener { share() }
        invitePlatformEt
        inviteDataEt
        R.id.v3_social_demo_view_invite_btn.addClickListener { invite() }
        getSocialInfoPlatformEt
        R.id.v3_social_demo_view_get_info_btn.addClickListener { getSocialInfo() }
        getFriendSocialInfoPlatformEt
        R.id.v3_social_demo_view_get_friend_info_btn.addClickListener { getFriendInfo() }
        // V3 invite  getInfo   getFriendInfo 暂时不实现 将其入口隐藏
        R.id.v3_social_demo_view_invite_layout.view<RelativeLayout>().show(false)
        R.id.v3_social_demo_view_get_social_info_layout.view<RelativeLayout>().show(false)
        R.id.v3_social_demo_view_get_friend_social_info_layout.view<RelativeLayout>().show(false)
    }

    private fun initShareTypes() {
        val shareTypeSpinner = findViewById<Spinner>(R.id.v3_social_demo_view_share_type_spinner)
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
                        linkUrlContainer.visibility = View.GONE
                    } else {
                        imageTypeContainer.visibility = View.GONE
                        linkUrlContainer.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun showImageType() {
        imageTypeContainer.visibility = View.VISIBLE
        val imageTypeSpinner = findViewById<Spinner>(R.id.v3_social_demo_view_share_image_type_spinner)
        val imageTypes = ArrayList<String>()
        ShareImageType.values().forEach {
            if (it != ShareImageType.NO) {
                imageTypes.add(it.name)
            }
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

                    if (shareImageType == ShareImageType.LOCAL) {
                        val testImageFilePath = generateTestImage(appView.appActivity)
                        shareImageSourceUrlEt.setText(testImageFilePath)
                    } else {
                        shareImageSourceUrlEt.setText(shareImageSourceUrlEt.content())
                    }
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
        val link = if (type == ShareType.LINK) shareContentLinkUrlEt.content() else ""
        // 在分享图片的时候需要指定该值，目前支持类型为NETWORK（网络图片),LOCAL(本地图片uri),SCREENSHOT(截屏)
        val imageType = shareImageType
        // 在分享图片的时候需要指定该值, 给出分享图片本地Uri或网络图片Uri地址
        val imageUri = if (type == ShareType.IMAGE) shareImageSourceUrlEt.content() else ""

        // 2. 指定分享的社交平台，比如facebook,twitter
        val platform = sharePlatformEt.content()

        if (platform.isBlank()) {
            appView.showToastMessage("请指定分享社交平台")
            return
        }

        socialApi.shareImpl(appActivity, platform, type, title, description, link, imageType, imageUri)
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

    /**
     * 生成本地测试的图片文件
     */
    private fun generateTestImage(context: Context): String {
        val testImageFile = File(context.getExternalFilesDir(null), "testImage.jpg")
        if (testImageFile.exists()) {
            appView.showToastMessage("测试图片`testImage.jpg`已生成")
        } else {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                testImageFile.createNewFile()
                inputStream = context.assets.open("slit.jpg")
                outputStream = FileOutputStream(testImageFile)
                val buffer = ByteArray(1024 * 10)
                var length = inputStream.read(buffer)
                while (length != -1) {
                    outputStream.write(buffer, 0, length)
                    length = inputStream.read(buffer)
                }
                appView.showToastMessage("测试图片`testImage.jpg`生成")
            } catch (e: Exception) {
                appView.showToastMessage(e.toString())
            } finally {
                outputStream?.flush()
                outputStream?.close()
                inputStream?.close()
            }
        }
        return testImageFile.absolutePath
    }

    override fun onSucceeded(resultJson: String) {
        appView.showToastMessage("成功: $resultJson")
    }

    override fun onFailed(error: OmniSDKError) {
        appView.showToastMessage("失败: ${error.getDescription()}")
    }

    override fun onCancelled() {
        appView.showToastMessage("取消")
    }

}