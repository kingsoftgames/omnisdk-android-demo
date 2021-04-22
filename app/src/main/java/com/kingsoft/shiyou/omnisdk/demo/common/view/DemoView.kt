package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.ICallback

/**
 * Description: 示例基础Demo View，所有演示的Demo示例View必须继承自这个类
 *
 * @author: LuXing created on 2021/3/10 18:26
 *
 */
abstract class DemoView(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(
        context,
        attributeSet,
        defStyleAttr
    ), ICallback {

    var TAG = "SDK(DemoView): "

    lateinit var appView: AppView

    val demoActivity get() = appView.demoActivity

    var hasAttached = false

    /**
     * 初始化View控件
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!hasAttached) {
            hasAttached = true
            TAG += this::class.simpleName!!
            initView()
        }
    }

    abstract fun initView()

    private var processingDialog: Dialog? = null

    /**
     * 显示加载框
     */
    override fun showProcessingDialog() {
        processingDialog = Dialog(demoActivity).apply {
            setContentView(R.layout.demo_processing_dialog)
        }
        processingDialog!!.show()
    }

    /**
     * 取消加载框
     */
    override fun cancelProcessingDialog() {
        try {
            processingDialog?.cancel()
            processingDialog = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun Int.editText(): EditText = findViewById<EditText>(this)

    protected fun EditText.content(): String =
        if (this.text != null && this.text.toString().trim().isNotBlank()) {
            this.text.toString()
        } else {
            ""
        }

    protected fun EditText.checkContent() = if (this.content().isBlank()) {
        appView.showToastMessage("相关数据不能为空")
        false
    } else {
        true
    }

    protected fun Int.button(): Button = findViewById<Button>(this)

    protected fun Int.addClickListener(execution: () -> Unit) {
        val btn = findViewById<Button>(this)
        btn.setOnClickListener {
            execution.invoke()
        }
    }
}