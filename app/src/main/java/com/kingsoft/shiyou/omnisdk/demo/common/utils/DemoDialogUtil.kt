package com.kingsoft.shiyou.omnisdk.demo.common.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import com.google.android.material.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kingsoft.shiyou.omnisdk.basic.LogUtils
import com.kingsoft.shiyou.omnisdk.basic.RIdUtils
import com.kingsoft.shiyou.omnisdk.basic.printDebugStackTrace

/**
 * Description: 弹窗UI提示
 *
 * @author: LuXing created on 2021/4/8 17:11
 *
 */
object DemoDialogUtil {

    private fun getThemeContext(context: Context): Context {
        return if (checkMaterialTheme(context)) {
            LogUtils.debugI({ "DemoDialogUtil" }, { "app is material" })
            context
        } else {
            LogUtils.debugI({ "DemoDialogUtil" }, { "app no material" })
            ContextThemeWrapper(
                context,
                RIdUtils.getStyleId(context, "DialogActivityTheme")
            )
        }
    }

    private val MATERIAL_CHECK_ATTRS = intArrayOf(R.attr.colorPrimaryVariant)

    private fun checkMaterialTheme(context: Context) = checkTheme(context, MATERIAL_CHECK_ATTRS)

    private fun checkTheme(context: Context, themeAttributes: IntArray) =
        isTheme(context, themeAttributes)

    private fun isTheme(context: Context, themeAttributes: IntArray): Boolean {
        try {
            val a = context.obtainStyledAttributes(themeAttributes)
            for (i in themeAttributes.indices) {
                if (!a.hasValue(i)) {
                    a.recycle()
                    return false
                }
            }
            a.recycle()
        } catch (e: Exception) {
            printDebugStackTrace { e }
        }
        return true
    }

    fun showDialogWithContent(
        activity: Activity,
        title: String,
        content: String,
        confirmListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ -> },
        cancelListener: DialogInterface.OnClickListener? = null,
        confirmString: String = "确定",
        cancelString: String = "取消"
    ): AlertDialog {
        return MaterialAlertDialogBuilder(getThemeContext(activity)).apply {
            setTitle(title)
            setMessage(content)
            setPositiveButton(confirmString, confirmListener)
            if (cancelListener != null) setNegativeButton(cancelString, cancelListener)
        }.show()
    }

    fun showDialogWithActivity(
        activity: Activity,
        title: String,
        content: String
    ): AlertDialog {
        return showDialogWithContent(activity, title, content)
    }

    fun showDialogWithView(
        activity: Activity,
        title: String?,
        layoutView: View,
        confirmListener: DialogInterface.OnClickListener? = null,
        cancelListener: DialogInterface.OnClickListener? = null,
        confirmString: String = "确定",
        cancelString: String = "取消"
    ): AlertDialog {
        return MaterialAlertDialogBuilder(getThemeContext(activity)).apply {
            setTitle(title)
            setView(layoutView)
            if (confirmListener != null) setPositiveButton(confirmString, confirmListener)
            if (cancelListener != null) setNegativeButton(cancelString, cancelListener)
        }.show()
    }
}