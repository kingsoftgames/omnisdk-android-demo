package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.content.Context
import android.os.Process
import android.util.AttributeSet
import android.widget.RadioButton
import android.widget.RadioGroup
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IExitApi
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IExitCallback
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoDialogUtil
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger

/**
 * Description:
 *
 * @author: LuXing created on 2022/1/21 15:36
 *
 */
class ExitDemoView : DemoView, IExitCallback {

    private lateinit var exitApi: IExitApi
    private var gameCustom = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        exitApi = ApiManager.instance.getExitApi(appActivity, this)
        findViewById<RadioButton>(R.id.v3_exit_demo_view_custom_false).isChecked = true
        findViewById<RadioButton>(R.id.v3_exit_demo_view_custom_true).isChecked = false
        gameCustom = false
        findViewById<RadioGroup>(R.id.v3_exit_demo_view_custom_radio_group).setOnCheckedChangeListener { _, checkedId ->
            gameCustom = (checkedId == R.id.v3_exit_demo_view_custom_true)
            DemoLogger.d(TAG, "set gameCustom: $gameCustom")
        }
        R.id.v3_exit_demo_view_exit_btn.addClickListener {
            exitApi.onExitImpl(gameCustom)
        }
    }

    override fun showExitUi() {
        appActivity.runOnUiThread {
            DemoDialogUtil.showDialogWithContent(
                appActivity, "Demo(模拟游戏方退出界面)", "是否立即退出游戏应用?",
                { _, _ ->
                    try {
                        Process.killProcess(Process.myPid())
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                },
                { _, _ -> }, "立即退出", "取消"
            )
        }
    }
}