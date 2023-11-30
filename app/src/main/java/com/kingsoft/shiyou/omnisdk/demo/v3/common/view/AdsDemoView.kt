package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKAdOptions
import com.kingsoft.shiyou.omnisdk.api.v3.entity.results.OmniSDKShowAdStatus
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAdsApi
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAdsCallback

/**
 * Description: 社交功能相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/11 11:20
 *
 */
class AdsDemoView : DemoView, IAdsCallback {

    private lateinit var adsApi: IAdsApi

    private val placementEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    //    val adsId = "b654b46505629c"
//    val adsId = "testx9dtjwj8hp"

    override fun initView() {

        // 获取社交功能API接口实例
        adsApi = ApiManager.instance.getAdsApi(appActivity, this)

        placementEt.setText("test_android_RV_1")

        R.id.v3_ads_demo_view_preload_btn.addClickListener {
            val loginInfo = appView.getLoginInfo()
            if (loginInfo == null) {
                appView.showToastMessage("请先登录账号，再请求广告")
                return@addClickListener
            }
            val placementId = placementEt.content()
            if (placementId.isBlank()){
                appView.showToastMessage("请输入广告位ID")
                return@addClickListener
            }
            val opts = OmniSDKAdOptions.builder()
                .placementId(placementId)
                .build()
            adsApi.preloadAd(appActivity, opts)
        }

        R.id.v3_ads_demo_view_show_btn.addClickListener {
            val loginInfo = appView.getLoginInfo()
            if (loginInfo == null) {
                appView.showToastMessage("请先登录账号，再请求广告")
                return@addClickListener
            }
            val placementId = placementEt.content()
            if (placementId.isBlank()){
                appView.showToastMessage("请输入广告位ID")
                return@addClickListener
            }
            val opts = OmniSDKAdOptions.builder()
                .placementId(placementId)
                .build()
            adsApi.showAd(appActivity, opts)
        }
    }

    override fun onPreloadAdSuccess(adId: String) {
        appView.showMessageDialog("广告id:$adId 加载完成")
        //  直接播放视频
//        val adsId = placementEt.content()
//        adsApi.showAd(appActivity, OmniSDKAdOptions(adsId))
    }

    override fun onShowAdSuccess(status: OmniSDKShowAdStatus, token: String) {
        when (status) {
            OmniSDKShowAdStatus.START -> {
                appView.showToastMessage("广告展示开始")
            }
            OmniSDKShowAdStatus.CLOSED -> {
                appView.showToastMessage("广告关闭")
            }
            OmniSDKShowAdStatus.CLICKED -> {
                appView.showToastMessage("点击广告")
            }
            OmniSDKShowAdStatus.REWARDED -> {
                appView.showMessageDialog("激励视频奖励达成 token=$token")
            }
        }
    }

    override fun onError(error: OmniSDKError) {
        appView.showErrorDialog(error.getDescription())
    }

}