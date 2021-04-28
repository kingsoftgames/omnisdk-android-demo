package com.kingsoft.shiyou.omnisdk.demo.common.utils

import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Description: 弹窗UI提示
 *
 * @author: LuXing created on 2021/4/8 17:11
 *
 */
object DemoDialogUtil {

    fun showDialogWithContent(
        activity: AppCompatActivity,
        title: String,
        content: String,
        confirmListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ -> },
        cancelListener: DialogInterface.OnClickListener? = null,
        confirmString: String = "确定",
        cancelString: String = "取消"
    ): AlertDialog {
        return MaterialAlertDialogBuilder(activity).apply {
            setTitle(title)
            setMessage(content)
            setPositiveButton(confirmString, confirmListener)
            if (cancelListener != null) setNegativeButton(cancelString, cancelListener)
        }.show()
    }

    fun showDialogWithView(
        activity: AppCompatActivity,
        title: String?,
        layoutView: View,
        confirmListener: DialogInterface.OnClickListener? = null,
        cancelListener: DialogInterface.OnClickListener? = null,
        confirmString: String = "确定",
        cancelString: String = "取消"
    ): AlertDialog {
        return MaterialAlertDialogBuilder(activity).apply {
            setTitle(title)
            setView(layoutView)
            if (confirmListener != null) setPositiveButton(confirmString, confirmListener)
            if (cancelListener != null) setNegativeButton(cancelString, cancelListener)
        }.show()
    }
}