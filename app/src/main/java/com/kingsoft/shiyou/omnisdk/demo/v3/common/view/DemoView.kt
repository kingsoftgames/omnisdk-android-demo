package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.ICallback

/**
 * Description: 示例基础Demo View，所有演示的Demo示例View必须继承自这个类
 *
 * @author: LuXing created on 2021/3/10 18:26
 *
 */
@Suppress("NOTHING_TO_INLINE")
abstract class DemoView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(
        context,
        attributeSet,
        defStyleAttr
    ), ICallback {

    var TAG = ""

    /** 当DemoView被构建实例化后立即会被赋值 */
    lateinit var appView: AppView

    val appActivity get() = appView.appActivity
    val baseContext get() = appView.baseContext
    val appContext get() = appView.appContext

    var hasAttached = false

    /** 初始化View控件 */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!hasAttached) {
            hasAttached = true
            TAG = "${this::class.simpleName!!}# "
            initView()
        }
    }

    abstract fun initView()

    private var processingDialog: Dialog? = null

    /** 显示加载框 */
    override fun showProcessingDialog() {
        processingDialog = Dialog(appActivity).apply {
            setContentView(R.layout.v3_demo_processing_dialog)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
        processingDialog!!.show()
    }

    /** 取消加载框 */
    override fun cancelProcessingDialog() {
        processingDialog?.let {
            kotlin.runCatching {
                it.cancel()
            }
        }
        processingDialog = null
    }

    protected inline fun Int.editText(): EditText = findViewById(this)

    protected inline fun EditText.content(): String =
        if (this.text != null && this.text.toString().trim().isNotBlank()) {
            this.text.toString()
        } else {
            ""
        }

    protected inline fun EditText.checkContent() = if (this.content().isBlank()) {
        appView.showToastMessage("相关数据不能为空")
        false
    } else {
        true
    }

    protected inline fun Int.button(): Button = findViewById(this)

    protected inline fun Int.addClickListener(crossinline execution: () -> Unit) {
        this.button().setOnClickListener {
            execution.invoke()
        }
    }

    protected inline fun <reified T : View> Int.view(): T = findViewById(this)
    fun View.show(isShow: Boolean) =
        if (isShow) visibility = View.VISIBLE else visibility = View.GONE
}