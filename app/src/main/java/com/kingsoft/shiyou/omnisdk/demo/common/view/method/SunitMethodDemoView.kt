package com.kingsoft.shiyou.omnisdk.demo.common.view.method

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherCallback
import com.kingsoft.shiyou.omnisdk.demo.common.view.DemoView
import com.kingsoft.shiyou.omnisdk.demo.common.view.EditTextDelegate

/**
 * Description: Sunit茄子渠道独有功能方法接口相关Demo UI
 *
 * @author: LuXing created on 2021/3/29 14:13
 *
 */
class SunitMethodDemoView : DemoView, IOtherCallback {

    private lateinit var otherApi: IOtherApi

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val rewardedEt: EditText by EditTextDelegate()
    private val eventEt: EditText by EditTextDelegate()

    override fun initView() {
        otherApi = ApiManager.instance.getOtherApi(appActivity, this)
        rewardedEt
        eventEt
        R.id.sunit_method_demo_view_rewarded_btn.addClickListener {
            if (rewardedEt.checkContent()) {
                otherApi.invokeChannelSunitShowRewardedBadgeViewMethod(rewardedEt.content())
            }
        }
        R.id.sunit_method_demo_view_event_btn.addClickListener {
            if (eventEt.checkContent()) {
                otherApi.invokeChannelSunitEventMethod(eventEt.content())
            }
        }
    }

    override fun onSucceeded(resultJson: String) = appView.showToastMessage(resultJson)

    override fun onFailed(responseCode: Pair<Int, String>) =
        appView.showToastMessage(responseCode.toString())

}