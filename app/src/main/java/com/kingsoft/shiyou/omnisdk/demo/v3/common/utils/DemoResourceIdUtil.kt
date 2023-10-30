package com.kingsoft.shiyou.omnisdk.demo.v3.common.utils

import android.content.Context

/**
 * Description: 资源ID获取方法类
 *
 * @author: LuXing created on 2021/4/8 16:23
 *
 */
object DemoResourceIdUtil {
    private fun getIdentifier(context: Context, name: String, defType: String): Int {
        return context.resources.getIdentifier(name, defType, context.packageName)
    }

    fun getLayoutId(context: Context, name: String): Int {
        return getIdentifier(context, name, "layout")
    }

    fun getId(context: Context, name: String): Int {
        return getIdentifier(context, name, "id")
    }
}