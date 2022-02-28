package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.EditText
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAdApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAdCallback

/**
 * Description: 广告功能相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/11 9:42
 *
 */
class AdDemoView : DemoView, IAdCallback {

    private lateinit var adApi: IAdApi

    private val bannerIdEt: EditText by EditTextDelegate()
    private val interstitialIdEt: EditText by EditTextDelegate()
    private val videoIdEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {

        // 获取广告相关功能API接口实例
        adApi = ApiManager.instance.getAdApi(appActivity, this)

        // 横幅广告
        bannerIdEt
        R.id.ad_demo_view_load_banner_ad_btn.addClickListener {
            if (bannerIdEt.content().isNotBlank()) {
                adApi.loadBannerAdImpl(bannerIdEt.content())
            } else {
                appView.showToastMessage("Banner广告ID不能为空")
            }
        }
        R.id.ad_demo_view_show_banner_ad_btn.addClickListener {
            if (bannerIdEt.content().isNotBlank()) {
                adApi.showBannerAdImpl(bannerIdEt.content(), Gravity.TOP, null)
            } else {
                appView.showToastMessage("Banner广告ID不能为空")
            }
        }

        R.id.ad_demo_view_hide_banner_ad_btn.addClickListener {
            if (bannerIdEt.content().isNotBlank()) {
                adApi.hideBannerAdImpl(bannerIdEt.content())
            } else {
                appView.showToastMessage("Banner广告ID不能为空")
            }
        }
        R.id.ad_demo_view_check_banner_ad_btn.addClickListener {
            if (bannerIdEt.content().isNotBlank()) {
                if (adApi.isBannerAdReadyImpl(bannerIdEt.content())) {
                    appView.showMessageDialog("广告:${bannerIdEt.content()} 已加载成功可显示")
                } else {
                    appView.showMessageDialog("广告:${bannerIdEt.content()} 未加载")
                }
            } else {
                appView.showToastMessage("Banner广告ID不能为空")
            }
        }

        // Interstitial插屏广告
        interstitialIdEt
        R.id.ad_demo_view_load_interstitial_ad_btn.addClickListener {
            if (interstitialIdEt.content().isNotBlank()) {
                adApi.loadInterstitialAdImpl(interstitialIdEt.content())
            } else {
                appView.showToastMessage("Interstitial广告ID不能为空")
            }
        }
        R.id.ad_demo_view_show_interstitial_ad_btn.addClickListener {
            if (interstitialIdEt.content().isNotBlank()) {
                adApi.showInterstitialAdImpl(interstitialIdEt.content())
            } else {
                appView.showToastMessage("Interstitial广告ID不能为空")
            }
        }
        R.id.ad_demo_view_check_interstitial_ad_btn.addClickListener {
            if (interstitialIdEt.content().isNotBlank()) {
                if (adApi.isInterstitialAdReadyImpl(interstitialIdEt.content())) {
                    appView.showMessageDialog("广告:${interstitialIdEt.content()} 已加载成功可显示")
                } else {
                    appView.showMessageDialog("广告:${interstitialIdEt.content()} 未加载")
                }
            } else {
                appView.showToastMessage("Interstitial广告ID不能为空")
            }
        }

        // Video视频广告
        videoIdEt
        R.id.ad_demo_view_load_video_ad_btn.addClickListener {
            if (videoIdEt.content().isNotBlank()) {
                adApi.loadRewardAdImpl(videoIdEt.content())
            } else {
                appView.showToastMessage("Video广告ID不能为空")
            }
        }
        R.id.ad_demo_view_show_video_ad_btn.addClickListener {
            if (videoIdEt.content().isNotBlank()) {
                adApi.showRewardAdImpl(videoIdEt.content())
            } else {
                appView.showToastMessage("Video广告ID不能为空")
            }
        }
        R.id.ad_demo_view_check_video_ad_btn.addClickListener {
            if (videoIdEt.content().isNotBlank()) {
                if (adApi.isRewardAdReadyImpl(videoIdEt.content())) {
                    appView.showMessageDialog("广告:${videoIdEt.content()} 已加载成功可显示")
                } else {
                    appView.showMessageDialog("广告:${videoIdEt.content()} 未加载")
                }
            } else {
                appView.showToastMessage("Video广告ID不能为空")
            }
        }
    }

    override fun showResult(result: String) {
        appView.showToastMessage("广告: $result")
    }

}