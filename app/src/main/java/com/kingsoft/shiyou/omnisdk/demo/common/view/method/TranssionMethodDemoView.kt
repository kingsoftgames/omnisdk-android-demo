package com.kingsoft.shiyou.omnisdk.demo.common.view.method

import android.content.Context
import android.util.AttributeSet
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IOtherCallback
import com.kingsoft.shiyou.omnisdk.demo.common.view.DemoView

/**
 * Description: Transsion传音渠道独有功能方法接口相关Demo UI
 *
 * @author: LuXing created on 2021/3/29 16:47
 *
 */
class TranssionMethodDemoView : DemoView, IOtherCallback {

    private lateinit var otherApi: IOtherApi

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        otherApi = ApiManager.instance.getOtherApi(appActivity, this)

        R.id.transsion_method_demo_view_show_float_btn.addClickListener {
            otherApi.invokeShowFloatAdMethod(appActivity)
        }
        R.id.transsion_method_demo_view_close_float_btn.addClickListener {
            otherApi.invokeCloseFloatAdMethod(appActivity)
        }
        R.id.transsion_method_demo_view_query_skus_btn.addClickListener {
            otherApi.invokeQuerySkuDetailsList(listOf(), 0)
        }
    }

    override fun onSucceeded(resultJson: String) = appView.showToastMessage(resultJson)

    override fun onFailed(responseCode: Pair<Int, String>) =
        appView.showToastMessage(responseCode.toString())

}