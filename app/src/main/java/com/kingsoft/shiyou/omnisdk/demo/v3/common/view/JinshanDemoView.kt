package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.content.Context
import android.util.AttributeSet
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKDeleteAccountOptions
import com.kingsoft.shiyou.omnisdk.api.v3.entity.options.OmniSDKRestoreAccountOptions
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger

/**
 * Description:
 *
 * @author: LuXing created on 2022/1/21 15:36
 *
 */
class JinshanDemoView : DemoView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    )

    override fun initView() {
        R.id.v3_jinshan_demo_view_btn2.addClickListener {
            // 通行证注销账号
            OmniSDKv3.instance.invokeMethod("accountDelete", context)
        }
        R.id.v3_jinshan_demo_view_btn3.addClickListener {
            OmniSDKv3.instance.openAccountCenter(appActivity)
        }
        R.id.v3_jinshan_demo_view_btn4.addClickListener {
            OmniSDKv3.instance.deleteAccount(appActivity)
        }

        R.id.v3_jinshan_demo_view_btn5.addClickListener {
            OmniSDKv3.instance.restoreAccount(appActivity)
        }

        R.id.v3_jinshan_demo_view_btn6.addClickListener {
            val opts = OmniSDKDeleteAccountOptions.builder().enableCustomUI(true).build()
            OmniSDKv3.instance.deleteAccount(appActivity, opts)
        }

        R.id.v3_jinshan_demo_view_btn7.addClickListener {
            val opts = OmniSDKRestoreAccountOptions.builder().enableCustomUI(true).build()
            OmniSDKv3.instance.restoreAccount(appActivity, opts)
        }
    }
}