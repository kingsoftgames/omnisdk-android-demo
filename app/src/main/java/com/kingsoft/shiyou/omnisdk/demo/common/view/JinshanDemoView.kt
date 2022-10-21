package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.demo.R

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
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {

        R.id.jinshan_demo_view_btn1.addClickListener {
            OmniSDK.instance.invokeMethod("updateUserInfo", context)
        }
    }


}