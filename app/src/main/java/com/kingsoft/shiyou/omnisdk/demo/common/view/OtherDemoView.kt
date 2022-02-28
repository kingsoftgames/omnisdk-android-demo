package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.core.entity.EventBreadcrumb
import com.kingsoft.shiyou.omnisdk.core.entity.EventData
import com.kingsoft.shiyou.omnisdk.core.entity.EventLevel
import com.kingsoft.shiyou.omnisdk.core.utils.SentryTrack
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherCallback
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoResourceIdUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.*

/**
 * Description: Omni SDK其他功能相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/12 9:47
 *
 */
class OtherDemoView : DemoView, IOtherCallback {

    private val urlEt: EditText by EditTextDelegate()
    private val paramsEt: EditText by EditTextDelegate()

    private val errorTrackMessageEt: EditText by EditTextDelegate()
    private val errorTrackEventNameEt: EditText by EditTextDelegate()

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
        otherApi = ApiManager.instance.getOtherApi(appActivity, this)

        urlEt
        paramsEt
        R.id.other_demo_view_open_url_btn.addClickListener {
            val url = urlEt.content()
            val params = paramsEt.content()
            if (url.isEmpty()) {
                appView.showToastMessage("URL地址不能为空")
            } else {
                otherApi.openBrowserActivityImpl(url, params)
            }
        }

        R.id.other_demo_view_open_local_url_btn.addClickListener {
            val url = "file:///android_asset/webviewJS.html"
            val params =
                paramsEt.content().replace("\"default_browser\":true", "\"default_browser\":false")
            otherApi.openBrowserActivityImpl(url, params)
        }

        initErrorTrackingDemoView()

        R.id.other_demo_view_show_score_dialog_btn.addClickListener {
            otherApi.showScoreDialog()
        }

        initChannelMethodDemoView()
    }

    private fun initErrorTrackingDemoView() {

        // 模拟设置用户信息
        SentryTrack.setUser("Omni${System.currentTimeMillis()}", "OmniTester")

        errorTrackMessageEt
        R.id.other_demo_view_error_track_message_btn.addClickListener {
            val errorMessage = errorTrackMessageEt.content()
            if (errorMessage.isNotBlank()) {
                appView.showToastMessage("Tracking: $errorMessage")
                val eventData = EventData().apply {
                    tagMap["omni.message.error"] = "NetworkError"
                    contextMap["Network Type"] = "WIFI"
                }
                SentryTrack.trackMessage(errorMessage, EventLevel.ERROR, eventData)
            }
        }

        errorTrackEventNameEt
        R.id.other_demo_view_error_track_custom_event_btn.addClickListener {
            val eventName = errorTrackEventNameEt.content()
            if (eventName.isNotBlank()) {
                appView.showToastMessage("Tracking: $eventName")
                val eventData = EventData().apply {
                    tagMap["omni.event.error"] = "LoadError"
                    contextMap["Reason"] = "Timeout"
                }
                eventData.breadcrumbs.add(EventBreadcrumb("OmniLaunchGameSuccessfully"))
                SentryTrack.trackEvent(eventName, EventLevel.FATAL, eventData)
            }
        }

        R.id.other_demo_view_error_track_divide_error_btn.addClickListener {
            val temp = 0
            try {
                val result = 100 / temp
            } catch (e: Exception) {
                appView.showToastMessage("Tracking: ${e.message}")
                SentryTrack.trackException(e)
            }
        }
        R.id.other_demo_view_error_track_out_of_index_error_btn.addClickListener {

            // 模拟生成日志文件
            val attachmentFile = File(appView.appActivity.filesDir, "omni_sentry_payment.log")
            if (attachmentFile.exists()) {
                attachmentFile.delete()
            }
            attachmentFile.createNewFile()
            attachmentFile.writeText(
                "Error: User Cannot Pay Because The Good Does Not Exist, ts = ${System.currentTimeMillis()}",
                Charsets.UTF_8
            )

            val products = listOf("com.package.gift")
            try {
                val result = products[2]
            } catch (e: Exception) {
                appView.showToastMessage("Tracking: ${e.message}")

                val eventData = EventData().apply {
                    tagMap["omni.access.error"] = "out_of_index"
                    contextMap["Data Object Type"] = "List"
                }

                // 添加面包屑数据，标示异常事件发生前一系列需要关注的微小事件
                eventData.breadcrumbs.add(
                    EventBreadcrumb("Checking Good Failed").apply {
                        category = "omni.findGood"
                        level = EventLevel.FATAL
                        extraData = mapOf(
                            "ProductId" to "com.package.gift",
                            "Price" to 12.99
                        )
                    }
                )

                // 添加附件文件
                eventData.attachments.add(attachmentFile)

                SentryTrack.trackException(e, eventData)
            }
        }

        R.id.other_demo_view_error_track_anr_error_btn.addClickListener {
            // 阻塞UI线程，模拟ANR异常
            runBlocking {
                delay(60_000)
            }
        }

        R.id.other_demo_view_error_track_crash_error_btn.addClickListener {
            // 抛出空指针异常，模拟应用奔溃CRASH
            throw NullPointerException("OmniTest:Null Pointer Exception")
        }
    }

    /**
     * 对于有特殊独用功能方法接口的渠道，将根据当前渠道名称加载其接口Demo UI界面。
     */
    private fun initChannelMethodDemoView() {
        channelMethodLayout = findViewById(R.id.other_demo_view_channel_method_layout)
        var channelName = OmniSDK.instance.getChannelName()
        //兼容老项目上面的配置
        if (channelName == "sgoverseas") {
            channelName = "website_en"
        }
        //兼容老项目上面的传音配置
        if (channelName == "tecno") {
            channelName = "transsion"
        }

        val channelMethodDemoViewName =
            channelName.lowercase(Locale.ROOT) + "_method_demo_view"
        val layoutId =
            DemoResourceIdUtil.getLayoutId(appActivity.baseContext, channelMethodDemoViewName)
        if (layoutId > 0) {
            // 当前渠道有独有方法需要进行Demo测试演示
            val channelMethodDemoView = LayoutInflater.from(appActivity)
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