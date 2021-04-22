package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherCallback
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoResourceIdUtil
import java.util.*

/**
 * Description: Omni SDK其他功能相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/12 9:47
 *
 */
class OtherDemoView : DemoView, IOtherCallback {

    private lateinit var otherApi: IOtherApi
    private lateinit var channelMethodLayout: FrameLayout

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        otherApi = ApiManager.instance.getOtherApi(this)
        R.id.other_demo_view_show_score_dialog_btn.addClickListener {
            otherApi.showScoreDialog()
        }
        R.id.other_demo_view_web_js_btn.addClickListener {
            otherApi.webJsImpl()
        }
        initChannelMethodDemoView()
    }

    /**
     * 对于有特殊独用功能方法接口的渠道，将根据当前渠道名称加载其接口Demo UI界面。
     */
    private fun initChannelMethodDemoView() {
        channelMethodLayout = findViewById(R.id.other_demo_view_channel_method_layout)
        val channelMethodDemoViewName =
            OmniSDK.instance.getChannelName().toLowerCase(Locale.ROOT) + "_method_demo_view"
        val layoutId =
            DemoResourceIdUtil.getLayoutId(demoActivity.baseContext, channelMethodDemoViewName)
        if (layoutId > 0) {
            // 当前渠道有独有方法需要进行Demo测试演示
            val channelMethodDemoView = LayoutInflater.from(demoActivity)
                .inflate(layoutId, channelMethodLayout, false) as DemoView
            channelMethodDemoView.appView = appView
            channelMethodLayout.visibility = View.VISIBLE
            channelMethodLayout.addView(channelMethodDemoView)
        } else {
            channelMethodLayout.visibility = View.GONE
        }
    }

    override fun onSucceeded(resultJson: String) {
        appView.showToastMessage("操作成功: $resultJson")
    }

    override fun onFailed(responseCode: Pair<Int, String>) {
        appView.showToastMessage("操作失败: $responseCode")
    }

}